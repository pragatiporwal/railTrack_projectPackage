package com.example.dell.railtrack;

public class Twentyfour_database {



    String food_no;
    String name;
    String price;
    String susername;
    public Twentyfour_database(String food_no, String name, String price,String susername) {
        this.food_no = food_no;
        this.name = name;
        this.price = price;
        this.susername=susername;
    }

    public String getFood_no() {
        return food_no;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

   public String getSusername()
   {
       return susername;
   }
}
