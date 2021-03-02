package me.sombrero.demospringdata;

import javax.persistence.*;
import java.util.Date;

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

}
