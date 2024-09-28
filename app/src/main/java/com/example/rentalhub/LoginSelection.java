package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LoginSelection extends AppCompatActivity {
Button userbtn;
FirebaseAuth mAuth;
FirebaseAuth a;
Button organization;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_selection);
        mAuth=FirebaseAuth.getInstance();

        userbtn=(Button) findViewById(R.id.user);
        organization=(Button) findViewById(R.id.organization);
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null)
                {
                    Intent intent=new Intent(LoginSelection.this,UserHome.class);
                    startActivity(intent);
                }
               else {
                    Intent intent=new Intent(LoginSelection.this,Login.class);
                    startActivity(intent);
                }
            }
        });
        organization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent=new Intent(LoginSelection.this,LoginOrg.class);
                    startActivity(intent);


            }
        });



    }

   /* @Override
    protected void onStart() {
        super.onStart();
        userbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null)
                {
                    Intent intent=new Intent(LoginSelection.this,UserHome.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent=new Intent(LoginSelection.this,Login.class);
                    startActivity(intent);
                }
            }
        } );

    }*/
}