package com.example.combirabbit.models;

public class ImagePlace {
    private int nImage;
    private int nDegree;

    public ImagePlace(int newImage, int newDegree){
        this.nImage = newImage;
        this.nDegree = newDegree;
    }

    public int getImage() {
        return nImage;
    }

    public void setImage(int newImage) {
        this.nImage = newImage;
    }

    public int getDegree() {
        return nDegree;
    }

    public void setDegree(int newDegree) {
        this.nDegree = newDegree;
    }
}
