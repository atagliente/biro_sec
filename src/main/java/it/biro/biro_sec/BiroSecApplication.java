package it.biro.biro_sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class BiroSecApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiroSecApplication.class, args);
	}

}
