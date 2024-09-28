package com.example.rentalhub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {
    Button b1;
    EditText add,rent,room;
    String searchpoint="",address,rnt,rm,index="";
    public SearchFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_search,null);
        b1=v.findViewById(R.id.srch);
        add=v.findViewById( R.id.editText1s );
        rent=v.findViewById( R.id.editText2s );
        room=v.findViewById( R.id.editText3s );





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                address=add.getText().toString();
                rnt=rent.getText().toString();
                rm=room.getText().toString();
                if(!TextUtils.isEmpty( address ))
                {
                    searchpoint=address;
                    index="1";
                }
                else if(!TextUtils.isEmpty( rnt ))
                {
                    searchpoint=rnt;
                    index="2";
                }
                else if(!TextUtils.isEmpty( rm ))
                {
                    searchpoint=rm;
                    index="3";
                }
                Bundle bundle=new Bundle();
                bundle.putString( "message",searchpoint );
                bundle.putString( "index",index );
                Intent intent=new Intent(getActivity(),Search_Result.class);
                intent.putExtras(bundle);
                add.setText( "" );
                rent.setText( "" );
                room.setText( "" );
                startActivity(intent);
            }
        });
        return v;


    }
}
