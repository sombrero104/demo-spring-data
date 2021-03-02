package me.sombrero.demospringdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private String username;

    private String password;

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
