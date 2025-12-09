package se.chasacademy.databaser.v5.boilerroom.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbc;

    public DatabaseSeeder(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void run(String... args) throws Exception {

        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM bibliotek",
                Integer.class
        );

        // Om databasen redan är fylld → hoppa över
        if (count != null && count > 0) {
            return;
        }

        seed();
    }

    private void seed() {

        // --- Bibliotek ---
        jdbc.update("INSERT INTO bibliotek (namn, adress) VALUES ('Stadsbiblioteket', 'Centrum 1')");
        jdbc.update("INSERT INTO bibliotek (namn, adress) VALUES ('Södermalms bibliotek', 'Södergatan 4')");

        // --- Kategori ---
        jdbc.update("INSERT INTO kategori (namn) VALUES ('Fantasy')");
        jdbc.update("INSERT INTO kategori (namn) VALUES ('Sci-Fi')");
        jdbc.update("INSERT INTO kategori (namn) VALUES ('Deckare')");

        // --- Författare ---
        jdbc.update("INSERT INTO författare (namn) VALUES ('J.R.R Tolkien')");
        jdbc.update("INSERT INTO författare (namn) VALUES ('Isaac Asimov')");
        jdbc.update("INSERT INTO författare (namn) VALUES ('Stieg Larsson')");

        // --- Böcker ---
        jdbc.update("INSERT INTO bok (titel, isbn, utgivningsår, kategori_id, författar_id) VALUES ('Sagan om Ringen', '12345', 1954, 1, 1)");
        jdbc.update("INSERT INTO bok (titel, isbn, utgivningsår, kategori_id, författar_id) VALUES ('Stiftelsen', '54321', 1951, 2, 2)");
        jdbc.update("INSERT INTO bok (titel, isbn, utgivningsår, kategori_id, författar_id) VALUES ('Män som hatar kvinnor', '99999', 2005, 3, 3)");

        // --- Exemplar ---
        jdbc.update("INSERT INTO exemplar (status, bok_id, biblioteks_id) VALUES ('tillgänglig', 1, 1)");
        jdbc.update("INSERT INTO exemplar (status, bok_id, biblioteks_id) VALUES ('tillgänglig', 1, 1)");
        jdbc.update("INSERT INTO exemplar (status, bok_id, biblioteks_id) VALUES ('tillgänglig', 2, 2)");
        jdbc.update("INSERT INTO exemplar (status, bok_id, biblioteks_id) VALUES ('tillgänglig', 3, 1)");

        // --- Medlem ---
        jdbc.update("INSERT INTO medlem (namn, personnummer, biblioteks_id) VALUES ('Anna Andersson', '19900101-1234', 1)");
        jdbc.update("INSERT INTO medlem (namn, personnummer, biblioteks_id) VALUES ('Bertil Berg', '19850412-5678', 1)");
        jdbc.update("INSERT INTO medlem (namn, personnummer, biblioteks_id) VALUES ('Carla Carlsson', '19771212-9999', 2)");

        // --- Lån ---
        jdbc.update("INSERT INTO lån (start_datum, slut_datum, medlems_id, exemplar_id, biblioteks_id) VALUES ('2025-01-01', NULL, 1, 1, 1)");
        jdbc.update("INSERT INTO lån (start_datum, slut_datum, medlems_id, exemplar_id, biblioteks_id) VALUES ('2025-01-02', '2025-01-10', 2, 2, 1)");
        jdbc.update("INSERT INTO lån (start_datum, slut_datum, medlems_id, exemplar_id, biblioteks_id) VALUES ('2025-01-05', NULL, 3, 3, 2)");
    }
}