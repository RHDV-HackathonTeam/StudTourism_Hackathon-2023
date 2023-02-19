package ru.denfad.studturism.Model;

public class Book {


    public int hostelId;
    public String FIO;
    public int guestCount;
    public String visitDate;
    public String exitDate;
    public String phone;
    public String email;
    public String town;
    public String hostel;

    public Book(String visitDate, String exitDate, String town, String hostel) {
        this.visitDate = visitDate;
        this.exitDate = exitDate;
        this.town = town;
        this.hostel = hostel;
    }

    public Book(int hostelId, String FIO, int guestCount, String visitDate, String exitDate, String phone, String email, String town, String hostel) {
        this.hostelId = hostelId;
        this.FIO = FIO;
        this.guestCount = guestCount;
        this.visitDate = visitDate;
        this.exitDate = exitDate;
        this.phone = phone;
        this.email = email;
        this.town = town;
        this.hostel = hostel;
    }
}
