package ru.denfad.studturism.Model;

public class Filter {

    public String region;
    public String city;
    public String flatType;
    public String bedCount;
    public String food;


    public Filter() {
    }

    public Filter(String region, String city, String flatType, String bedCount, String food) {
        this.region = region;
        this.city = city;
        this.flatType = flatType;
        this.bedCount = bedCount;
        this.food = food;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public String getBedCount() {
        return bedCount;
    }

    public void setBedCount(String bedCount) {
        this.bedCount = bedCount;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
