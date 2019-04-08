package com.example.dell.railtrack;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class twentyfour extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtno,txtname,txtprice,tvfill;
    EditText etno,etname,etprice;
    Button btnok;
    DatabaseReference food_item_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twentyfour);
        Intent intent=getIntent();
       final String username=intent.getStringExtra("username").trim();
        tvfill=(TextView)findViewById(R.id.tvfill);
        txtno = (TextView)findViewById(R.id.textView38);
        txtname = (TextView)findViewById(R.id.textView41);
        txtprice = (TextView)findViewById(R.id.textView42);
        etno = (EditText)findViewById(R.id.editText16);
        etname = (EditText)findViewById(R.id.editText17);
        etprice = (EditText)findViewById(R.id.editText18);
        btnok = (Button)findViewById(R.id.button24);

        food_item_table= FirebaseDatabase.getInstance().getReference("Food_item_table");
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert_in_food_item_table(username);
                //Intent totwentythree = new Intent(twentyfour.this,twentythree.class);
                //startActivity(totwentythree);
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

    private void insert_in_food_item_table(String username)
    {
        String food_no = etno.getText().toString().trim();
        String name = etname.getText().toString().trim();
        String price = etprice.getText().toString().trim();
        if(TextUtils.isEmpty(food_no) || TextUtils.isEmpty(name) || TextUtils.isEmpty(price))
        {
            tvfill.setText("Enter all fields");
            //Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            tvfill.setText("");
            Twentyfour_database food_item_db=new Twentyfour_database(food_no,name,price,username);
            food_item_table.child(food_no).setValue(food_item_db);
            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.twentyfour, menu);
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
            Intent in=new Intent(twentyfour.this,Thirteen.class);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(in);
            finish();
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
            Intent i=new Intent(twentyfour.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(twentyfour.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
