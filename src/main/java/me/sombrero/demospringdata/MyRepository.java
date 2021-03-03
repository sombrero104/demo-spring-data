package me.sombrero.demospringdata;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

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

    /**
     * 단일값을 받아오는 경우에는 아래와 같이 Optional을 사용하는 것을 추천.
     * null 체크 등을 좀 더 아름답게(?) 처리할 수 있다.
     * Optional을 사용하지 않고 Entity만 반환하게 사용하면
     * 값이 없는 경우 그냥 null이 나온다.
     */
    <E extends T> Optional<E> findById(Id id); // Optional을 리턴하는 경우.
    // <E extends T> E findById(Id id); // Optional을 사용하지 않고 Entity만 반환하는 경우 값이 없으면 null 리턴.
}
