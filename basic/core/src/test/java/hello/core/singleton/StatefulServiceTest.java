package hello.core.singleton;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //Thread A : A 사용자 10,000원 주문
        int userAPrice = statefulService1.order("userA", 10_000);
        //Thread B : B 사용자 20,000 주문
        int userBPrice = statefulService2.order("userB", 20_000);

        //Thread A : 사용자A 주문 금액 조회
        System.out.println("price = " + userAPrice);

        org.assertj.core.api.Assertions.assertThat(userBPrice).isEqualTo(20_000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}