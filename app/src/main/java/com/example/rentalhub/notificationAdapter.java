package com.example.rentalhub;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import Interface.HomesInFeedInterface;
import Model.Renters;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.viewHolder> {
    List<Renters> mData;
    Context mContext;

    public notificationAdapter(List<Renters> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }


    @Override
    public viewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rent_list_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder( viewHolder holder, int position) {


        holder.nam.setText(mData.get(position).getName());
        holder.contact.setText(mData.get(position).getPhone());
        holder.adres.setText(mData.get(position).getAddress());
        holder.homenam.setText( mData.get( position ).getHomeName() );
        holder.b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String number=holder.contact.getText().toString();
                    Intent intent=new Intent(  Intent.ACTION_DIAL  );
                    intent.setData( Uri.parse("tel:"+number) );
                    v.getContext().startActivity( intent );
            }
        } );
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView nam, contact, adres,homenam,b1;
        ///public Button b1;
        public viewHolder(View itemView) {
            super(itemView);
            nam = itemView.findViewById(R.id.nam);
            contact = itemView.findViewById(R.id.cnctNo);
            adres = itemView.findViewById(R.id.adres);
            homenam=itemView.findViewById( R.id.homenam );
            b1=itemView.findViewById( R.id.callBtn );
        }

    }

}
