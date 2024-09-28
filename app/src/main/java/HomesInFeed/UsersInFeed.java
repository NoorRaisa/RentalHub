package HomesInFeed;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalhub.R;

import Interface.UsersInFeedInterface;

public class UsersInFeed extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView Name,emailorg,numborg;
    public ImageView HIFuserPic;
    public UsersInFeedInterface listener;
    ///private View view;

    public UsersInFeed(View itemView)
    {
        super(itemView);
        Name=(TextView)itemView.findViewById( R.id.Name);
        emailorg=(TextView)itemView.findViewById(R.id.emailorg);
        numborg=(TextView)itemView.findViewById(R.id.numborg);
        HIFuserPic=(ImageView)itemView.findViewById(R.id.Pic);

    }
    public void setItemClickListener(UsersInFeedInterface listener)
    {
        this.listener=listener;
    }
    @Override
    public void onClick(View view){

        listener.onClick(view,getAdapterPosition(),false);
    }
}
