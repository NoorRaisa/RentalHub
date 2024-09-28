package com.example.rentalhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OrgProfileFragment extends Fragment {
    Button b1,b2,b3,b4;
    TextView name,email,num,add;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    ImageView pro_pic;
    String phone;
    public OrgProfileFragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_org_profile,null);
        b1=v.findViewById(R.id.edit);
        b2=v.findViewById(R.id.post);
        b3=v.findViewById(R.id.req);
        b4=v.findViewById( R.id.logoutbtn );
        name=v.findViewById( R.id.profile_nameorg );
        email=v.findViewById( R.id.profile_emailorg );
        num=v.findViewById( R.id.profile_mobileorg );
        pro_pic=v.findViewById( R.id.circleImageVieworg );
        auth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Org Users");
        retrivedata();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),EditProfileOrg.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),MyPosts.class);
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),Requests.class);
                startActivity(intent);
            }
        });

        b4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(getActivity(),LoginSelection.class);
                intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity( intent );
            }
        } );
        return v;


    }

    private void retrivedata() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild("Image")) {
                    String image = snapshot.child("Image").getValue().toString();
                    Picasso.get().load(image).into(pro_pic);
                }

                if (snapshot.hasChild("Phone")) {
                    String cn = snapshot.child("Phone").getValue().toString();
                    num.setText(cn);
                }
                if (snapshot.hasChild("Name")) {
                    String cn = snapshot.child("Name").getValue().toString();
                    name.setText(cn);
                }
                if (snapshot.hasChild("Email")) {
                    String cn = snapshot.child("Email").getValue().toString();
                    email.setText(cn);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText( getContext(),"Error",Toast.LENGTH_SHORT ).show();
            }
        });
    }

}