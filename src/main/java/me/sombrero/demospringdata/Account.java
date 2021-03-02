package me.sombrero.demospringdata;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Entity
 * Account가 엔티티임을 알려준다. 이 엔티티는 테이블과 매핑이 된다. (@Table 애노테이션이 생략된 것과 같다.)
 *
 * @Entity(name = "myAccount") 처럼 엔티티에 이름을 줄 수 있다. 이 이름은 객체 세상에서만 사용하는 이름이다.
 * 테이블 이름은 @Table 애노테이션에 지정해줄 수 있는데 이름을 설정하지 않으면 기본적으로 엔티티의 이름을 사용한다.
 * @Entity 에도 이름을 설정하지 않으면 기본적으로 클래스 이름을 사용한다.
 *
 * User는 몇몇 DB에서 예약어로 되어 있어서 테이블명으로 사용 불가능하다. (postgre도 그러하다.)
 */

/**
 * [ Entity 타입과 Value 타입 ]
 * Entity 타입은 id와 같은 고유한 식별자를 가지고 있다.
 * 다른 엔티티에서 이 Account 엔티티를 독립적으로 참조하는 경우가 생긴다.
 * 하지만 Value 타입인 username과 password 같은 필드는 항상 Account 엔티티를 통해서만 접근할 수 있다.
 *
 * [ Value 타입의 종류 ]
 * (1) 기본 타입 (String, Date, Boolean, ...)
 * (2) Composite Value 타입
 * (3) Collection Value 타입
 *      (3-1) 기본 타입의 컬렉션
 *      (3-2) 컴포짓 타입의 컬렉션
 */
@Entity
public class Account {

    /**
     * [ Long 타입 ]
     * primitive 타입인 long으로 사용하면 값이 없는 경우 디폴트 값이 0으로 들어가서
     * 실제 아이디가 0인 것으로 오해하는 상황이 없도록 랩퍼 타입인 Long으로 아이디를 만들었다.
     */
    /**
     * [ @GeneratedValue ]
     * DB에 따라서 자동생성 값 생성 전략이 달라진다.
     * 하이버네이트에서 기본적으로 postgres는 시퀀스를 사용한다. (postgres는 시퀀스 외에 아이덴티티 컬럼도 지원한다.)
     * 시퀀스를 만들어준 것을 받아서 키 값으로 만들어서 insert를 하게 된다.
     * 경우에 따라서는 키의 유일성을 확인하기 위해서 백업 테이블을 사용할 수도 있다.
     * 하이버네이트에서는 자동 값 생성 전략이 DB에 따라 다르게 설정되어 있다.
     * @GeneratedValue 에 명시적으로 전략을 설정할 수 있는데 디폴트는 AUTO 이다.
     *      @GeneratedValue(strategy = GenerationType.AUTO)
     * 시퀀스로 설정할 수도 있는데 어떤 시퀀스를 사용할지 설정할 수도 있다.
     * @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "")
     */
    @Id @GeneratedValue
    private Long id;

    /**
     * 모든 엔티티의 멤버변수에는 @Column이 생략되어 있는 것과 마찬가지이다.
     * @Column을 지정하는 대부분의 경우는 nullable, unique와 같이 추가적인 제약을 지정해줄 때 사용한다.
     *
     * 주의!)
     * 최초 @Column 만 붙여서 컬럼을 만든 상태에서 중간에 unique = true 를 붙일 수는 없다.
     * 이미 컬럼이 만들어진 상태에서는 변경이 불가능하다. (그래서 개발 시에는 ddl-auto 옵션을 create로 사용하는 것이 편리하다.)
     */
    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    /**
     * 컬럼이 이미 만들어져 있는 상태에서 중간에 타입을 변경할 수 없다.
     * (예를 들어 TemporalType.TIMESTAMP 으로 컬럼을 만들었는데 중간에 TemporalType.TIME 으로 바꾼다던가..)
     */
    @Temporal(TemporalType.DATE) // 날짜만 DB에 들어간다. (예시 => 2021-03-02)
    // @Temporal(TemporalType.TIME) // 시간만 DB에 들어간다. (예시 => 18:04:15.671)
    // @Temporal(TemporalType.TIMESTAMP) // 날짜와 시간이 DB에 들어간다. (예시 => 2021-03-02 18:03:00.79)
    private Date created = new Date();

    private String yes;

    @Transient // 컬럼으로 매핑 안해줌. (컬럼 생성 제외.)
    private String no;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "home_street"))
    })
    private Address address;

    /**
     * [ 관계의 주인이 Account인 경우 ] => @OneToMany : 기본값은 조인 테이블 생성.
     * Account 엔티티에서 Study 엔티티와의 관계를 설정했으므로
     * Account가 이 관계에 대한 주인이며, Account가 이 관계를 관리한다.
     * 실행하면 아래와 같이 'account_studies'라는 조인 테이블이 생성된다.
     * =>
     *     create table account_studies (
     *        account_id int8 not null,
     *         studies_id int8 not null,
     *         primary key (account_id, studies_id)
     *     )
     *
     * [ 양방향 관계 설정하기 ]
     * 양쪽에 @OneToMany, @ManyToOne를 붙인 후..
     * 아래와 같이 @OneToMany의 mappedBy에 상대 필드인 Account 변수 이름을 설정해 준다.
     * 이렇게 설정하지 않으면 양쪽 모두 단방향이게 된다.
     * 주인은 외래키를 가진 Study가 된다.
     * @OneToMany(mappedBy = "owner")
     * 그리고, 관계에 대한 매핑을 관계의 주인인 Study쪽에 해준다.
     */
    // @OneToMany // 한 사람이 여러개의 스터디를 만들 수 있다.
    @OneToMany(mappedBy = "owner") // 양방향 관계로 설정할 경우, mappedBy를 설정해 준다. (상대의 필드 변수 이름으로..) 주인은 FK를 가진 Study가 된다.
    private Set<Study> studies = new HashSet<>(); // OneToMany 이므로 타입이 컬렉션이어야 한다.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Study> getStudies() {
        return studies;
    }

    public void setStudies(Set<Study> studies) {
        this.studies = studies;
    }

    /**
     * Study와 Account가 양방향 관계일 경우,
     * 양쪽 다 서로에 대한 정보를 가지고 있어야 하므로
     * 서로에 대한 정보를 저장하는 부분을 하나로 묶어서 관리하는 것이 좋다.
     */
    public void addStudy(Study study) {
        this.getStudies().add(study);
        study.setOwner(this);
    }

    public void removeStudy(Study study) {
        this.getStudies().remove(study);
        study.setOwner(null);
    }

}
