package me.sombrero.demospringdata;

import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * 스프링 데이터 JPA Repository 인터페이스들(JpaRepository, PagingAndSortingRepository, CrudRepository)이
 * 제공하는 API들을 가져오지 않고 싶은 경우(내가 직접 만들고 싶은 경우)에는
 * 아래처럼 JpaRepository를 상속받지 않고 @RepositoryDefinition를 붙인다.
 */
@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class) // 엔티티 타입, 엔티티에서 사용하는 아이디(PK 타입)
public interface CommentRepository {

    Comment save(Comment comment);

    List<Comment> findAll();

}
