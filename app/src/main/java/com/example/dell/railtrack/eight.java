package com.example.dell.railtrack;

import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;

public class eight extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtenter,txtto,txttoans,txtfrom,txtfromans,txtno,txtnoans,txtname,txtnameans,txtseat,txtseatans;
    EditText edpnr;
    Button btnok,btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eight);

        btnok = (Button)findViewById(R.id.button14);
        btnback = (Button)findViewById(R.id.button15);
        edpnr = (EditText)findViewById(R.id.editText15);
        txtenter = (TextView)findViewById(R.id.textView24);
        txtto = (TextView)findViewById(R.id.textView25);
        txttoans = (TextView)findViewById(R.id.textView30);
        txtfrom = (TextView)findViewById(R.id.textView26);
        txtfromans = (TextView)findViewById(R.id.textView31);
        txtno = (TextView)findViewById(R.id.textView27);
        txtnoans = (TextView)findViewById(R.id.textView32);
        txtname = (TextView)findViewById(R.id.textView28);
        txtnameans = (TextView)findViewById(R.id.textView33);
        txtseat = (TextView)findViewById(R.id.textView29);
        txtseatans = (TextView)findViewById(R.id.textView34);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tothree = new Intent(eight.this,three.class);
                startActivity(tothree);
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
        getMenuInflater().inflate(R.menu.eight, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
            Intent i=new Intent(eight.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(eight.this,Contact_us.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
