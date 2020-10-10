package com.example.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;


public class OrderList extends Observable implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("images")
    @Expose
    private List<ImageList> foodList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageList> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<ImageList> foodList) {
        this.foodList = foodList;
    }
}
