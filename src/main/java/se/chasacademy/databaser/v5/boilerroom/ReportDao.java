package se.chasacademy.v5.boilerroom.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReportDao {
    private final JdbcTemplate jdbc;

    public ReportDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Här kommer alla 9 frågor//
    //3. fråga: Antalet böcker ej utlånade för alla bibliotek
    public int countUnloanedBooksAllLibraries() {
        String sql = """
            SELECT COUNT(*) 
            FROM exemplar e
            LEFT JOIN lan l
              ON l.exemplar_id = e.exemplar_id
             AND l.slut_datum IS NULL
            WHERE l.lan_id IS NULL
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