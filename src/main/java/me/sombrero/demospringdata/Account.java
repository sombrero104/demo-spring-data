package me.sombrero.demospringdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Entity
 * Account가 엔티티임을 알려준다. 이 엔티티는 테이블과 매핑이 된다. (@Table 애노테이션이 생략된 것과 같다.)
 *
 * @Entity(name = "myAccount") 처럼 엔티티에 이름을 줄 수 있다. 이 이름은 객체 세상에서만 사용하는 이름이다.
 * 테이블 이름은 @Table 애노테이션에 지정해줄 수 있는데 이름을 설정하지 않으면 기본적으로 엔티티의 이름을 사용한다.
 * @Entity 에도 이름을 설정하지 않으면 기본적으로 클래스 이름을 사용한다.
 */
@Entity
public class Account {

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
