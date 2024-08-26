package com.example.java3.model;
import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable  {
    private int id_sp;
    private String img_sp;
    private String IMG_nb;
    private String name_sp;
    private String description_sp;
    private String price_sp;
    private String gia_goc;
    private int quantity_sp;
    private String outstanding;
    private int giam_gia;
    private String id_cate;
    private float rating;
    private int reviewCount;

    public Product() {
        // Constructor mặc định không có tham số
    }

    public Product(int id_sp, String img_sp, String IMG_nb, String name_sp, String description_sp,
                   String price_sp, int quantity_sp, String outstanding, String id_cate, int giam_gia, String gia_goc) {  // Sửa thành boolean
        this.id_sp = id_sp;
        this.img_sp = img_sp;
        this.IMG_nb = IMG_nb;
        this.name_sp = name_sp;
        this.description_sp = description_sp;
        this.price_sp = price_sp;
        this.quantity_sp = quantity_sp;
        this.outstanding = outstanding;
        this.id_cate = id_cate;
        this.giam_gia = giam_gia;
        this.gia_goc = gia_goc;

    }

    public Product(String img_sp, String name_sp, String price_sp, int giam_gia,float rating, int reviewCount,String description_sp,int quantity_sp,int id_sp) {
        this.id_sp = id_sp;
        this.img_sp = img_sp;
        this.name_sp = name_sp;
        this.price_sp = price_sp;
        this.giam_gia = giam_gia;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.description_sp = description_sp;
        this.quantity_sp = quantity_sp;
    }


    public int getId_sp() {
        return id_sp;
    }

    public void setId_sp(int id_sp) {
        this.id_sp = id_sp;
    }

    public String getImg_sp() {
        return img_sp;
    }

    public void setImg_sp(String img_sp) {
        this.img_sp = img_sp;
    }

    public String getIMG_nb() {
        return IMG_nb;
    }

    public void setIMG_nb(String IMG_nb) {
        this.IMG_nb = IMG_nb;
    }

    public String getName_sp() {
        return name_sp;
    }

    public void setName_sp(String name_sp) {
        this.name_sp = name_sp;
    }

    public String getDescription_sp() {
        return description_sp;
    }

    public void setDescription_sp(String description_sp) {
        this.description_sp = description_sp;
    }

    public int getQuantity_sp() {
        return quantity_sp;
    }

    public void setQuantity_sp(int quantity_sp) {
        this.quantity_sp = quantity_sp;
    }

    public String getPrice_sp() {
        return price_sp;
    }

    public void setPrice_sp(String price_sp) {
        this.price_sp = price_sp;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getId_cate() {
        return id_cate;
    }

    public void setId_cate(String id_cate) {
        this.id_cate = id_cate;
    }

    public int getGiam_gia() {
        return giam_gia;
    }

    public void setGiam_gia(int giam_gia) {
        this.giam_gia = giam_gia;
    }

    public String getGia_goc() {
        return gia_goc;
    }

    public void setGia_goc(String gia_goc) {
        this.gia_goc = gia_goc;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_sp);
        dest.writeString(img_sp);
        dest.writeString(IMG_nb);
        dest.writeString(name_sp);
        dest.writeString(description_sp);
        dest.writeString(price_sp);
        dest.writeString(gia_goc);
        dest.writeInt(quantity_sp);
        dest.writeString(outstanding);
        dest.writeString(id_cate);
        dest.writeInt(giam_gia);
        dest.writeFloat(rating);
        dest.writeInt(reviewCount);
    }

    protected Product(Parcel in) {
        id_sp = in.readInt();
        img_sp = in.readString();
        IMG_nb = in.readString();
        name_sp = in.readString();
        description_sp = in.readString();
        price_sp = in.readString();
        gia_goc = in.readString();
        quantity_sp = in.readInt();
        outstanding = in.readString();
        id_cate = in.readString();
        giam_gia = in.readInt();
        rating = in.readFloat();
        reviewCount = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
