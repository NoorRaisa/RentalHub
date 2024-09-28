package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Payment extends AppCompatActivity {
ImageButton b1,b2,b3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        b1= (ImageButton) findViewById( R.id.button1 );
        b2=(ImageButton) findViewById( R.id.button2 );
        b3=(ImageButton) findViewById( R.id.button3 );
        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Payment.this,UnderCons.class);
                startActivity( intent );
            }
        } );
        b2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Payment.this,UnderCons.class);
                startActivity( intent );
            }
        } );
        b3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Payment.this,UnderCons.class);
                startActivity( intent );
            }
        } );
    }
}