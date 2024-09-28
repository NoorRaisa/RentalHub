package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;

public class Search_Result extends AppCompatActivity {
 String searchpoint="";
    String index="";
    Query ref;
 DatabaseReference HomeRef;
 RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
Query reference;
Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Bundle bundle = getIntent().getExtras();
        searchpoint = bundle.getString("message");
        index=bundle.getString( "index" );
        recyclerView =findViewById(R.id.recycler_menu_s);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
        ///HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
         ///reference= FirebaseDatabase.getInstance().getReference().child( "Rent_posts" );


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(index.equals( "1" ))
        {
            reference=  FirebaseDatabase.getInstance().getReference().child( "Rent_posts" ).orderByChild( "Address" ).equalTo( searchpoint );
        }
        else if(index.equals( "2" ))
        {
            reference=  FirebaseDatabase.getInstance().getReference().child( "Rent_posts" ).orderByChild( "Rent" ).equalTo( searchpoint );
        }
        else if(index.equals( "3" ))
        {
            reference=  FirebaseDatabase.getInstance().getReference().child( "Rent_posts" ).orderByChild( "Rooms" ).equalTo( searchpoint );
        }
        FirebaseRecyclerOptions<HomeInFeedModel> option =
                new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(reference, HomeInFeedModel.class).build();

        //FirebaseRecyclerOptions<HomeInFeedModel> option = new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(HomeRef, HomeInFeedModel.class).build();
        FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed> adapter = new FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed>(option) {
            @Override
            protected void onBindViewHolder( HomesInFeed holder, int position,  HomeInFeedModel model) {

                holder.HIFapartmentname.setText(model.getHomeName());
                holder.HIFrent.setText(model.getRent());
                holder.HIFrooms.setText(model.getRooms());
                holder.HIFlocalAreaName.setText(model.getAddress());
                Picasso.get().load(model.getImage()).into(holder.HIFhomePic);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pId=model.getpId();
                        String publisher=model.getPublisher();
                        String homename=model.getHomeName();
                        Bundle bundle=new Bundle();
                        bundle.putString( "pId",pId );
                        bundle.putString( "publisher",publisher );
                        bundle.putString( "homename",homename );
                        Intent intent=new Intent(Search_Result.this, HomeDetails.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }


            @Override
            public HomesInFeed onCreateViewHolder( ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
                HomesInFeed holder = new HomesInFeed(view);
                return holder;
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}