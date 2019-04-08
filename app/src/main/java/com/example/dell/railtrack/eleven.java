package com.example.dell.railtrack;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class eleven extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    ListView lv;
    ArrayList<String> al;
   //String city;
    FirebaseAuth mauth;
    TextView tv,tvinside;
    double distance[] = new double[8];
    private FusedLocationProviderClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleven);
        tv=(TextView)findViewById(R.id.tvemail);
        tvinside=(TextView)findViewById(R.id.tvinside);
        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            finish();
            Intent i=new Intent(eleven.this,Traveller_login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        FirebaseUser user=mauth.getCurrentUser();

        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
        client = LocationServices.getFusedLocationProviderClient(this);


        Intent intent = getIntent();
        final String number = intent.getStringExtra("number");
        String name = intent.getStringExtra("name");
      //  Toast.makeText(this, "" + number + "  " + name, Toast.LENGTH_LONG).show();
        lv = (ListView) findViewById(R.id.lv_id);
        al = new ArrayList<String>();

        SQLiteDatabase db1 = openOrCreateDatabase("RailTrack.db", MODE_PRIVATE, null);
        String q1 = "create table if not exists railway_station(train_no varchar,train_name varchar,station varchar,arrival_time varchar,departure_time varchar,city varchar,dist varchar,primary key(train_no,station))";
        db1.execSQL(q1);

        q1 = "Select * from railway_station where train_no='" + number + "'";
        Cursor c = db1.rawQuery(q1, null);
        if (c.moveToFirst()) {
            tvinside.setText("If you are inside the train, click on this button");
            do {

                al.add(c.getString(0) + "  " + c.getString(1) + "  " + c.getString(2) + "  " + c.getString(3) + "  " + c.getString(4));
            } while (c.moveToNext());
        } else {
            tv.setText("Sorry there is no train with such name or no .Please try again with different train no. or name");
            Toast.makeText(this, "Sorry there are no train with such name", Toast.LENGTH_SHORT).show();
        }
        db1.close();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(arrayAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your current location will be displayed.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                if (ActivityCompat.checkSelfPermission(eleven.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                client.getLastLocation().addOnSuccessListener(eleven.this, new OnSuccessListener<Location>() {
                    String city1;
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null)
                        {
                           double slong=location.getLongitude();
                           double slat=location.getLatitude();

                           double mydis = haversine(28.0146,73.3160, 27.2000, 73.7235);
                            Toast.makeText(eleven.this, "my dist"+mydis, Toast.LENGTH_SHORT).show();
                            if (number.equals("22464"))
                            {
                                double dist[] = new double[8];
                               /* dist[0] = haversine(28.0146, 73.3160, 27.5546, 73.4762);
                                dist[1] = haversine(28.0146, 73.3160, 27.2000, 73.7235);
                                dist[2] = haversine(28.0146, 73.3160, 26.7276, 73.9175);
                                dist[3] = haversine(28.0146, 73.3160, 26.8918, 74.3177);
                                dist[4] = haversine(28.0146, 73.3160, 27.0372, 74.7247);
                                dist[5] = haversine(28.0146, 73.3160, 26.9206, 75.7911);
                                dist[6] = haversine(28.0146, 73.3160, 27.5607, 76.6215);
                                dist[7] = haversine(28.0146,73.3160,28.6628,77.1853);*/
                               dist[0] =0;
                                dist[1] =53.53757014581607;
                                dist[2]=99.10791852731703;
                                dist[3] =154.98720358603046;
                                dist[4] =159.28165115165191;
                                dist[5] =176.4198763966336;
                                dist[6] =272.8835065875887;
                                dist[7] =329.1443586004272;
                                int flag=0;
                                //Toast.makeText(eleven.this, ""+dist[0]+" "+dist[1]+" "+dist[2]+" "+dist[3]+" "+dist[4]+" "+dist[5]+" "+dist[6]+" "+dist[7], Toast.LENGTH_SHORT).show();
                               for (int i = 0; i < 8; i++)
                               {
                                   if(i<7)
                                   {
                                       if (dist[i]<=mydis && mydis<dist[i+1])
                                       {
                                           flag=1;
                                           SQLiteDatabase db1 = openOrCreateDatabase("RailTrack.db", MODE_PRIVATE, null);
                                           String q1 = "create table if not exists railway_station(train_no varchar,train_name varchar,station varchar,arrival_time varchar,departure_time varchar,city varchar,dist varchar,primary key(train_no,station))";
                                           db1.execSQL(q1);
                                           String d=dist[i+1]+"";
                                          // Toast.makeText(eleven.this, ""+d, Toast.LENGTH_SHORT).show();
                                           q1 = "Select station from railway_station where dist='"+d+"'";
                                           Cursor c = db1.rawQuery(q1, null);
                                           if (c.moveToFirst())
                                           {

                                               do {
                                           city1 = c.getString(0);
                                           Toast.makeText(eleven.this, "Next City"+city1, Toast.LENGTH_SHORT).show();
                                               } while (c.moveToNext());
                                           }
                                           db1.close();
                                       }

                                   }
                                   else if(mydis<dist[7] && flag==0)
                                   {
                                       flag=1;
                                       SQLiteDatabase db1 = openOrCreateDatabase("RailTrack.db", MODE_PRIVATE, null);
                                       String q1 = "create table if not exists railway_station(train_no varchar,train_name varchar,station varchar,arrival_time varchar,departure_time varchar,city varchar,dist varchar,primary key(train_no,station))";
                                       db1.execSQL(q1);
                                       String d=dist[i]+"";
                                       // Toast.makeText(eleven.this, ""+d, Toast.LENGTH_SHORT).show();
                                       q1 = "Select station from railway_station where dist='"+d+"'";
                                       Cursor c = db1.rawQuery(q1, null);
                                       if (c.moveToFirst())
                                       {

                                           do {
                                               city1 = c.getString(0);
                                               Toast.makeText(eleven.this, "Next City"+city1, Toast.LENGTH_SHORT).show();
                                           } while (c.moveToNext());
                                       }
                                       db1.close();
                                     //  Toast.makeText(eleven.this, "You have reached your destination", Toast.LENGTH_SHORT).show();
                                   }

                                }
                                if(flag==0)
                                {
                                    city1="You have reached your destination";
                                    Toast.makeText(eleven.this, "You have reached your destination", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Intent in=new Intent(eleven.this,Googletrack.class);
                            in.putExtra("longitude",location.getLongitude());
                            in.putExtra("latitude",location.getLatitude());
                            in.putExtra("train_no",number);
                            Toast.makeText(eleven.this, ""+city1, Toast.LENGTH_SHORT).show();
                            in.putExtra("city",city1);
                            startActivity(in);
                          //  Toast.makeText(eleven.this, "Latitude "+location.getLatitude()+"Longitude "+location.getLongitude(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double Rad = 6372.8; //Earth's Radius In kilometers
        // TODO Auto-generated method stub
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return Rad * c;

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
        getMenuInflater().inflate(R.menu.eleven, menu);
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
            Intent i=new Intent(eleven.this,Traveller_login.class);
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
            Intent i=new Intent(eleven.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(eleven.this,Contact_us.class);
            startActivity(i);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
