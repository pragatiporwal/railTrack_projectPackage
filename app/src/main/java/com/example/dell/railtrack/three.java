package com.example.dell.railtrack;

import android.content.ClipData;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class three extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btntrack,btnfood;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            finish();
            Intent i=new Intent(three.this,Traveller_login.class);
            startActivity(i);
           // Toast.makeText(three.this, "hello", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        }
        FirebaseUser user=mauth.getCurrentUser();

        btntrack = (Button)findViewById(R.id.button9);
        btnfood = (Button)findViewById(R.id.button11);
        try {

            fillrailway_station("22464", "Rjsthn S Krnti", "Bikaner Junction", "Start", "17:05","Bikaner","0.0");
            fillrailway_station("22464", "Rjsthn S Krnti", "Garhnokha Railway Station", "17:51", "17:53","Garhnokha","53.53757014581607");
            fillrailway_station("22464", "Rjsthn S Krnti", "Nagaur Railway Station", "18:46", "18:51","Nagaur","99.10791852731703");
            fillrailway_station("22464", "Rjsthn S Krnti", "Merta Road Junction", "20:15", "21:05","Merta","154.98720358603046");
            fillrailway_station("22464", "Rjsthn S Krnti", "Degana Junction", "21:38", "21:40","Degana","159.28165115165191");
            fillrailway_station("22464", "Rjsthn S Krnti", "Makrana Junction", "22:09", "22:12","Makrana","176.4198763966336");
            fillrailway_station("22464", "Rjsthn S Krnti", "Jaipur Railway Station", "00:15", "00:25","Jaipur","272.8835065875887");
            fillrailway_station("22464", "Rjsthn S Krnti", "Alwar Railway Station", "02:30", "02:33","Alwar","329.1443586004272");
            fillrailway_station("22464", "Rjsthn S Krnti", "Delhi Cantt. Railway Station", "05:06", "05:08","Delhi Cantt.","385.5720106644891");
            fillrailway_station("22464", "Rjsthn S Krnti", "Delhi Sarai Rohilla Railway Station", "05:40", "End","Delhi Sarai Rohilla","");
            fillrailway_station("12464", "Rjsthn S Krnti", "Jodhpur Railway Station", "Start", "19:15","Jodhpur","");
            fillrailway_station("12464", "Rjsthn S Krnti", "Merta Road Junction", "20:40", "21:05","Merta","");


        } catch (Exception e) {
           // Toast.makeText(this, "Data already exists", Toast.LENGTH_SHORT).show();
        }

        btntrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toseven = new Intent(three.this,seven.class);
                startActivity(toseven);
            }
        });


        btnfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tonine = new Intent(three.this,nine.class);
                startActivity(tonine);
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
    void fillrailway_station(String t1,String t2,String t3,String t4,String t5,String t6,String t7) {
        SQLiteDatabase db=openOrCreateDatabase("RailTrack.db",MODE_PRIVATE,null);
        String q="create table if not exists railway_station(train_no varchar,train_name varchar,station varchar,arrival_time varchar,departure_time varchar,city varchar,dist varchar,primary key(train_no,station))";
        db.execSQL(q);
        q="insert into railway_station values('"+t1+"','"+t2+"','"+t3+"','"+t4+"','"+t5+"','"+t6+"','"+t7+"')";
        db.execSQL(q);
        db.close();
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
        getMenuInflater().inflate(R.menu.three, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mauth.signOut();
            finish();
            Intent i=new Intent(three.this,Traveller_login.class);
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
            Intent i=new Intent(three.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(three.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
