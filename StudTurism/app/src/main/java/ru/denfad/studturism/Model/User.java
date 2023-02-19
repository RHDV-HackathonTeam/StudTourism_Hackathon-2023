package ru.denfad.studturism.Model;

import java.util.List;

public class User {
    
    public int id;
    public String name;
    public String surname;
    public String secondname;
    public String phone;
    public String email;
    public int level;
    public List<String> tags;

    public User() {
    }

    public User(int id, String name, String surname, String secondname, String phone, String email, int level, List<String> tags) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondname = secondname;
        this.phone = phone;
        this.email = email;
        this.level = level;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
