package com.example.dell.railtrack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class customer_order_info extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Button btncon;
    EditText etname1,etphone1;
    DatabaseReference customer_order_table,bookingtable;
    String total_price;
    TextView tvname;
    String phn;
    String key;
    int k=0;

   String str[]=new String[100];

    DatabaseReference mdbref;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            finish();
            Intent i=new Intent(customer_order_info.this,Traveller_login.class);
            startActivity(i);
            //Toast.makeText(eleven.this, "hello", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        }
        FirebaseUser user=mauth.getCurrentUser();
        mdbref= FirebaseDatabase.getInstance().getReference("Restaurant_owner_login");

        etname1=(EditText)findViewById(R.id.etn);
        etphone1=(EditText)findViewById(R.id.etp);
        btncon=(Button)findViewById(R.id.btnok);
        tvname=(TextView)findViewById(R.id.tvname);
        Intent in=getIntent();
        key=in.getStringExtra("key");
        final String restaurant=in.getStringExtra("restaurant");
        final String city=in.getStringExtra("city");
        final String train_no=in.getStringExtra("train_no");
        total_price=in.getStringExtra("total_price");
        bookingtable=FirebaseDatabase.getInstance().getReference("Booking_table").child(key);

        getDetails1();

        //getDetails();

        final String station= find_station(city);
        customer_order_table= FirebaseDatabase.getInstance().getReference("Customer_order_table").child(key);
        btncon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etname1.getText().toString().trim();
                final String phone = etphone1.getText().toString().trim();
                if (name.isEmpty() || phone.isEmpty()) {
                    tvname.setText("Please fill the details");
                } else {
                    customer_database item_db = new customer_database(key, train_no, name, phone, total_price, restaurant, station);
                    customer_order_table.setValue(item_db);
                    Toast.makeText(customer_order_info.this, "Booking confirmed", Toast.LENGTH_SHORT).show();
                    String p = sendmessage(restaurant);
                    Intent in = new Intent(customer_order_info.this, OrderSummary.class);
                    in.putExtra("restaurant", restaurant);
                    in.putExtra("city", city);
                    in.putExtra("train_no", train_no);
                    in.putExtra("total_price", total_price);
                    in.putExtra("station", station);
                    in.putExtra("name", name);
                    in.putExtra("phone_no", phone);
                    in.putExtra("rest_phone", p);
                    startActivity(in);
                }
            }

        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getDetails1() {
        //final int[] i = {0};
        bookingtable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                    //DatabaseReference str=bookingtable.child(key);
                    //String str2=(String) snapshot.getValue();
                    str[k]="Food item"+(k+1)+" = "+String.valueOf(snapshot.child("food_name").getValue())+" Quantity= "+String.valueOf(snapshot.child("quantity").getValue())+"  ";
                   // str[i[0]][0]= String.valueOf(snapshot.child("food_name").getValue());
                    //str[i[0]][1]= String.valueOf(snapshot.child("quantity").getValue());
                   // Toast.makeText(customer_order_info.this, " "+str[i[0]][0]+" "+str[i[0]][1], Toast.LENGTH_SHORT).show();
                   // Toast.makeText(customer_order_info.this, "str1 "+snapshot.child("quantity"), Toast.LENGTH_SHORT).show();
                    k++;
                    // Toast.makeText(customer_order_info.this, "str2 "+str2, Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDetails() {
        bookingtable.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                      DatabaseReference str=bookingtable.child(key);
                      String str2=(String) snapshot.getValue();
                      Toast.makeText(customer_order_info.this, "str1 "+str, Toast.LENGTH_SHORT).show();
                        Toast.makeText(customer_order_info.this, "str2 "+str2, Toast.LENGTH_SHORT).show();


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private String sendmessage(final String restaurant) {
        mdbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.child("sname_of_rest").getValue().equals(restaurant))
                    {
                        phn=(String)snapshot.child("sphone").getValue();
                       // Toast.makeText(customer_order_info.this, ""+phn, Toast.LENGTH_SHORT).show();
                      ActivityCompat.requestPermissions(customer_order_info.this,new String[]{android.Manifest.permission.SEND_SMS},1);
                      for(int i=0;i<k;i++) {
                          String msg1 = "RailTrack order placed RESTAURANT:" + restaurant + "  " + str[i];
                          SmsManager manager = SmsManager.getDefault();
                          manager.sendTextMessage(phn, null, msg1, null, null);
                          //Toast.makeText(customer_order_info.this, ""+phn, Toast.LENGTH_SHORT).show();
                      }

                    }
                }

                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return phn;
    }

    private String find_station(String city) {

        String station="";
        SQLiteDatabase db1 = openOrCreateDatabase("RailTrack.db", MODE_PRIVATE, null);
        String q1 = "create table if not exists railway_station(train_no varchar,train_name varchar,station varchar,arrival_time varchar,departure_time varchar,city varchar,primary key(train_no,station))";
        db1.execSQL(q1);
        q1 = "Select station from railway_station where city='" + city + "'";
        Cursor c = db1.rawQuery(q1, null);
        if (c.moveToFirst()) {
            do {

                station=c.getString(0);
            } while (c.moveToNext());
        } else
            Toast.makeText(this, " no station with such name", Toast.LENGTH_SHORT).show();
        db1.close();

        return station;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer_order_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            mauth.signOut();
            finish();
            Intent i=new Intent(customer_order_info.this,Traveller_login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_about_us) {
            Intent i=new Intent(customer_order_info.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(customer_order_info.this,Contact_us.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
