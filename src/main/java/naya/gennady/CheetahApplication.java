package naya.gennady;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CheetahApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheetahApplication.class, args);
	}
}
