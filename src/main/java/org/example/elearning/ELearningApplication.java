package org.example.elearning;

import models.entities.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {
        "contorllers",                // (1) your controller package
        "models.services",            // (2) services
        "models.repositories",        // (3) repos
        "models.entities",// (4) entities
        "config"
})
@EntityScan("models.entities")
@EnableJpaRepositories("models.repositories")

public class ELearningApplication
{


    public static void main(String[] args)
    {

        SpringApplication.run(ELearningApplication.class, args);
    }

}
