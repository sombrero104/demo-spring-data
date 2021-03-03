package me.sombrero.demospringdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*Account account = new Account();
        account.setUsername("Anton");
        account.setPassword("pass");

        Study study = new Study();
        study.setName("Spring Data JPA");*/

        /**
         * 단방향 관계일 경우에는 주인인 한쪽에만 값을 설정해줘도 되지만,
         * 양방향 관계일 경우에는 양쪽 다 값을 줘야 한다.
         * 양방향이기 때문에 서로간에 정보를 서로가 가지고 있어야 맞다.
         */
        /*
        account.getStudies().add(study);
        study.setOwner(account);
        */
        // account.addStudy(study); // 양방향 관계일 경우, 위 두줄을 하나로 묶어서 메소드로 만들어 두는 것이 좋다.

        /**
         * 저장하는 메소드 호출.
         * 저장하는 메소드 호출 전까지의 엔티티는 Transient 상태,
         * 저장하는 메소드 호출 후에는 JPA에 의해 관리되는 Persistent 상태이다. (JPA가 아는 상태.)
         * 그리고 저장하는 메소드를 호출한다고 해서 바로 DB에 저장되는 것은 아니다.
         */
        // entityManager.persist(account); // JPA로 저장하기.

        /**
         * 아래와 같이 하이버네이트의 API인 Session을 꺼내서 사용할 수도 있다.
         */
        Session session = entityManager.unwrap(Session.class); // JPA가 감싸고 있는 하이버네이트 API에 접근해서 사용.
        /*session.save(account); // 하이버네이트로 저장하기.
        session.save(study);*/

        /**
         * 위에서 이미 저장된(캐시된) 데이터가 있기 때문에 DB에서 가져오지 않는다. (select 쿼리가 발생하지 않는다.)
         */
        // Account anton = session.load(Account.class, account.getId()); // 조회하지만 select 쿼리문이 발생하지 않는다.

        /**
         * 원래의 값과는 다른 값으로 값을 한 번 수정하는 경우.
         */
        // anton.setUsername("Gogol"); // update 쿼리문이 발생한다.

        /**
         * 값을 여러번 수정하는데 마지막에 수정한 값이 원래의 값과 같은 경우.
         */
        /*anton.setUsername("Gogol");
        anton.setUsername("Carl");
        anton.setUsername("Anton"); // 이름을 3번 변경했지만 update문이 발생하지 않는다.
        // => 왜? 바꿀 필요가 없으니까. 객체의 상태가 처음이랑 똑같으니까.
        // => 이것이 Dirty Checking, Write Behind의 조합이다.

        System.out.println("=====================================================");
        System.out.println("##### anton.getUsername(): " + anton.getUsername());*/
        /**
         * 위 코드를 실행하는 시점은 save 호출 이후 아직 DB에 저장하지도 않은 상태이다!!
         * 때문에 위에서 캐시된 정보를 출력한 후 트랜잭션이 끝날 때 마지막에 insert가 발생한다.
         */


        /*Post post = new Post();
        post.setTitle("Spring Data JPA");

        Comment comment1 = new Comment();
        comment1.setComment("spring data jpa comment..1");
        post.addComment(comment1);

        Comment comment2 = new Comment();
        comment2.setComment("spring data jpa comment..2");
        post.addComment(comment2);

        session.save(post);*/

        /*Post post = session.get(Post.class, 3l);
        session.delete(post);*/

        /*Post post = session.get(Post.class, 1l); // get은 무조건 DB에서 가져옴. 해당 데이터가 없으면 레퍼런스를 null로 만들고 예외를 던짐.
        // Post post_load = session.load(Post.class, 1l); // load는 데이터가 없으면 예외를 던짐. 프록시로도 가져올 수 있음.
        System.out.println("===================================");
        System.out.println("##### post.getTitle(): " + post.getTitle());*/

        /*Comment comment = session.get(Comment.class, 2l);
        System.out.println("===================================");
        System.out.println("##### comment.getComment(): " + comment.getComment());
        System.out.println("##### comment.getPost().getTitle(): " + comment.getPost().getTitle());*/


        /*Post post = session.get(Post.class, 1l);
        System.out.println("===================================");
        System.out.println("##### post.getTitle(): " + post.getTitle());

        post.getComments().forEach(c -> {
            System.out.println("------------------------------------");
            System.out.println(c.getComment());
        });*/

        /**
         * [ JPQL (HQL) ]
         * 이 JPQL(HQL)이 각 DB에 맞는 SQL로 변환이 되어서 최종적으로 변환된 SQL이 실행됨.
         * JPQL의 단점은 오타가 발생할 수 있다는 것이다. (Type safe하지 않다.)
         */
        // Query query = entityManager.createQuery("SELECT p FROM Post AS p");// 엔티티 Post를 조회.
        /**
         * JPA 2.0 이후부터는 아래와 같이 Typed Query를 만들 수 있다.
         * 이렇게 하면 결과 리스트가 Post타입의 리스트로 나오게 된다.
         */
        /*Query query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
        List<Post> posts = query.getResultList();
        posts.forEach(System.out::println);*/

        /**
         * Type Safe한 쿼리인 Criteria를 사용하는 방법.
         */
        /*CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> query = builder.createQuery(Post.class);
        Root<Post> root = query.from(Post.class);
        query.select(root);
        List<Post> posts = entityManager.createQuery(query).getResultList();
        posts.forEach(System.out::println);*/

        /**
         * NamedQuery 사용하는 방법.
         * Post 엔티티 상단에 @NamedQueries를 설정해주고 아래와 같이 사용할 수 있다.
         */
        /*Query all_post = entityManager.createNamedQuery("all_post");
        List posts = all_post.getResultList();
        posts.forEach(System.out::println);*/
    }

}
