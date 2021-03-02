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
        Session session = entityManager.unwrap(Session.class);
        session.save(account); // 하이버네이트로 저장하기.
        session.save(study);

        /**
         * 위에서 이미 저장된(캐시된) 데이터가 있기 때문에 DB에서 가져오지 않는다. (select 쿼리가 발생하지 않는다.)
         */
        Account anton = session.load(Account.class, account.getId());
    }

}
