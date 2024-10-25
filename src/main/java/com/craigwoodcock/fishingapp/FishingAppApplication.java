package com.craigwoodcock.fishingapp;

import com.craigwoodcock.fishingapp.exception.UserAlreadyExistsException;
import com.craigwoodcock.fishingapp.model.Role;
import com.craigwoodcock.fishingapp.model.User;
import com.craigwoodcock.fishingapp.repository.UserRepository;
import com.craigwoodcock.fishingapp.utils.DateFormatter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@SpringBootApplication
public class 	FishingAppApplication {
private static final Logger log = Logger.getLogger(FishingAppApplication.class.getName());
	public static void main(String[] args) {
		SpringApplication.run(FishingAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
					if (!userRepository.existsByUsername("craig123")){
						User user = new User();
						log.info("cr");
						user.setUsername("craig123");
						user.setName("craig");
						user.setEmail("craig@gmail.com");
						user.setPassword(passwordEncoder.encode("craig"));
						user.setCreatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
						user.setUpdatedAt(DateFormatter.formatLocalDateTime(LocalDateTime.now()));
						user.setRole(Role.USER);
						userRepository.save(user);
					}else {
						log.info("This user already exists");
						log.info("starting application");
					}
			}
		};
	}
}
