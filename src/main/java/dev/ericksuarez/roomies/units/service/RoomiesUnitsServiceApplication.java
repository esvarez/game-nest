package dev.ericksuarez.roomies.units.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RoomiesUnitsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomiesUnitsServiceApplication.class, args);
	}

}
