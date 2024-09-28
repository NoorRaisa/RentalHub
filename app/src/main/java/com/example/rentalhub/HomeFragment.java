package com.example.rentalhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import HomesInFeed.HomesInFeed;
import Model.HomeInFeedModel;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    String number;
    TextView t1;
    public HomeFragment()
    {

    }
    FirebaseAuth mAuth;
    private DatabaseReference HomeRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate( R.layout.recychome, null );
        ///t1=v.findViewById( R.id.tv77 );
        Bundle bundle=this.getArguments();
        ///String bn=bundle.getString( "Mobile" );
        ///t1.setText(bn);
        recyclerView =v.findViewById(R.id.recycler_menu1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        HomeRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<HomeInFeedModel> option = new FirebaseRecyclerOptions.Builder<HomeInFeedModel>().setQuery(HomeRef, HomeInFeedModel.class).build();
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
                        Intent intent=new Intent(getActivity(), HomeDetails.class);
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

