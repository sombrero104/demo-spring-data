package me.sombrero.demospringdata;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface MyRepository<T, Id extends Serializable> extends Repository<T, Id> {
    /**
     * 위에서 Id extends Serializable로 정의한 이유? (Serializable Id를 쓰는 이유?)
     * 대부분 ID로 쓰고 있는 타입(Integer 등등)이 해당 인터페이스를 구현하고 있기 때문에
     * 조금이라도 타입으로 제약을 하고자 Serializable을 구현한 것만 ID로 생각하겠다고 정의한 것인데
     * 반드시 이렇게 해야 하는건 아니다.
     */

    <E extends T> E save(E entity); // 커스텀 메소드. 스프링 데이터 JPA가 자동으로 구현해준다.

    List<T> findAll();

}
