package ru.denfad.studturism.Model;

public class Service {

    public String service;
    public String description;
    public int price;

    public Service() {
    }

    public Service(String service, String description, int price) {
        this.service = service;
        this.description = description;
        this.price = price;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
