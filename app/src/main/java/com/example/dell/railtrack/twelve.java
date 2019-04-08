package com.example.dell.railtrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

public class twelve extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText etrain;
   LinearLayout llcity;
   TextView tvemail,tvcity;
    Button btnok;
    DatabaseReference mdbref;
    private ProgressDialog progessDialog;
    FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twelve);
        progessDialog=new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            finish();
            Intent i=new Intent(twelve.this,Traveller_login.class);
            startActivity(i);
            //Toast.makeText(eleven.this, "hello", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        }
        FirebaseUser user=mauth.getCurrentUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdbref= FirebaseDatabase.getInstance().getReference("Restaurant_owner_login");
        llcity=(LinearLayout)findViewById(R.id.llcity);
        etrain=(EditText)findViewById(R.id.ettrain);
        btnok=(Button)findViewById(R.id.btnok);
        tvemail=(TextView)findViewById(R.id.tvemail);
        tvcity=(TextView)findViewById(R.id.tvcity);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String train=etrain.getText().toString().trim();
                if(train.isEmpty())
                {
                    tvemail.setText("Please enter Train number");
                }
                else
                {
                    viewCity(train);
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
        getMenuInflater().inflate(R.menu.twelve, menu);
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
            Intent i=new Intent(twelve.this,Traveller_login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void viewCity(final String train)
    {
        tvemail.setText("");
        progessDialog.setMessage("Searching.....");
        progessDialog.show();
        SQLiteDatabase db1 = openOrCreateDatabase("RailTrack.db", MODE_PRIVATE, null);
        String q1 = "create table if not exists railway_station(train_no varchar,train_name varchar,station varchar,arrival_time varchar,departure_time varchar,city varchar,primary key(train_no,station))";
        db1.execSQL(q1);
        q1 = "Select city from railway_station where train_no='" + train + "'";
        Cursor c = db1.rawQuery(q1, null);
        if (c.moveToFirst())
        {
            tvcity.setText("Select one city.");
            tvcity.setTextColor(Color.rgb(0,0,139));
            do {
                final String city=c.getString(0);
                mdbref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            if(snapshot.child("scity").getValue().equals(city))
                            {
                                TextView tv=new TextView(twelve.this);
                                tv.setText(city);
                                tv.setTextSize(25);
                                tv.setTextColor(Color.rgb(0,0,0));
                                llcity.addView(tv);

                                progessDialog.dismiss();
                                tv.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent=new Intent(twelve.this,fourteen.class);
                                        intent.putExtra("train_no",train);
                                        intent.putExtra("city",city);
                                        startActivity(intent);
                                        //Toast.makeText(twelve.this, ""+city, Toast.LENGTH_SHORT).show();

                                    }
                                });
                               break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } while (c.moveToNext());
        }
        else {
            tvcity.setText("Sorry there is no train with such number");
            Toast.makeText(this, "Sorry there are no train with such number", Toast.LENGTH_SHORT).show();
        }
        db1.close();





    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about_us) {
            Intent i=new Intent(twelve.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(twelve.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
