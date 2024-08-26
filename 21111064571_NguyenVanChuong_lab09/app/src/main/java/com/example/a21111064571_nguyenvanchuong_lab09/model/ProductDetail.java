package com.example.a21111064571_nguyenvanchuong_lab09.model;

public class ProductDetail {
    private int id;
    private int productId;
    private String description;
    private double price;
    private String color;

    public ProductDetail(int id, int productId, String description, double price, String color) {
        this.id = id;
        this.productId = productId;
        this.description = description;
        this.price = price;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
