package com.example.sitcinemax;

public class Ticket {
    String NameUser, SICUser, PhoneNumber, MovieName, Seats, SIC2, PhoneNumber2, Name2;

    public Ticket() {
    }

    public Ticket(String nameUser, String SICUser, String phoneNumber, String movieName, String seats, String SIC2, String phoneNumber2, String name2) {
        NameUser = nameUser;
        this.SICUser = SICUser;
        PhoneNumber = phoneNumber;
        MovieName = movieName;
        Seats = seats;
        this.SIC2 = SIC2;
        PhoneNumber2 = phoneNumber2;
        Name2 = name2;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getSICUser() {
        return SICUser;
    }

    public void setSICUser(String SICUser) {
        this.SICUser = SICUser;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getSeats() {
        return Seats;
    }

    public void setSeats(String seats) {
        Seats = seats;
    }

    public String getSIC2() {
        return SIC2;
    }

    public void setSIC2(String SIC2) {
        this.SIC2 = SIC2;
    }

    public String getPhoneNumber2() {
        return PhoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        PhoneNumber2 = phoneNumber2;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }
}