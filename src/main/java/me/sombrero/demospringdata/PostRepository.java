package me.sombrero.demospringdata;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 원래는 스프링부트가 아니라면 @Configuration이 붙은 클래스에 @EnableJpaRepositories를 붙여줘야 한다.
 */
public interface PostRepository extends JpaRepository<Post, Long> { // < 엔티티 타입, 엔티티에서 사용하는 아이디(PK 타입) >
}
