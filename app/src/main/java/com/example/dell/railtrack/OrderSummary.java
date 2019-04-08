package com.example.dell.railtrack;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OrderSummary extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnok;
    TextView train,name,phone,total,res,station,tvcash;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            finish();
            Intent i=new Intent(OrderSummary.this,Traveller_login.class);
            startActivity(i);
            //Toast.makeText(eleven.this, "hello", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        }
        FirebaseUser user=mauth.getCurrentUser();
        Intent in=getIntent();
        final String restaurant=in.getStringExtra("restaurant");
        final String city=in.getStringExtra("city");
        final String train_no=in.getStringExtra("train_no");
        final String total_price=in.getStringExtra("total_price");
        final String station1=in.getStringExtra("station");
        final String name1=in.getStringExtra("name");
        final String phone_no=in.getStringExtra("phone_no");
        final String rest_phone=in.getStringExtra("rest_phone");

        train=(TextView)findViewById(R.id.trainno);
        name=(TextView)findViewById(R.id.cusname);
        phone=(TextView)findViewById(R.id.phone);
        total=(TextView)findViewById(R.id.total);
        res=(TextView)findViewById(R.id.res);
        station=(TextView)findViewById(R.id.stat);
        tvcash=(TextView)findViewById(R.id.tvcash);
        btnok=(Button)findViewById(R.id.ok);
        train.setText(train_no);
        name.setText(name1);
        phone.setText(phone_no);
        total.setText(total_price);
        res.setText(restaurant);
        station.setText(station1);
        tvcash.setText("Please pay the amount Rupees "+total_price+" at the time of delivery");

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(OrderSummary.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
               finish();

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
        getMenuInflater().inflate(R.menu.order_summary, menu);
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
           // finish
            finish();
            Intent i=new Intent(OrderSummary.this,Traveller_login.class);
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
            Intent i=new Intent(OrderSummary.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(OrderSummary.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
