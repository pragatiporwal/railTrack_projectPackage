package com.example.dell.railtrack;

public class fifteen_database {
    String food_name;
    String quantity;
    String price;
    String restaurant;
    public fifteen_database(String food_name, String price, String quantity,String restaurant) {
        this.food_name = food_name;
        this.price = price;
        this.quantity = quantity;
        this.restaurant=restaurant;
    }

    public String getFood_name() {
        return food_name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getRestaurant()
    {
        return restaurant;
    }
}
