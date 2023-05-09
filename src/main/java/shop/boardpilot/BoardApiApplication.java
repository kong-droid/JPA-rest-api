package shop.boardpilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@ComponentScan(basePackages = {"shop.boardpilot"})
@PropertySource(value = "classpath:/application-${spring.profiles.active}.yml")
public class BoardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApiApplication.class, args);
    }

}
