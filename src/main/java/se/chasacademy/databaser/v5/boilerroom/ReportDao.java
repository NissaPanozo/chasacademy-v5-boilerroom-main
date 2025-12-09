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
    public int antalUtlånadeTotalt() {
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


    }
}