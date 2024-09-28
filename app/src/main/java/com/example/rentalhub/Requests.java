package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.Renters;

public class Requests extends AppCompatActivity {
    private RecyclerView rentList;
    String cn;
    String mob;
    RecyclerView.LayoutManager layoutManager;
    StorageReference storageReference;
    DatabaseReference user=FirebaseDatabase.getInstance().getReference().child( "Users" );
    private FirebaseUser cUser;
    notificationAdapter adapter;
    FirebaseStorage storage;
    List<Renters> mData;
    DatabaseReference rentRef= FirebaseDatabase.getInstance().getReference("ConfirmRent");
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_requests );
        rentList= findViewById(R.id.rent_recyler);
        // rentList.setLayoutManager(new LinearLayoutManager(this));
        rentList.setHasFixedSize(true);
        Log.d("checked","here");
        mData = new ArrayList<>();
        layoutManager = new LinearLayoutManager( this);
        rentList.setLayoutManager(layoutManager);
        adapter = new notificationAdapter(mData,rentList.getContext());  ///new
        rentList.setAdapter(adapter);    ///new
        cUser = FirebaseAuth.getInstance().getCurrentUser();
        ///retrivedata();
        data();

    }

    /*private void retrivedata() {
        user.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild("Phone")) {
                    cn = snapshot.child("Phone").getValue().toString();
                    mob=cn;

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText( Requests.this,"Error",Toast.LENGTH_SHORT ).show();
            }
        });
    }*/

    private void data() {
        /*rentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren())
                {
                    Renters data = ds.getValue(Renters.class);
                    Log.d("checked",data.getAddress());
                    mData.add(data);

                }
                Log.d("checked", String.valueOf(mData));
                adapter = new notificationAdapter(mData,rentList.getContext());
                rentList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Log.d("checked","failed to retrieve");
            }
        });*/
        Query query=FirebaseDatabase.getInstance().getReference("ConfirmRent")
                .orderByChild( "ID" ).equalTo( auth.getCurrentUser().getUid());
        query.addListenerForSingleValueEvent( valueEventListener );

    }
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            ///mData.clear();
            if(snapshot.exists())
            {
                for(DataSnapshot snpshot:snapshot.getChildren()){
                    Renters data = snpshot.getValue(Renters.class);
                    mData.add( data );
                    ////rentList.setAdapter(adapter);

                }
                adapter.notifyDataSetChanged();

            }
        }

        @Override
        public void onCancelled(DatabaseError error) {

        }
    };
}

