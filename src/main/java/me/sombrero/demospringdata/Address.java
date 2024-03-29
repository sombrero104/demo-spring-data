package me.sombrero.demospringdata;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * [ Composite Value 타입 맵핑 ]
 * Address가 독립적으로 레퍼런스가 되어야 하는 경우에는 Address도 엔티티로 만들 수 있다.
 * 하지만 이 예제에서는 Value 타입으로 만들었다.
 * 이 예제에서는 Address를 Account에 속한 하나의 데이터로 취급한다.
 * 때문에 이 Address의 생명주기는 Account 엔티티에 속해있다.
 * Account 엔티티가 만들어지면 Address도 만들어지고, Account 엔티티가 삭제되면 같이 삭제된다.
 * 이와 같이 어떠한 엔티티에 종속적인 타입을 Value 타입이라고 보면 된다.
 * Value 타입에는 @Embeddable 애노테이션을 붙인다.
 * 이 Address를 사용하는 엔티티쪽에서는 @Embedded를 붙여준다.
 * Account 테이블이 만들어질 때 이 Address의 필드들도 Account 테이블 안에 컬럼이 만들어진다.
 */
@Embeddable
public class Address {

    /**
     * 이 곳의 필드들도 모두 @Column 이 생략되어 있다.
     */
    // @Column
    private String street;

    private String city;

    private String state;

    private String zipCode;

}
