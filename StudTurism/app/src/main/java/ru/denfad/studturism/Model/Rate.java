package ru.denfad.studturism.Model;

import com.google.gson.annotations.SerializedName;

public class Rate {

    @SerializedName("bed_count")
    public String bedCount;
    @SerializedName("room_type")
    public String roomType;
    public String description;
    public String price;

    public Rate() {
    }

    public Rate(String bedCount, String roomType, String description, String price) {
        this.bedCount = bedCount;
        this.roomType = roomType;
        this.description = description;
        this.price = price;
    }

    public String getBedCount() {
        return bedCount;
    }

    public void setBedCount(String bedCount) {
        this.bedCount = bedCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
