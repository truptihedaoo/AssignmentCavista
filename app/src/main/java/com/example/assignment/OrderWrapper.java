package com.example.assignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderWrapper {
    @SerializedName("data")
    @Expose
    private ArrayList<OrderList> orderList;

    public List<OrderList> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<OrderList> orderList) {
        this.orderList = orderList;
    }
}
