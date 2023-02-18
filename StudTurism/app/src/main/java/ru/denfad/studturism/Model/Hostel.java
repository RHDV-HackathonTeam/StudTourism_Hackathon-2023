package ru.denfad.studturism.Model;

public class Hostel {

    public int id;
    public String name;
    public String organization;
    public String town;
    public int minDayCount;
    public int maxDayCount;
    public int price;
    public int imageId;


    public Hostel(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public Hostel(int id, String name, String organization, String town, int minDayCount, int maxDayCount, int price, int imageId) {
        this.id = id;
        this.name = name;
        this.organization = organization;
        this.town = town;
        this.minDayCount = minDayCount;
        this.maxDayCount = maxDayCount;
        this.price = price;
        this.imageId = imageId;
    }
}
