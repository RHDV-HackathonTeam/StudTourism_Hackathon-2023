package ru.denfad.studturism.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsPost {

    public String source;
    public String text;
    public String href;
    @SerializedName("picture_url")
    public String pictureUrl;
    public int likesCount;
    public String title;

    public NewsPost() {

    }

    public NewsPost(String source, String text, String href, String pictureUrl, int likesCount, String title) {
        this.source = source;
        this.text = text;
        this.href = href;
        this.pictureUrl = pictureUrl;
        this.likesCount = likesCount;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
