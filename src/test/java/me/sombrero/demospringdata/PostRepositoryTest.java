package me.sombrero.demospringdata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) // jUnit4의 @RunWith(SpringRunner.class)와 같다.
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    /**
     * @DataJpaTest와 같이 데이터용 테스트들은 기본적으로 @Transactional를 가지고 있는데
     * @Transactional이 붙어있으면 기본적으로 모든 테스트는 롤백을 시킨다.
     * 하이버네이트는 필요할 때만 DB에 데이터를 싱크하는데
     * '이거 어차피 롤백할 쿼리 인데 내가 뭐하러 DB에 insert하냐. 안해.'라고 하고 저장을 안한다.
     * 그래서 insert 쿼리조차 안날아간다.
     * 롤백하고 싶지 않으면 @Rollback(false)를 붙여주면 insert 쿼리문이 날아간다.
     */
    @Test
    @Rollback(false)
    void crudRepository() {
        // Given
        Post post = new Post();
        post.setTitle("hello spring boot common");
        assertThat(post.getId()).isNull();

        /**
         * [ CrudRepository API 테스트 ]
         */
        // When
        Post newPost = postRepository.save(post);

        // Then
        assertThat(newPost.getId()).isNotNull();

        // When
        List<Post> posts = postRepository.findAll();

        // Then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.contains(newPost));

        /**
         * [ pagingAndSortingRepository API 테스트 ]
         */
        // When
        Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));// page:첫번째 페이지, size:페이지 사이즈
        assertThat(page.getTotalElements()).isEqualTo(1); // 전체 갯수가 몇개인지
        assertThat(page.getNumber()).isEqualTo(0); // 현재 페이지 번호
        assertThat(page.getSize()).isEqualTo(10); // 페이지 사이즈
        assertThat(page.getNumberOfElements()).isEqualTo(1); // 현재 페이지에 있는 post 갯수

        /**
         * [ PostRepository에 커스텀하게 만든 API 테스트 ]
         */
        // When
        page = postRepository.findByTitleContains("spring", PageRequest.of(0, 10));
        assertThat(page.getTotalElements()).isEqualTo(1); // 전체 갯수가 몇개인지
        assertThat(page.getNumber()).isEqualTo(0); // 현재 페이지 번호
        assertThat(page.getSize()).isEqualTo(10); // 페이지 사이즈
        assertThat(page.getNumberOfElements()).isEqualTo(1); // 현재 페이지에 있는 post 갯수
    }

}