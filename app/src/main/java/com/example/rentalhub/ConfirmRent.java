package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.core.utils.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmRent extends AppCompatActivity {
Button pay;
EditText name ,mob,add;
String saveCurrentDate;
String saveCurrentTime;
FirebaseAuth mAuth;
DatabaseReference rentRef;
String pId="",homename="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_rent);

        name=(EditText) findViewById( R.id.editText1 );
        mob=(EditText) findViewById( R.id.editText2 );
        add=(EditText) findViewById( R.id.editText3 );
        pay=(Button) findViewById( R.id.payment );
        ///Intent intent=getIntent();
        ///pId=intent.getStringExtra( "publisher" );
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            pId=bundle.getString( "publisher" );
            homename=bundle.getString( "homename" );
        }
        mAuth=FirebaseAuth.getInstance();
        pay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checking();
            }
        } );

    }

    private void checking() {
        if (TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(this, "Please Provide All the Informations", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(add.getText().toString()))
        {
            Toast.makeText(this, "Please Provide All the Informations", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(mob.getText().toString()))
        {
            Toast.makeText(this, "Please Provide All the Informations", Toast.LENGTH_SHORT).show();
        }
        else{
            confirmation();
        }
    }

    private void confirmation() {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH: mm: ss a");
        saveCurrentTime= currentTime.format(calendar.getTime());
        rentRef= FirebaseDatabase.getInstance().getReference().child("ConfirmRent");

        HashMap<String,Object> map= new HashMap<>();
        map.put("Name",name.getText().toString());
        map.put("Phone",mob.getText().toString());
        map.put("Address",add.getText().toString());
        map.put( "HomeName",homename );
        // map.put("Publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map. put("State","Not Rented yet");
        map.put("ID",pId);
        String key=name.getText().toString();
        rentRef.child(key).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete( Task<Void> task) {
                if (task.isSuccessful()) {
                    name.setText( "" );
                    mob.setText( "" );
                    add.setText( "" );
                    Toast.makeText( ConfirmRent.this, "Request Sent Succesfully.Please Pay Your Advance Money", Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent( ConfirmRent.this, Payment.class ) );

                }
            }
        });
    }
}