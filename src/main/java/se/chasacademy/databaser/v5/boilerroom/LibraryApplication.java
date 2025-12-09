package se.chasacademy.v5.boilerroom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {
private final DatabaseSeeder seeder;

    public LibraryApplication(DatabaseSeeder seeder) {
        /* Tom konstruktor för framtiden. */
        this.seeder = seeder;
    }

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Välkommen till Bibliotek Z");
    }
}