package com.example.rentalhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import HomesInFeed.UsersInFeed;
import Interface.UsersInFeedInterface;
import Model.UserInFeedModel;

public class OrgHomeFragment extends Fragment {
    public OrgHomeFragment()
    {

    }
    FirebaseAuth mAuth;
    private DatabaseReference UserRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recychome, null);
        recyclerView =v.findViewById(R.id.recycler_menu1);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager( getContext());
        recyclerView.setLayoutManager(layoutManager);
        mAuth = FirebaseAuth.getInstance();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Org Users");
        return v;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<UserInFeedModel> option = new FirebaseRecyclerOptions.Builder<UserInFeedModel>().setQuery(UserRef, UserInFeedModel.class).build();
        FirebaseRecyclerAdapter<UserInFeedModel, UsersInFeed> adapter = new FirebaseRecyclerAdapter<UserInFeedModel, UsersInFeed>(option) {
            @Override
            protected void onBindViewHolder( UsersInFeed holder, int position,  UserInFeedModel model) {

                holder.Name.setText(model.getName());
                holder.emailorg.setText(model.getEmail());
                holder.numborg.setText(model.getPhone());
                Picasso.get().load(model.getImage()).into(holder.HIFuserPic);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pId=model.getid();
                        ///String publisher=model.getPublisher();
                        ///String homename=model.getHomeName();
                        Bundle bundle=new Bundle();
                        bundle.putString( "pId",pId );
                        ///bundle.putString( "publisher",publisher );
                        ///bundle.putString( "homename",homename );
                        Intent intent=new Intent(getActivity(), organizDetails.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public UsersInFeed onCreateViewHolder( ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_org_home, parent, false);
                UsersInFeed holder = new UsersInFeed(view);
                return holder;
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}