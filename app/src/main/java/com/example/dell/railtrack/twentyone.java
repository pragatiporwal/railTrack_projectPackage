package com.example.dell.railtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ButtonBarLayout;
import android.text.TextUtils;
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

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class twentyone extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    EditText name_of_restaurant,username,email,password,phoneno,address,city;
    Button register;
    DatabaseReference restaurant_owner_database;
    FirebaseUser user;
    FirebaseAuth mauth;
    TextView tvselect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twentyone);

        restaurant_owner_database= FirebaseDatabase.getInstance().getReference("Restaurant_owner_login");

        name_of_restaurant=(EditText)findViewById(R.id.etnameofrestaurant);
        username=(EditText)findViewById(R.id.etusername);
        email=(EditText)findViewById(R.id.etemail);
        username=(EditText)findViewById(R.id.etusername);
        password=(EditText)findViewById(R.id.etpassword);
        phoneno=(EditText)findViewById(R.id.etphoneno);
        address=(EditText)findViewById(R.id.etaddress);
        tvselect=(TextView)findViewById(R.id.tvselect);
        city=(EditText)findViewById(R.id.etcity);
        register=(Button)findViewById(R.id.btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insert_in_database();
                //Intent tobeta = new Intent(twentyone.this,beta.class);
                //startActivity(tobeta);
              //  user=mauth.getCurrentUser();
                //Toast.makeText(twentyone.this, ""+user, Toast.LENGTH_SHORT).show();

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void insert_in_database()
    {
        String sname_of_restaurant=name_of_restaurant.getText().toString().trim();
        String susername=username.getText().toString().trim();
        String semail=email.getText().toString().trim();
        String spassword=password.getText().toString().trim();
        String sphone=phoneno.getText().toString().trim();
        String saddress=address.getText().toString().trim();
        String scity=city.getText().toString().trim();
        if(TextUtils.isEmpty(sname_of_restaurant) || TextUtils.isEmpty(susername) || TextUtils.isEmpty(semail) || TextUtils.isEmpty(spassword) || TextUtils.isEmpty(sphone) || TextUtils.isEmpty(saddress) ||TextUtils.isEmpty(scity))
        {
           tvselect.setText("Please enter all fields.");
           // Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            tvselect.setText("");
            twentyone_database twentyone_db=new twentyone_database(sname_of_restaurant,susername,semail,spassword,sphone,saddress,scity);
            restaurant_owner_database.child(susername).setValue(twentyone_db);
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(twentyone.this,twentythree.class);
            startActivity(i);
        }
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
        getMenuInflater().inflate(R.menu.twentyone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about_us) {
            Intent i=new Intent(twentyone.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(twentyone.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
