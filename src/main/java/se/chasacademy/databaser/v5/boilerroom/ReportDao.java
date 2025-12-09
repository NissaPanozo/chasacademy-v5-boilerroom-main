package se.chasacademy.databaser.v5.boilerroom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReportDao {
    private final JdbcTemplate jdbc;
    private final ReportDao dao;

    public ReportDao(JdbcTemplate jdbc, ReportDao dao) {
        this.jdbc = jdbc;
        this.dao = dao;
    }

    // Här kommer alla 9 frågor

    //2. Antalet böcker utlånade för alla bibliotek
    public int antalUtLånadeTotalt() {
        String sql = """
                SELECT COUNT(*)
                FROM lån
                WHERE slut_datum IS NULL
                """;
        return jdbc.queryForObject(sql, Integer.class);
    }

    // 5. Top 10 lista på populära böcker för alla bibliotek
    public List<Map<String, Object>> getTop10BooksAllLibraries() {
        String sql = """
            SELECT b.title, COUNT(lo.id) AS loan_count
            FROM books b
            LEFT JOIN loans lo ON b.id = lo.book_id
            GROUP BY b.title
            ORDER BY loan_count DESC
            LIMIT 10
            """;
        return jdbc.queryForList(sql);
    }
}