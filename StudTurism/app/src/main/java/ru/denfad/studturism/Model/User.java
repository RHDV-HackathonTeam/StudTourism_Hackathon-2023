package ru.denfad.studturism.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class User {
    

    public String username;
    public String name;
    public String surname;
    @SerializedName("family_name")
    public String secondname;
    public String password;
    public String phone;
    public String email;
    public String level;
    public List<String> tags;
    public String experience;
    public String role;
    public List<String> notifications = new ArrayList<>();
    @SerializedName("ref_link")
    public String redLink;

    public User() {
    }


    public User(String username, String name, String surname, String secondname, String password, String phone, String email, String level, List<String> tags, String experience, String role, List<String> notifications, String redLink) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.secondname = secondname;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.level = level;
        this.tags = tags;
        this.experience = experience;
        this.role = role;
        this.notifications = notifications;
        this.redLink = redLink;
    }

    public User(String username, String name, String surname, String secondname, String password, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.secondname = secondname;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLevel(String level) {
        this.level = level;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
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

    public String getLevel() {
        return level;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public String getRedLink() {
        return redLink;
    }

    public void setRedLink(String redLink) {
        this.redLink = redLink;
    }
}
