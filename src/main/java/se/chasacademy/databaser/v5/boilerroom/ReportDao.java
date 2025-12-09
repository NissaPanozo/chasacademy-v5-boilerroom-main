package se.chasacademy.databaser.v5.boilerroom;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReportDao {
    private final JdbcTemplate jdbc;
    private final BokLånRäknare bokRäknare;

    public ReportDao(JdbcTemplate jdbc, BokLånRäknare bokRäknare) {
        this.jdbc = jdbc;
        this.bokRäknare = bokRäknare;
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
    public int antaletBöckerEjUtlånade() {
        String sql = """
            SELECT COUNT(*) 
            FROM exemplar e
            LEFT JOIN lån l
              ON l.exemplar_id = e.exemplar_id
             AND l.slut_datum IS NULL
            WHERE l.lån_id IS NULL
            """;

        return jdbc.queryForObject(sql, Integer.class);
    }


    // 4. * Top 10 lista på populär böcker per bibliotek.
    public List<BokLånRäknare> top10BokPerBibliotek() {
        String sql = """
        SELECT bl.namn AS bibliotek, b.titel AS bok, COUNT(l.ån_id) AS antal_lån
        FROM lån l
        JOIN exemplar e ON l.exemplar_id = e.exemplar_id
        JOIN bok b ON e.bok_id = b.bok_id
        JOIN bibliotek bl ON e.biblioteks_id = bl.biblioteks_id
        GROUP BY bl.namn, b.titel
        ORDER BY bl.namn, antal_lån DESC
        LIMIT 10;
    """;

        return jdbc.query(sql, (rs, rowNum) ->
                new BokLånRäknare(
                        rs.getString("titel"),
                        rs.getString("bibliotek"),
                        rs.getInt("antal_lån")
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