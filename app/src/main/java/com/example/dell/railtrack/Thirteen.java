package com.example.dell.railtrack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Thirteen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnlogin,btnnew,btnforget;
    EditText etusername,etpwd;
    TextView txtname,txtpwd;
    ListView lvuser;
    private ProgressDialog progessDialog;
    DatabaseReference mdbref;
    List<twentyone_database> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thirteen);
        progessDialog=new ProgressDialog(this);
        btnlogin = (Button)findViewById(R.id.btnlogin);
        btnnew = (Button)findViewById(R.id.btnnewuser);
        btnforget = (Button)findViewById(R.id.btnforgetpassword);
        etusername= (EditText)findViewById(R.id.etusername);
        etpwd = (EditText)findViewById(R.id.etpassword);
        txtname=(TextView)findViewById(R.id.tvemail);
        txtpwd=(TextView)findViewById(R.id.tvpass);
        lvuser=(ListView)findViewById(R.id.lvuser);
        userlist=new ArrayList<twentyone_database>();
        mdbref= FirebaseDatabase.getInstance().getReference("Restaurant_owner_login");

        btnlogin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessDialog.setMessage("Login User.....");
                progessDialog.show();
                String name = etusername.getText().toString();
                String pwd = etpwd.getText().toString();
                if (name.isEmpty()) {
                    txtname.setText("Please enter email");
                }
                else if (pwd.isEmpty()) {
                    txtpwd.setText("Please enter password");

                } else
                    {
                        txtpwd.setText("");
                        txtname.setText("");
                    viewBook(name, pwd);
                    // Intent totwentythree = new Intent(Thirteen.this,twentythree.class);
                    //startActivity(totwentythree);
                }
            }
        }));


        btnnew.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                Intent totwentyone = new Intent(Thirteen.this, twentyone.class);
                startActivity(totwentyone);
            }

        });

        btnforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toeighteen = new Intent(Thirteen.this,eighteen.class);
                startActivity(toeighteen);
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

  /* @Override
    protected void onStart() {
        super.onStart();
        mdbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();
                Toast.makeText(Thirteen.this, "Hello", Toast.LENGTH_SHORT).show();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   // for (DataSnapshot snapshotone : snapshot.getChildren()) {

                        Toast.makeText(Thirteen.this, "Hello1", Toast.LENGTH_SHORT).show();
                        twentyone_database db = snapshot.getValue(twentyone_database.class);
                        Toast.makeText(Thirteen.this, "Hello2", Toast.LENGTH_SHORT).show();
                        userlist.add(db);
                    }
                    Userlist adapter = new Userlist(Thirteen.this, userlist);
                    lvuser.setAdapter(adapter);
                //}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    /*private void viewBook(String name,String pwd) {
        Toast.makeText(Thirteen.this, "before child", Toast.LENGTH_SHORT).show();
        mdbref.child("saddress").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    //Book book=dataSnapshot.getValue(.class);
                    Toast.makeText(Thirteen.this, "Hellooo", Toast.LENGTH_SHORT).show();
                    String restaurant =dataSnapshot.getValue(String.class);
                    Toast.makeText(Thirteen.this, "work done---"+restaurant, Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(Thirteen.this, " data snapshot not exxits", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
    private void viewBook(final String name, final String pwd)
    {
        mdbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int flag=0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {

                  //  Toast.makeText(Thirteen.this, ""+snapshot.child("susername").getValue(), Toast.LENGTH_SHORT).show();
                   if(snapshot.child("susername").getValue().equals(name)) {
                       flag=1;
                       if (snapshot.child("spassword").getValue().equals(pwd)) {
                          // Toast.makeText(Thirteen.this, ""+snapshot.child("spassword").getValue(), Toast.LENGTH_SHORT).show();
                           String str=(String)snapshot.child("susername").getValue();
                         //  Toast.makeText(Thirteen.this, ""+str, Toast.LENGTH_SHORT).show();
                           SharedPreferences sp=getSharedPreferences("railtrack", Context.MODE_PRIVATE);
                           SharedPreferences.Editor ed=sp.edit();
                           ed.putString("suername",etusername.getText().toString());
                           ed.putString("password",etpwd.getText().toString());
                           ed.apply();
                        Intent i = new Intent(Thirteen.this, twentythree.class);
                           i.putExtra("username",str);
                           startActivity(i);
                           progessDialog.dismiss();
                       }
                       else
                           {
                               progessDialog.dismiss();
                               txtpwd.setText("Password incorrect");

                         //  Toast.makeText(Thirteen.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                       }
                   }

                }
                if(flag==0)
                {
                    progessDialog.dismiss();
                    txtname.setText("Incorrect Username");
                    //Toast.makeText(Thirteen.this, "Incorrect Username", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.thirteen, menu);
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
            Intent i=new Intent(Thirteen.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(Thirteen.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
