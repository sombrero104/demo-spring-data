package me.sombrero.demospringdata;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * [ 프로그래밍으로 빈 등록하기 ]
 * 아래처럼 빈을 등록하는 프로그램을 작성한 후
 * @Configuration이 있는 곳에서 @Import(SombreroRegistrar.class)를 붙여주면 된다.
 * 그럼 내가 만든 Sombrero 클래스가 빈으로 등록되게 된다.
 */
public class SombreroRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(Sombrero.class); // 빈으로 만들 클래스.
        beanDefinition.getPropertyValues().add("name", "sombrero104"); // name 변수값이 여기서 덮어써진다.
        /**
         * 스프링 데이터 JPA에서는 위의 과정이 좀 더 복잡할 뿐이다.
         * 어떠한 인터페이스를 상속받는 클래스를 다 찾아서 그 클래스에 대한 beanDefinition을 다 만들어서
         * 최종적으로는 그 클래스 이름에 해당하는 beanDefinition을 등록해준다.
         */

        registry.registerBeanDefinition("sombrero", beanDefinition); // 빈 이름을 주면 된다.
    }

}
