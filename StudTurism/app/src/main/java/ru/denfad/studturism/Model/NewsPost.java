package ru.denfad.studturism.Model;

import java.util.List;

public class NewsPost {

    public int id;
    public String source;
    public String text;
    public String href;
    public List<Integer> imageIds;
    public List<String> images;
    public int likesCount;

    public NewsPost(String text, List<Integer> imageIds, int likesCount) {
        this.text = text;
        this.imageIds= imageIds;
        this.likesCount = likesCount;
    }

    public NewsPost() {

    }

    public NewsPost(int id, String source, String text, String href, List<String> images, int likesCount) {
        this.id = id;
        this.source = source;
        this.text = text;
        this.href = href;
        this.images = images;
        this.likesCount = likesCount;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Integer> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }
}
