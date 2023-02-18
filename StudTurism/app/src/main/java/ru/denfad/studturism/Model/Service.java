package ru.denfad.studturism.Model;

public class Service {

    public int id;
    public String name;
    public String description;
    public int price;

    public Service(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
