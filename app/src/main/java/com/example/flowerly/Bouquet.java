package com.example.flowerly;

public class Bouquet {
    private String name;
    private int price;
    private int imageRes;

    public Bouquet(String name, int price, int imageRes){
        this.name = name;
        this.imageRes = imageRes;
        this.price = price;
    }

    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
    public int getImageRes(){
        return imageRes;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setImageRes(int imageRes){
        this.imageRes = imageRes;
    }
}


