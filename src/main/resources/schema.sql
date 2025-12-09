-- Skapa era tabeller här
CREATE TABLE IF NOT EXISTS bibliotek (
    biblioteks_id  SERIAL PRIMARY KEY,
    namn           VARCHAR(100) NOT NULL,
    adress         VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS Bibliotekarie (
    Bibliotekarie_id   SERIAL PRIMARY KEY,
    Namn               VARCHAR(150) NOT NULL,
    Biblioteks_id      INT NOT NULL REFERENCES Bibliotek (Biblioteks_id)
);

CREATE TABLE IF NOT EXISTS Medlem (
    Medlems_id      SERIAL PRIMARY KEY,
    Namn            VARCHAR(150) NOT NULL,
    Personnummer    VARCHAR(20) UNIQUE NOT NULL,
    Biblioteks_id   INT NOT NULL REFERENCES Bibliotek (Biblioteks_id)
);

CREATE TABLE IF NOT EXISTS Kategori (
    Kategori_id     SERIAL PRIMARY KEY,
    Namn            VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Författare (
    Författar_id    SERIAL PRIMARY KEY,
    Namn            VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS Bok (
    Bok_id          SERIAL PRIMARY KEY,
    Titel           VARCHAR(200) NOT NULL,
    ISBN            VARCHAR(50),
    Utgivningsår    INT,
    Kategori_id     INT REFERENCES Kategori (Kategori_id),
    Författar_id    INT REFERENCES Författare (Författar_id)
);

CREATE TABLE IF NOT EXISTS Exemplar (
    Exemplar_id     SERIAL PRIMARY KEY,
    Status          VARCHAR(50) NOT NULL,
    Bok_id          INT NOT NULL REFERENCES Bok (Bok_id),
    Biblioteks_id   INT NOT NULL REFERENCES Bibliotek (Biblioteks_id)
);

CREATE TABLE IF NOT EXISTS Lån (
    Lån_id             SERIAL PRIMARY KEY,
    Start_datum        DATE NOT NULL,
    Slut_datum         DATE,
    Medlems_id         INT NOT NULL REFERENCES Medlem (Medlems_id),
    Exemplar_id        INT NOT NULL REFERENCES Exemplar (Exemplar_id),
    Bibliotekarie_id   INT REFERENCES Bibliotekarie (Bibliotekarie_id),
    Biblioteks_id      INT NOT NULL REFERENCES Bibliotek (Biblioteks_id)
);
