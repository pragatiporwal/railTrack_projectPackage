package com.example.dell.railtrack;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Traveller_login extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private EditText t1_email,t1_password;
    private Button t1_login,t1_newUser,t1_forgetPassword;
    private FirebaseAuth mauth;
    TextView txtemail,txtpass;
    private ProgressDialog progessDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progessDialog=new ProgressDialog(this);
        setContentView(R.layout.activity_traveller_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mauth=FirebaseAuth.getInstance();
        txtemail = (TextView)findViewById(R.id.tvemail);
        txtpass = (TextView)findViewById(R.id.tvpass);

        t1_email=(EditText)findViewById(R.id.editText_email);
        t1_password=(EditText)findViewById(R.id.editText_password);
        t1_login=(Button)findViewById(R.id.button_login);
        t1_newUser=(Button)findViewById(R.id.button_newUser);
        t1_forgetPassword=(Button)findViewById(R.id.button_forgetPassword);
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null && firebaseAuth.getCurrentUser().isEmailVerified())
                {finish();
                    Intent i=new Intent(Traveller_login.this,three.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(Traveller_login.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                }
            }
        };
        t1_newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Traveller_login.this,two.class);
                startActivity(i);
            }
        });
        t1_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Traveller_login.this,four.class);
                startActivity(i);
            }
        });

        t1_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignin();
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
    protected void onStart()
    {
        super.onStart();
        mauth.addAuthStateListener(mAuthListener);
    }
    private void startSignin()
    {
        String email=t1_email.getText().toString().trim();
        String password=t1_password.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            txtemail.setText("Please enter email");
            Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
        }
        else if( TextUtils.isEmpty(password))
        {
            txtpass.setText("Please enter password");
        }
        else
        {
            progessDialog.setMessage("Logging In.....");
            progessDialog.show();

           txtemail.setText("");
           txtpass.setText("");
            mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        progessDialog.dismiss();
                        Toast.makeText(Traveller_login.this,"Sign-In Problem.Email or password maybe incorrect.",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        finish();
                    }
                }
            });
        }

    }
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override

        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Intent i=new Intent(Traveller_login.this,five.class);
            startActivity(i);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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
        getMenuInflater().inflate(R.menu.traveller_login, menu);
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
            Intent i=new Intent(Traveller_login.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(Traveller_login.this,Contact_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
