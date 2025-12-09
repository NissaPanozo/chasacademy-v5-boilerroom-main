package se.chasacademy.databaser.v5.boilerroom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReportDao {
    private final JdbcTemplate jdbc;

    public ReportDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Här kommer alla 9 frågor

    //2.
    public int antalUtlånadeTotalt() {
        String sql = """
                SELECT COUNT(*)
                FROM lån
                WHERE slut_datum IS NULL
                """;
        return jdbc.queryForObject(sql, Integer.class);
    }
}