package ru.denfad.studturism.Model;

import com.google.gson.annotations.SerializedName;

public class Book {


    @SerializedName("hostel_id")
    public String hostelId;
    public String fio;
    @SerializedName("guest_count")
    public String guestCount;
    @SerializedName("entry")
    public String visitDate;
    @SerializedName("depart")
    public String exitDate;
    @SerializedName("phone_number")
    public String phone;
    public String email;
    public String username;
    public String status;

    public Book() {
    }

    public Book(String hostelId, String fio, String guestCount, String visitDate, String exitDate, String phone, String email, String username, String status) {
        this.hostelId = hostelId;
        this.fio = fio;
        this.guestCount = guestCount;
        this.visitDate = visitDate;
        this.exitDate = exitDate;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.status = status;
    }

    public String getHostelId() {
        return hostelId;
    }

    public void setHostelId(String hostelId) {
        this.hostelId = hostelId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(String guestCount) {
        this.guestCount = guestCount;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getExitDate() {
        return exitDate;
    }

    public void setExitDate(String exitDate) {
        this.exitDate = exitDate;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
