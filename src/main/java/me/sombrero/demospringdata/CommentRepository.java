package me.sombrero.demospringdata;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * 스프링 데이터 JPA Repository 인터페이스들(JpaRepository, PagingAndSortingRepository, CrudRepository)이
 * 제공하는 API들을 가져오지 않고 싶은 경우(정말 내가 제공하고 싶은 기능들만 딱 정의하고 싶은 경우)에는
 * 아래처럼 JpaRepository를 상속받지 않고 @RepositoryDefinition를 붙인다.
 * 이렇게 만들면 스프링 데이터 JPA가 이 인터페이스도 빈으로 등록해주고
 * 커스텀 메소드도 자동으로 구현해준다.
 */
// @RepositoryDefinition(domainClass = Comment.class, idClass = Long.class) // 엔티티 타입, 엔티티에서 사용하는 아이디(PK 타입)
// public interface CommentRepository extends MyRepository<Comment, Long> { // 여기에서 정의하지 않고 MyRepository 상속 받도록 변경했다.
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /*Comment save(Comment comment); // 커스텀 메소드. 스프링 데이터 JPA가 자동으로 구현해준다.

    List<Comment> findAll();*/

    /**
     * 스프링 데이터 JPA Repository 인터페이스들이 제공하는 API 중 일부만 사용하고 싶은 경우에는
     * 복사해서 가져와서 써도 된다.
     */
    long count();

    // @Query("SELECT c FROM Comment AS c")
    /*@Query(value = "SELECT * FROM Comment", nativeQuery = true)
    List<Comment> findByCommentContains(String keyword);*/

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

    /**
     * [ IgnoreCase ]
     * IgnoreCase를 붙이면
     *  => upper(comment0_.comment) like upper(?)
     * 위 처럼 쿼리문에 upper()를 사용해서
     * DB의 값과 파라미터로 받은 값을 둘 다 대문자로 변환해서 같은지 비교한다.
     *
     * [ LikeCountGreaterThan ]
     * LikeCountGreaterThan을 붙이면 파라미터로 받은 likeCount 값보다 큰 것을 찾아준다.
     */
    // List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThanOrderByLikeCountDesc(String keyword, int likeCount);

    /**
     * Pageable을 파라미터로 받아서 Page로 반환.
     */
    // Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    /**
     * Stream으로 반환.
     */
    // Stream<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);

    /**
     * @Async (쿼리에서는 비추?)
     * 비동기 쿼리. (권장하는 방법은 아님.)
     * 백그라운드에서 동작하는 스레드풀에 이 메소드를 실행하는 작업을 위임한다.
     * 이 메소드를 호출해서 실행하는 것을 별도의 스레드에서 동작 시키는 것이다.
     * non-blocking으로 만들려면 반환 타입을 Future로 감싸준다.
     *
     * @Async를 사용하려면 @Configuration이 있는 곳에 @EnableAsync를 붙여줘야 한다.
     *
     * 그런데..
     * 보통 성능 병목 현상은 DB에서 부하가 일어나서 발생하는데..
     * DB에 쿼리를 비동기로 날려도 성능 부하는 DB가 받기 때문에 결국엔 부하는 같다.
     * 그냥.. 처음 비동기를 시작한 메인 스레드만 다른 일을 좀 더 빨리 처리할 수 있을 뿐이다.
     * 그래서 쿼리할 때에 비동기로 설정하는 것은 비추..라고 한다.
     */
    // @Async
    // Future<List<Comment>> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);
    // ListenableFuture<List<Comment>> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);
}
