package ru.denfad.studturism.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {

    public String username;
    public String text;
    public String likes;
    @SerializedName("imgs_refs")
    public List<String> imgRefs;

    public Review(String username, String text, String likes, List<String> imgRefs) {
        this.username = username;
        this.text = text;
        this.likes = likes;
        this.imgRefs = imgRefs;
    }

    public Review() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public List<String> getImgRefs() {
        return imgRefs;
    }

    public void setImgRefs(List<String> imgRefs) {
        this.imgRefs = imgRefs;
    }
}
