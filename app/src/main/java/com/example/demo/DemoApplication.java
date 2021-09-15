package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(DemoService demoService) {
        return (args) -> {
            ExecutorService sseMvcExecutor = Executors.newSingleThreadExecutor();
            sseMvcExecutor.execute(() -> {
            	Random random = new Random();
				for (int i = 0; true; i++) {
					try {
						String user = random.nextBoolean() ? "user1" : "user2";
						demoService.publish(user, user + " - SSE MVC - " + LocalTime.now().toString());
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
            });
        };
    }

}


