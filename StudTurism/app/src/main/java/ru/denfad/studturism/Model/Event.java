package ru.denfad.studturism.Model;

import com.google.gson.annotations.SerializedName;

public class Event {

    public String href;
    public String lab;
    public String city;
    @SerializedName("description_of_organization")
    public String descriptionOrg;
    @SerializedName("picture_url")
    public String pictureUrl;


    public Event(String href, String lab, String city, String descriptionOrg, String pictureUrl) {
        this.href = href;
        this.lab = lab;
        this.city = city;
        this.descriptionOrg = descriptionOrg;
        this.pictureUrl = pictureUrl;
    }
}
