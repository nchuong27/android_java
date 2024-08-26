package com.example.java3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private int id;
    private int productId;
    private int userId;
    private int quantity;
    private String price;
    private String size;
    private String productName;
    private String productImage;
    private boolean selected;

    public CartItem(int id, int productId, int userId, int quantity, String price, String size, String productName, String productImage) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
        this.productName = productName;
        this.productImage = productImage;
    }

    // Getter và Setter
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getPriceAsDouble() {
        try {
            String cleanedPrice = price.replace(".", "")
                    .replace(",", ".").replace(" đ", "").trim();
            return Double.parseDouble(cleanedPrice);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Constructor Parcelable
    protected CartItem(Parcel in) {
        id = in.readInt();
        productId = in.readInt();
        userId = in.readInt();
        quantity = in.readInt();
        price = in.readString();
        size = in.readString();
        productName = in.readString();
        productImage = in.readString();
        selected = in.readByte() != 0;  // selected là boolean nên ta dùng in.readByte() != 0
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(productId);
        dest.writeInt(userId);
        dest.writeInt(quantity);
        dest.writeString(price);
        dest.writeString(size);
        dest.writeString(productName);
        dest.writeString(productImage);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };
}
