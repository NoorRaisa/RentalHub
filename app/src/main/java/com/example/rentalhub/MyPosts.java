package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;
import Model.Renters;

public class MyPosts extends AppCompatActivity {
    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Query reference;

    private FirebaseAuth cUser=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        recyclerView =findViewById(R.id.recycler_myPost);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);



        HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");

    }

    @Override
    protected void onStart() {
        super.onStart();
        reference=  FirebaseDatabase.getInstance().getReference().child( "Rent_posts" ).orderByChild( "Publisher" ).equalTo( cUser.getCurrentUser().getUid() );
        FirebaseRecyclerOptions<HomeInFeedModel> option = new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(reference, HomeInFeedModel.class).build();
        FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed> adapter = new FirebaseRecyclerAdapter<HomeInFeedModel, HomesInFeed>(option) {
            @Override
            protected void onBindViewHolder(HomesInFeed holder, int position,  HomeInFeedModel model) {

                holder.HIFapartmentname.setText(model.getHomeName());
                holder.HIFrent.setText(model.getRent());
                holder.HIFrooms.setText(model.getRooms());
                holder.HIFlocalAreaName.setText(model.getAddress());
                Picasso.get().load(model.getImage()).into(holder.HIFhomePic);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pId=model.getpId();
                        ///String publisher=model.getPublisher();
                        ///String homename=model.getHomeName();
                        Bundle bundle=new Bundle();
                        bundle.putString( "pId",pId );
                        ///bundle.putString( "publisher",publisher );
                        ///bundle.putString( "homename",homename );
                        Intent intent=new Intent(MyPosts.this, HomeDetailsUser.class);
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