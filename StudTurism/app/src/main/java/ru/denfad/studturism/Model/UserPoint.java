package ru.denfad.studturism.Model;

public class UserPoint {

    public int id;
    public int userId;
    public double X;
    public double Y;
    public int likeCount = 0;
    String description;
    public int imageId;
    public String imageUri;

    public UserPoint(double x, double y, String description, int imageId) {
        X = x;
        Y = y;
        this.description = description;
        this.imageId = imageId;
    }

    public UserPoint(int id, double x, double y, int likeCount, String description, String imageUri) {
        this.id = id;
        X = x;
        Y = y;
        this.likeCount = likeCount;
        this.description = description;
        this.imageUri = imageUri;
    }
}
