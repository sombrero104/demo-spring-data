package me.sombrero.demospringdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 원래는 스프링부트가 아니라면 @Configuration이 붙은 클래스에 @EnableJpaRepositories를 붙여줘야 한다.
 * 스프링부트에서는 자동으로 설정해준다.
 * 스프링부트가 아니라면 main()메소드가 있는 @SpringBootApplication쪽에 붙여주면 된다.
 * (@SpringBootApplication가 @Configuration이기 때문에..)
 * 그리고 @Repository를 붙이지 않아도 빈으로 등록이 된다.
 */
public interface PostRepository extends JpaRepository<Post, Long> { // < 엔티티 타입, 엔티티에서 사용하는 아이디(PK 타입) >

    /**
     * [ 커스텀 메소드 ]
     * 이렇게만 만들어두면 스프링 데이터 JPA가 메소드 이름을 분석해서 자동으로 쿼리를 만들어준다.
     */
    Page<Post> findByTitleContains(String title, Pageable pageable);

    long countByTitleContains(String title);

}
