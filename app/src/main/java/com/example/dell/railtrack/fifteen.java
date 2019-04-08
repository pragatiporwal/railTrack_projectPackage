package com.example.dell.railtrack;

import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class fifteen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout l1,l2,l3;
    DatabaseReference mdbref,cdbref;
    String book[][]=new String[100][3];
    Button btnconfirm;
    TextView tvselect;
    private ProgressDialog progessDialog;
    double total_price=0.0;
    final int[] k = {0};
    DatabaseReference booking_table,key_ref;
    String city,train_no,key,restaurant;
    FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progessDialog=new ProgressDialog(this);
        setContentView(R.layout.activity_fifteen);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mauth=FirebaseAuth.getInstance();
        if(mauth.getCurrentUser()==null)
        {
            finish();
            Intent i=new Intent(fifteen.this,Traveller_login.class);
            startActivity(i);
            //Toast.makeText(eleven.this, "hello", Toast.LENGTH_SHORT).show();
            // Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        }
        FirebaseUser user=mauth.getCurrentUser();
        booking_table= FirebaseDatabase.getInstance().getReference("Booking_table").push();

        //key_ref=FirebaseDatabase.getInstance().getReference("Booking_table");
        btnconfirm=(Button)findViewById(R.id.btncon);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
         restaurant=intent.getStringExtra("restaurant").trim();
         city=intent.getStringExtra("city").trim();
         train_no=intent.getStringExtra("train_no").trim();
        //Toast.makeText(this, ""+restaurant, Toast.LENGTH_SHORT).show();
        l1=(LinearLayout)findViewById(R.id.l1);
        l2=(LinearLayout)findViewById(R.id.l2);
        l3=(LinearLayout)findViewById(R.id.l3);
        tvselect=(TextView)findViewById(R.id.tvselect);
        mdbref= FirebaseDatabase.getInstance().getReference("Restaurant_owner_login");
        cdbref=FirebaseDatabase.getInstance().getReference("Food_item_table");
        progessDialog.setMessage("Searching.....");
        progessDialog.show();
        mdbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (final DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.child("sname_of_rest").getValue().equals(restaurant))
                    {
                        final String susername1= (String) snapshot.child("susername").getValue();
                       // Toast.makeText(fifteen.this, ""+susername1, Toast.LENGTH_SHORT).show();
                        cdbref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                for (final DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                    if (snapshot1.child("susername").getValue().equals(susername1)) {
                                        final String str = ((String) snapshot1.child("name").getValue());
                                        final String mtr=((String) snapshot1.child("price").getValue());

                                        final CheckBox cb = new CheckBox(fifteen.this);
                                        cb.setText(str);
                                        cb.setTextSize(30);
                                        cb.setTextColor(Color.rgb(0,0,0));
                                        l1.addView(cb);
                                        progessDialog.dismiss();
                                        TextView tb=new TextView(fifteen.this);
                                        tb.setText(mtr);
                                        tb.setTextColor(Color.rgb(0,0,0));
                                        tb.setTextSize(30);
                                        l3.addView(tb);
                                        final EditText et=new EditText(fifteen.this);
                                        et.setHint("Quantity");
                                        et.setTextColor(Color.rgb(0,0,0));
                                        l2.addView(et);
                                        et.setTextSize(15);

                                        cb.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if(cb.isChecked())
                                                    {

                                                            book[k[0]][0]=str;
                                                            book[k[0]][1]=mtr;
                                                        final String ptr=et.getText().toString().trim();
                                                        if(ptr.isEmpty())
                                                        {
                                                            Toast.makeText(fifteen.this, "Please enter quantity first.", Toast.LENGTH_SHORT).show();
                                                            cb.setChecked(false);
                                                        }
                                                        else {
                                                            book[k[0]][2] = ptr;
                                                            k[0]++;
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                        }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(k[0]==0)
                {
                    tvselect.setText("Please select one item");
                    tvselect.setTextColor(Color.rgb(255,0,0));
                    }
                else {
                    for (int i = 0; i < k[0]; i++) {
                        insert_in_booking(book[i][0], book[i][1], book[i][2], restaurant);

                    }

                    for (int i = 0; i < k[0]; i++) {
                        double price = Double.parseDouble(book[i][1]);
                        double quantity = Double.parseDouble(book[i][2]);
                        total_price = total_price + (price * quantity);

                    }
                    Intent in = new Intent(fifteen.this, customer_order_info.class);
                    String ytr = total_price + "";
                    in.putExtra("restaurant", restaurant);
                    in.putExtra("city", city);
                    in.putExtra("train_no", train_no);
                    in.putExtra("total_price", ytr);
                    in.putExtra("key", key);
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

    private void insert_in_booking(String food_name,String price,String quantity,String rest)
    {
        key=booking_table.getKey();

        //Toast.makeText(this, "station "+station, Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, ""+key, Toast.LENGTH_SHORT).show();
        fifteen_database item_db=new fifteen_database(food_name,price,quantity,rest);
        //booking_table.child(food_no).setValue(food_item_db);
        booking_table.child(food_name).setValue(item_db);

        Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.fifteen, menu);
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
            Intent i=new Intent(fifteen.this,Traveller_login.class);
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
            Intent i=new Intent(fifteen.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(fifteen.this,Contact_us.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
