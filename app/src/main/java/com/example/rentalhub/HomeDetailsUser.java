package com.example.rentalhub;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Model.HomeInFeedModel;


import androidx.appcompat.app.AppCompatActivity;

public class HomeDetailsUser extends AppCompatActivity {
    Button b1,b2;
    private TextView HDhomeName, HDarea, HDrent, HDrooms, HDdescription,HDadvance,HDphone;
    private ImageView HDhomePic;
    private String homeID="",publisher="",homename="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details);
        b1=(Button) findViewById(R.id.rent);
        b2=(Button)findViewById( R.id.call );
        HDhomeName=(TextView) findViewById( R.id.HDhomeName );
        HDarea=(TextView)findViewById( R.id.HDarea );
        HDrent=(TextView)findViewById( R.id.HDrent );
        HDrooms=(TextView)findViewById( R.id.HDrooms );
        HDdescription=(TextView)findViewById( R.id.HDdescription );
        HDadvance=(TextView)findViewById( R.id.HDadvance );
        HDhomePic=(ImageView)findViewById( R.id.HDhomePic );
        HDphone=(TextView)findViewById( R.id.HDphone );
        HDhomeName.setPaintFlags( HDhomeName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG );
        b1.setEnabled( false );
        b1.setBackgroundColor( Color.parseColor("#d6d6c2") );
        b2.setEnabled( false );
        b2.setBackgroundColor( Color.parseColor("#d6d6c2") );
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            homeID=bundle.getString( "pId" );
            ///publisher=bundle.getString( "publisher" );
            ///homename=bundle.getString( "homename" );
        }
        //Intent intent=getIntent();
        //homeID=intent.getStringExtra( "pId" );

        getHomeDetails(homeID);

        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                bundle.putString( "homename",homename );
                bundle.putString( "publisher",publisher );
                Intent intent=new Intent(HomeDetailsUser.this,ConfirmRent.class);
                intent.putExtras( bundle );
                startActivity(intent);
            }
        });*/


    }

    private void getHomeDetails(String homeID) {
        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
        homeRef.child(homeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    HomeInFeedModel home =  snapshot.getValue(HomeInFeedModel.class);
                    HDhomeName.setText(home.getHomeName());
                    HDdescription.setText(home.getDescription());
                    HDrent.setText(home.getRent());
                    HDrooms.setText(home.getRooms());
                    HDarea.setText(home.getAddress());
                    HDadvance.setText( home.getAdvance() );
                    HDphone.setText( home.getMobile() );
                    Picasso.get().load(home.getImage()).into(HDhomePic);

                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
}