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
        SELECT b.titel AS bok, COUNT(l.id) AS antal_utlåningar
        FROM bok b
        LEFT JOIN lån l ON b.id = l.bok_id
        GROUP BY b.titel
        ORDER BY antal_utlåningar DESC
        LIMIT 10
    """;
            return jdbc.queryForList(sql);
        }

        // 6. Se hur många medborgare som lånat en eller flera böcker
        public int getMedborgareSomLånatBöcker() {
            String sql = "SELECT COUNT(DISTINCT medlems_id) FROM lån";
            return jdbc.queryForObject(sql, Integer.class);
        }

        // 7. Se hur många böcker som aldrig blivit utlånade
        public int getBooksNeverLoaned() {
            String sql = """
        SELECT COUNT(*) 
        FROM bok b
        WHERE b.id NOT IN (
            SELECT e.bok_id 
            FROM lån l
            JOIN exemplar e ON l.exemplar_id = e.id
        )
    """;
            return jdbc.queryForObject(sql, Integer.class);
        }

        // 8. Se antalet böcker per kategori för alla bibliotek
        public List<Map<String, Object>> getBookCountPerCategoryAllLibraries() {
            String sql = """
        SELECT k.namn AS kategori, COUNT(b.id) AS antal_bocker
        FROM bok b
        JOIN kategori k ON b.kategori_id = k.id
        GROUP BY k.namn
        ORDER BY k.namn
    """;
            return jdbc.queryForList(sql);
        }

        // 9. Se antalet böcker per kategori för varje bibliotek
        public List<Map<String, Object>> getBookCountPerCategoryPerLibrary() {
            String sql = """
        SELECT 
            lib.namn AS bibliotek,
            k.namn AS kategori,
            COUNT(b.id) AS antal_bocker
        FROM exemplar e
        JOIN bok b ON e.bok_id = b.id
        JOIN kategori k ON b.kategori_id = k.id
        JOIN bibliotek lib ON e.biblioteks_id = lib.id
        GROUP BY lib.namn, k.namn
        ORDER BY lib.namn, k.namn
    """;

            return jdbc.queryForList(sql);
        }


    }