package ru.denfad.studturism.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hostel {
    public int id;
    public String hostel;
    public String university;
    public String city;
    public int price;
    @SerializedName("picture_url")
    public String pictureUrl;
    public String address;
    public String period;
    @SerializedName("conditions_for_organizations")
    public String conditionsForOrganizations;
    @SerializedName("conditions_for_students")
    public String conditionsForStudents;
    public String organization;
    public String phone;
    public String email;
    public List<Rate> rates;
    public List<Service> services;

    public Hostel() {
    }

    public Hostel(int id, String hostel, String university, String city, int price, int imageId, String pictureUrl, String address, String period, String conditionsForOrganizations, String conditionsForStudents, String organization, String phone, String email, List<Rate> rates, List<Service> services) {
        this.id = id;
        this.hostel = hostel;
        this.university = university;
        this.city = city;
        this.price = price;
        this.pictureUrl = pictureUrl;
        this.address = address;
        this.period = period;
        this.conditionsForOrganizations = conditionsForOrganizations;
        this.conditionsForStudents = conditionsForStudents;
        this.organization = organization;
        this.phone = phone;
        this.email = email;
        this.rates = rates;
        this.services = services;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getConditionsForOrganizations() {
        return conditionsForOrganizations;
    }

    public void setConditionsForOrganizations(String conditionsForOrganizations) {
        this.conditionsForOrganizations = conditionsForOrganizations;
    }

    public String getConditionsForStudents() {
        return conditionsForStudents;
    }

    public void setConditionsForStudents(String conditionsForStudents) {
        this.conditionsForStudents = conditionsForStudents;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
