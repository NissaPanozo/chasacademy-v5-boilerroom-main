package se.chasacademy.databaser.v5.boilerroom;

public class BokLånRäknare {
    public String bibliotek;
    public String titel;
    public int antalLån;

    public BokLånRäknare(String bibliotek, String titel, int antalLån) {
        this.bibliotek = bibliotek;
        this.titel = titel;
        this.antalLån = antalLån;
    }
}
