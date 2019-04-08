package com.example.dell.railtrack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class two extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnsignup,btnlogin;
    EditText edemail,edpass;
    TextView txtemail,txtpass;
    private ProgressDialog progessDialog;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
   DatabaseReference logindatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        firebaseAuth=FirebaseAuth.getInstance();
        logindatabase= FirebaseDatabase.getInstance().getReference("Login");
        progessDialog=new ProgressDialog(this);
        btnsignup = (Button)findViewById(R.id.button2);
        edemail = (EditText)findViewById(R.id.editText1);
        edpass = (EditText)findViewById(R.id.editText2);
        btnlogin=(Button)findViewById(R.id.buttonlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(two.this,Traveller_login.class);
                startActivity(i );
            }
        });
       txtemail = (TextView)findViewById(R.id.tvemail);
      txtpass = (TextView)findViewById(R.id.tvpass);
      btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

                //Intent toalpha = new Intent(two.this,alpha.class);
                //startActivity(toalpha);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
    private void registerUser()
    {
        final String email = edemail.getText().toString().trim();
        final String pass = edpass.getText().toString().trim();
         if(TextUtils.isEmpty(email))
            {
               txtemail.setText("Please enter email");
                Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show();
            }
            else if( TextUtils.isEmpty(pass))
        {
            txtpass.setText("Please enter password");
        }
            else
            {
               progessDialog.setMessage("Registering User.....");
                progessDialog.show();
                txtpass.setText("");
                txtemail.setText("");

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    progessDialog.dismiss();
                     firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if(task.isSuccessful())
                             {
                                 Toast.makeText(two.this, "Registration successfull. Please check your mail box for verification.", Toast.LENGTH_SHORT).show();
                                 edemail.setText("");
                                 edpass.setText("");
                                 finish();
                                Intent i=new Intent(two.this,Traveller_login.class);
                                startActivity(i);
                             }
                             else
                             {
                                 Toast.makeText(two.this, "Could not send mail to specified email.", Toast.LENGTH_SHORT).show();
                             }

                         }
                     });

                }
                else
                    {
                        progessDialog.dismiss();
                    Toast.makeText(two.this, "Not Successfully Registered", Toast.LENGTH_SHORT).show();
                }
            }

           });
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
        getMenuInflater().inflate(R.menu.two, menu);
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
            Intent i=new Intent(two.this,About_us.class);
            startActivity(i);
        } else if (id == R.id.nav_contact_us) {
            Intent i=new Intent(two.this,Contact_us.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
