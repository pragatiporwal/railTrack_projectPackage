package com.example.dell.railtrack;

import android.content.Intent;
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

public class fourteen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DatabaseReference mdbref;
    LinearLayout ll;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourteen);
        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            finish();
            Intent i=new Intent(fourteen.this,Traveller_login.class);
            startActivity(i);
            //Toast.makeText(eleven.this, "hello", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        }
        FirebaseUser user=mauth.getCurrentUser();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        final String city=intent.getStringExtra("city").trim();
        final String train_no=intent.getStringExtra("train_no").trim();
        mdbref= FirebaseDatabase.getInstance().getReference("Restaurant_owner_login");
        ll=(LinearLayout)findViewById(R.id.ll);
        TextView tv=new TextView(fourteen.this);
        tv.setText("Select one restaurant");
        tv.setTextColor(Color.rgb(0,0,139));
        tv.setTextSize(35);
        ll.addView(tv);
        viewRestaurant(city,train_no);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void viewRestaurant(final String city, final String train_no)
    {

        mdbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.child("scity").getValue().equals(city))
                    {
                        //al.add((String) snapshot.child("sname_of_rest").getValue());
                        String str= (String) snapshot.child("sname_of_rest").getValue();
                        //tvres.setText(str);
                        final TextView tv=new TextView(fourteen.this);
                        tv.setText(str);
                        ll.addView(tv);
                        tv.setTextSize(25);
                        tv.setTextColor(Color.rgb(0,0,0));
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(fourteen.this,fifteen.class);
                                String str=tv.getText().toString().trim();
                                intent.putExtra("restaurant",str);
                                intent.putExtra("city",city);
                                intent.putExtra("train_no",train_no);
                                startActivity(intent);
                            }
                        });

                        //Toast.makeText(fourteen.this, ""+str, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        getMenuInflater().inflate(R.menu.fourteen, menu);
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
            Intent i=new Intent(fourteen.this,Traveller_login.class);
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
            Intent i=new Intent(fourteen.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(fourteen.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
