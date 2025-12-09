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

    // Här kommer alla 9 frågor

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

    // 6. Se hur många medborgare som lånat en eller flera böcker
    public int getCitizensWhoLoanedBooks() {
        String sql = "SELECT COUNT(DISTINCT citizen_id) FROM loans";
        return jdbc.queryForObject(sql, Integer.class);
    }

    // 7. Se hur många böcker som aldrig blivit utlånade
    public int getBooksNeverLoaned() {
        String sql = "SELECT COUNT(*) FROM books WHERE id NOT IN (SELECT book_id FROM loans)";
        return jdbc.queryForObject(sql, Integer.class);
    }

    // 8. Se antalet böcker per kategori för alla bibliotek
    public List<Map<String, Object>> getBookCountPerCategoryAllLibraries() {
        String sql = """
            SELECT c.name AS category, COUNT(b.id) AS book_count
            FROM books b
            JOIN categories c ON b.category_id = c.id
            GROUP BY c.name
            """;
        return jdbc.queryForList(sql);
    }

    // 9. Se antalet böcker per kategori för varje bibliotek
    public List<Map<String, Object>> getBookCountPerCategoryPerLibrary() {
        String sql = """
            SELECT l.name AS library_name, c.name AS category, COUNT(b.id) AS book_count
            FROM books b
            JOIN libraries l ON b.library_id = l.id
            JOIN categories c ON b.category_id = c.id
            GROUP BY l.name, c.name
            """;
        return jdbc.queryForList(sql);
    }
}