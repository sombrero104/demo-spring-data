package me.sombrero.demospringdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * 스프링 데이터 JPA Repository 인터페이스들(JpaRepository, PagingAndSortingRepository, CrudRepository)이
 * 제공하는 API들을 가져오지 않고 싶은 경우(정말 내가 제공하고 싶은 기능들만 딱 정의하고 싶은 경우)에는
 * 아래처럼 JpaRepository를 상속받지 않고 @RepositoryDefinition를 붙인다.
 * 이렇게 만들면 스프링 데이터 JPA가 이 인터페이스도 빈으로 등록해주고
 * 커스텀 메소드도 자동으로 구현해준다.
 */
// @RepositoryDefinition(domainClass = Comment.class, idClass = Long.class) // 엔티티 타입, 엔티티에서 사용하는 아이디(PK 타입)
public interface CommentRepository extends MyRepository<Comment, Long> { // 여기에서 정의하지 않고 MyRepository 상속 받도록 변경했다.

    /*Comment save(Comment comment); // 커스텀 메소드. 스프링 데이터 JPA가 자동으로 구현해준다.

    List<Comment> findAll();*/

    /**
     * 스프링 데이터 JPA Repository 인터페이스들이 제공하는 API 중 일부만 사용하고 싶은 경우에는
     * 복사해서 가져와서 써도 된다.
     */
    long count();

    // @Query("SELECT c FROM Comment AS c")
    @Query(value = "SELECT * FROM Comment", nativeQuery = true)
    List<Comment> findByCommentContains(String keyword);

    /**
     * Page로 리턴하기 위해서는 파라미터로 Pageable을 줘야 한다.
     * 그냥 List로 반환하면 페이지 관련 정보 없이 리턴되는 것이다.
     * Pageable에 sort() 메소드가 있어서 sorting 관련 정보도 넣을 수 있다.
     * Paging 관련 정보 없이 sorting만 하고 싶은 경우에는 파라미터에 Pageable이 아닌 Sort만 정의해 줘도 된다.
     * Sort는 페이징 관련 개념이 없으므로 Page가 아닌 List로 리턴한다.
     */
    // Page<Comment> findByLikeCountGreaterThanAndCommentOrderByCreatedDesc(int likeCount, Comment comment, Pageable pageable);
    // Page<Comment> findByLikeCountGreaterThanAndComment(int likeCount, Comment comment, Pageable pageable);
    List<Comment> findByLikeCountGreaterThanAndComment(int likeCount, Comment comment, Sort sort);

}
