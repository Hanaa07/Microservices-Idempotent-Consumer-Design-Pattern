package org.emsi.microservicesidempotentconsumerdemo;

import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroservicesIdempotentConsumerdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicesIdempotentConsumerdemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(RequestService requestService, RequestRepository requestRepository) {
        return args -> {
            Request req = requestService.create(UUID.randomUUID());
            requestService.create(req.getUuid());
            requestService.create(req.getUuid());
            System.out.println("Nb of requests : {}" + requestRepository.count()); // 1, processRequest is idempotent
            req = requestService.start(req.getUuid());
            try {
                req = requestService.start(req.getUuid());
            } catch (InvalidNextStateException ex) {
                System.out.println("Cannot start request twice!");
            }
            req = requestService.complete(req.getUuid());
            System.out.println("Request: {}" + req);
        };
    }

}
