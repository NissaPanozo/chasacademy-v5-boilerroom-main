package se.chasacademy.databaser.v5.boilerroom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StatisticsRunner implements CommandLineRunner {
    private final JdbcTemplate jdbc;

    public StatisticsRunner (JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public void run (String... args) throws Exception {
        System.out.println("");
    }
}
