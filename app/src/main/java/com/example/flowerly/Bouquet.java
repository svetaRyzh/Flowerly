package com.example.flowerly;

public class Bouquet {
    private String name;
    private int price;
    private String imageUrl;
    private String category;

    public Bouquet(String name, int price, String imageUrl, String category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName(){
        return name;
    }
    public int getPrice(){
        return price;
    }
    public String getImageUrl(){
        return imageUrl;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPrice(int price){
        this.price = price;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
}


