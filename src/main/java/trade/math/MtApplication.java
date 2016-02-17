package trade.math;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import trade.math.service.BggIdToTitleService;

@SpringBootApplication
public class MtApplication {
    public static void main(String[] args) {
        SpringApplication.run(MtApplication.class, args);
    }
}