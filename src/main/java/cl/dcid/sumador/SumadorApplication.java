package cl.dcid.sumador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableRetry
@EnableWebMvc
@EnableAsync
@EntityScan("cl.dcid.sumador.model")
public class SumadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SumadorApplication.class, args);
	}

}
