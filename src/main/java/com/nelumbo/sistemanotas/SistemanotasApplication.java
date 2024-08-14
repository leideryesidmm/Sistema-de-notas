package com.nelumbo.sistemanotas;

import com.nelumbo.sistemanotas.seeders.Seeder;
import com.nelumbo.sistemanotas.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class SistemanotasApplication {
	private final Seeder seeder;
	private final StudentService studentService;

	public static void main(String[] args) {
		SpringApplication.run(SistemanotasApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(){
		return args -> {if(!studentService.existsByStudents())seeder.seed();};
	}
}
