package me.sombrero.demospringdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

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
        List<Comment> comments = commentRepository.findAll();
        assertThat(comments).isEmpty();
    }

}