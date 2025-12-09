package se.chasacademy.v5.boilerroom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    public LibraryApplication() {
        /* Tom konstruktor för framtiden. */
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Välkommen till Bibliotek Z");
    }
}