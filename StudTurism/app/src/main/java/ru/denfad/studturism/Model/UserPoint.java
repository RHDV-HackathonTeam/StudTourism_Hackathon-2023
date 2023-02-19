package ru.denfad.studturism.Model;

public class UserPoint {

    public int userId;

    public double X;
    public double Y;
    public int likeCount = 0;
    public String name;
    public String description;
    public int imageId;
    public String imageUri;

    public UserPoint(String name, double x, double y, String description, int imageId) {
        this.name = name;
        X = x;
        Y = y;
        this.description = description;
        this.imageId = imageId;
    }

    public UserPoint( double x, double y, int likeCount, String description, String imageUri) {

        X = x;
        Y = y;
        this.likeCount = likeCount;
        this.description = description;
        this.imageUri = imageUri;
    }
}
