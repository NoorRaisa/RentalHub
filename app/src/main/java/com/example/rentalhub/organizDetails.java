package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
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
import Model.UserInFeedModel;

public class organizDetails extends AppCompatActivity {
private TextView HDuserName,HDemail,HDphoneorg;
ImageView HDuserpic;
String id;
Button call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_organiz_details );
        HDuserName=(TextView) findViewById( R.id.HDuserName );
        HDemail=(TextView) findViewById( R.id.HDemail );
        HDphoneorg=(TextView) findViewById( R.id.HDphoneorg );
        HDuserpic=(ImageView) findViewById( R.id.HDuserPic );
        call=(Button)findViewById( R.id.callorg );
        HDuserName.setPaintFlags( HDuserName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG );
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            id=bundle.getString( "pId" );
        }
        getuserDetails(id);

        call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number=HDphoneorg.getText().toString();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData( Uri.parse("tel:"+number) );
                startActivity( intent );
            }
        } );
    }

    private void getuserDetails(String id) {
        DatabaseReference homeRef = FirebaseDatabase.getInstance().getReference().child("Org Users");
        homeRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    UserInFeedModel user =  snapshot.getValue(UserInFeedModel.class);
                    HDuserName.setText(user.getName());
                    HDemail.setText( user.getEmail() );
                    HDphoneorg.setText( user.getPhone() );
                    Picasso.get().load(user.getImage()).into(HDuserpic);
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });
    }
}