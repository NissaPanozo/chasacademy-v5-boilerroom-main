package se.chasacademy.databaser.v5.boilerroom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StatisticsRunner implements CommandLineRunner {
    private final JdbcTemplate jdbc;
    private final ReportDao dao;

    public StatisticsRunner (JdbcTemplate jdbc, ReportDao dao){
        this.jdbc = jdbc;
        this.dao = dao;
    }

    @Override
    public void run (String... args) throws Exception {
        System.out.println("");
        int antalUtLånade = dao.antalUtlånadeTotalt();
        System.out.println("Antalet totalt utlånade böcker: " + antalUtLånade);
    }
}
