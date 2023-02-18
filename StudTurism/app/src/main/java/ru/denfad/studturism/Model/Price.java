package ru.denfad.studturism.Model;

public class Price {
    public int id;
    public String name;
    public String description;
    public int price;

    public Price(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
