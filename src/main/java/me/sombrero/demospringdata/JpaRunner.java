package me.sombrero.demospringdata;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

        // entityManager.persist(account); // JPA로 저장하기.

        /**
         * 아래와 같이 하이버네이트의 API인 Session을 꺼내서 사용할 수도 있다.
         */
        Session session = entityManager.unwrap(Session.class);
        session.save(account); // 하이버네이트로 저장하기.
    }

}