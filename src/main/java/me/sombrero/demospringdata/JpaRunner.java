package me.sombrero.demospringdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("Anton");
        account.setPassword("pass");

        Study study = new Study();
        study.setName("Spring Data JPA");

        /**
         * 단방향 관계일 경우에는 주인인 한쪽에만 값을 설정해줘도 되지만,
         * 양방향 관계일 경우에는 양쪽 다 값을 줘야 한다.
         * 양방향이기 때문에 서로간에 정보를 서로가 가지고 있어야 맞다.
         */
        /*
        account.getStudies().add(study);
        study.setOwner(account);
        */
        account.addStudy(study); // 양방향 관계일 경우, 위 두줄을 하나로 묶어서 메소드로 만들어 두는 것이 좋다.

        // entityManager.persist(account); // JPA로 저장하기.

        /**
         * 아래와 같이 하이버네이트의 API인 Session을 꺼내서 사용할 수도 있다.
         */
        Session session = entityManager.unwrap(Session.class);
        session.save(account); // 하이버네이트로 저장하기.
        session.save(study);
    }

}
