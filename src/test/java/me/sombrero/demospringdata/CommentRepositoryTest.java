package me.sombrero.demospringdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // jUnit4의 @RunWith(SpringRunner.class)와 같다.
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    void crud() {
        /*Comment comment = new Comment();
        comment.setComment("Hello comment!!");
        commentRepository.save(comment);

        List<Comment> all = commentRepository.findAll();
        assertThat(all.size()).isEqualTo(1);

        long count = commentRepository.count();
        assertThat(count).isEqualTo(1);*/

        /**
         * [ Optional로 반환받아서 사용 (Optional은 자바8 이상에서 지원.) ]
         */
        /*Optional<Comment> byId = commentRepository.findById(100l); // Optional로 반환된다.
        assertThat(byId).isEmpty();*/
         // byId.isPresent(); // 값이 있는지 없는지 확인하는 Optional 메소드.
        /**
         * 위에서 받아온 comment가 있으면 해당 comment가 저장되고
         * 없으면 새로운 comment를 만든다.
         */
        // Comment comment = byId.orElse(new Comment());
        /**
         * 위에서 받아온 comment가 있으면 해당 comment가 저장되고
         * 없으면 예외를 던진다.
         */
        // Comment comment = byId.orElseThrow(IllegalArgumentException::new);

        /**
         * [ Optional로 반환받지 않고 그냥 Entity로 반환받는 경우 ]
         */
        /*Comment comment = commentRepository.findById(100l);
        if(comment == null) { // 최근에는 null 안쓰는 쪽으로 가는 추세.
            throw new IllegalArgumentException();
        }*/
        // assertThat(comment).isNull();

        /**
         * [ 스프링 데이터 JPA가 반환하는 컬렉션 타입은 null이 될 수 없다. ]
         * 스프링 데이터 JPA가 지원하는 Repository들이 반환하는 컬렉션 타입들은
         * 값이 없더라도 결코 null이 되지 않는다.
         * 비어있는 컬렉션 타입으로 리턴해 주는데 이것은 스프링 데이터 JPA의 특징이다.
         * 때문에 컬렉션 타입으로 반환받을 때에는 null 체크는 필요가 없는 코드이다.
         */
        /*List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isEmpty();*/

        /**
         * [ null을 저장할 경우. ]
         */
        // commentRepository.save(null);

        /**
         * [ 쿼리 메소드 만들기. ]
         * 1. list 타입으로 반환 받아서 테스트 하기.
         */
        /*this.createComment(20, "spring data jpa");
        this.createComment(55, "HIBERNATE SPRING");
        List<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseAndLikeCountGreaterThanOrderByLikeCountDesc("Spring", 10);
        assertThat(comments.size()).isEqualTo(2);
        assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 55);*/

        /**
         * [ 쿼리 메소드 만들기. ]
         * 2. 페이지 타입으로 반환 받아서 테스트 하기.
         */
        /*this.createComment(20, "spring data jpa");
        this.createComment(55, "HIBERNATE SPRING");
        this.createComment(70, "Hello Spring");
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "likeCount"));
        Page<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);
        assertThat(comments.getNumberOfElements()).isEqualTo(2); // 현재 페이지 엘리먼트 갯수.
        // comments.getTotalElements()는 페이지와 상관없이 전체 갯수.
        assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 70);*/

        /**
         * [ 쿼리 메소드 만들기. ]
         * 3. 스트림 타입으로 반환 받아서 테스트 하기.
         * 스트림은 다 쓴 후 닫아줘야 하기 때문에 try-with-resources를 사용해야 한다.
         */
        this.createComment(20, "spring data jpa");
        this.createComment(55, "HIBERNATE SPRING");
        this.createComment(70, "Hello Spring");
        try(Stream<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("Spring")) {
            Comment firstComment = comments.findFirst().get();
            assertThat(firstComment.getLikeCount()).isEqualTo(70);
        }

    }

    private void createComment(int likeCount, String commentStr) {
        Comment comment = new Comment();
        comment.setComment(commentStr);
        comment.setLikeCount(likeCount);
        commentRepository.save(comment);
    }

}