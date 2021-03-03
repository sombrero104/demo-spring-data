package me.sombrero.demospringdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// @EnableJpaRepositories
@Import(SombreroRegistrar.class) // SombreroRegistrar에 내가 프로그래밍한 부분에 의해 Sombrero 클래스가 빈으로 등록되게 된다.
public class DemoSpringDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringDataApplication.class, args);
    }

}
