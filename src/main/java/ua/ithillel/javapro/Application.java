package ua.ithillel.javapro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ua.ithillel.javapro")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
