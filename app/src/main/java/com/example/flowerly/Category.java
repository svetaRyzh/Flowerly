package com.example.flowerly;

public class Category {
    private String name;
    private int imageRes; ;

    public Category(String name, int imageRes){
        this.name = name;
        this.imageRes = imageRes;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setImageRes(int iconRes){
        this.imageRes = iconRes;
    }
    public int getImageRes(){
        return imageRes;
    }
}
