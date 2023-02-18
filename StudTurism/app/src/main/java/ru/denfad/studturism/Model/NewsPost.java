package ru.denfad.studturism.Model;

import java.util.List;

public class NewsPost {

    public int id;
    public String text;
    public List<Integer> imageIds;
    public int likesCount;

    public NewsPost(String text, List<Integer> imageIds, int likesCount) {
        this.text = text;
        this.imageIds= imageIds;
        this.likesCount = likesCount;
    }
}
