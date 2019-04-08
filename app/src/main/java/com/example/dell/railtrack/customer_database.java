package com.example.dell.railtrack;

import android.widget.EditText;

public class customer_database {

    String key;
    String train_no;
    String etname;
    String etphone;
    String total_price;
    String restaurant;
    String station;

        public customer_database(String key,String train_no, String etname, String etphone, String total_price, String restaurant, String station)
        {
            this.key=key;
            this.train_no = train_no;
            this.etname = etname;
            this.etphone=etphone;
            this.total_price=total_price;
            this.restaurant=restaurant;
            this.station=station;
        }

    public String getKey() {
        return key;
    }

    public String getEtname() {
        return etname;
    }

    public String getStation() {
        return station;
    }

    public String getEtphone() {
        return etphone;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getTrain_no() {
        return train_no;
    }

    public String getRestaurant()
        {
            return restaurant;
        }
    }


