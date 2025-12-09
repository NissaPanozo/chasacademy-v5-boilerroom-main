package se.chasacademy.databaser.v5.boilerroom;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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
        //Fråga 1.
        int olånade = dao.räknaOlånadeBöcker();
        System.out.println("Antalet olånade böcker: " + olånade);

        //Fråga 2.
        int antalUtlånade = dao.antalUtlånadeTotalt();
        System.out.println("Antalet totalt utlånade böcker: " + antalUtlånade);

        //Fråga 3:
        int antaletBöckerEjUtlånade = dao.antaletBöckerEjUtlånade();
        System.out.println("Antalet böcker ej utlånade för alla bibliotek: " + antaletBöckerEjUtlånade);

        //Fråga 4:
        List<BokLånRäknare> top10BokPerBibliotek = dao.top10BokPerBibliotek();
        System.out.println("Top 10 lista på populär böcker per bibliotek: " + top10BokPerBibliotek);


    }
}
