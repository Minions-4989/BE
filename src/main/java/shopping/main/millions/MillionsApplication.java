package shopping.main.millions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MillionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MillionsApplication.class, args);
    }

}
