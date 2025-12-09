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

    // Här kommer alla 9 frågor//
    // Fråga 1: Antalet böcker ej utlånade för alla bibliotek.
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

    //2. Antalet böcker utlånade för alla bibliotek
    public int antalUtLånadeTotalt() {
        String sql = """
                SELECT COUNT(*)
                FROM lån
                WHERE slut_datum IS NULL
                """;
        return jdbc.queryForObject(sql, Integer.class);
    }

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


    // 4. * Top 10 lista på populär böcker per bibliotek.
    public List<BookLoanCount> getTop10BooksPerLibrary() {
        String sql = """
        SELECT b.titel, lib.namn AS bibliotek, COUNT(l.id) AS antal_utlan
        FROM lån l
        JOIN exemplar e ON l.exemplar_id = e.id
        JOIN bok b ON e.bok_id = b.id
        JOIN bibliotek lib ON e.biblioteks_id = lib.id
        GROUP BY b.titel, lib.namn
        ORDER BY lib.namn, antal_utlan DESC
        LIMIT 10
    """;

        return jdbc.query(sql, (rs, rowNum) ->
                new BookLoanCount(
                        rs.getString("titel"),
                        rs.getString("bibliotek"),
                        rs.getInt("antal_utlan")
                )
        );


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