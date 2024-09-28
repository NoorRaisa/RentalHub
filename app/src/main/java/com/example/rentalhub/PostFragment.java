package com.example.rentalhub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class PostFragment extends Fragment {
    ImageButton up;
    int Gallery=1;
    Uri uri;
    EditText name, add, rent, mob, room, des,adv;
    String saveCurrentDate, saveCurrentTime;
    private String randomKey;
    private FirebaseAuth auth;
    private StorageReference picOfPostHome;
    FirebaseStorage storage;
    private FirebaseFirestore firestore;
    private DatabaseReference databaseReference;
    private DatabaseReference postDataRef;
    Button b1;
    ProgressDialog pd;
    String namehome,address,mobile,rooms,rnt,desc,advance;
    String number;
    private String downloadUri;

    public PostFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, null);
        pd=new ProgressDialog( getContext() );
        firestore=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();
        picOfPostHome=storage.getReference().child( "home_pictures" );
        postDataRef = FirebaseDatabase.getInstance().getReference().child("Rent_posts");
        up=v.findViewById(R.id.upload);
        name = v.findViewById(R.id.homeName);
        b1 = v.findViewById(R.id.btn);
        add=v.findViewById( R.id.address );
        rent = v.findViewById(R.id.rent);
        mob = v.findViewById(R.id.mobile);
        room = v.findViewById(R.id.room);
        des = v.findViewById(R.id.des);
        adv=v.findViewById( R.id.advance );
        auth= FirebaseAuth.getInstance();
        ///mob.setText( number );
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        up.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View v) {
                ///CropImage.activity().start(getActivity());
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"title"),Gallery=1);
            }
        });

        b1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectdata();
            }
        } );

    return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery  && data!=null)
        {

            uri=data.getData();
            up.setImageURI(uri);
        }
        else
        {
            Toast.makeText( getContext(),"Something Is Wrong",Toast.LENGTH_SHORT ).show();
        }
    }
    void collectdata()
    {
        namehome=name.getText().toString();
        address=add.getText().toString();
        rnt=rent.getText().toString();
        mobile=mob.getText().toString();
        rooms=room.getText().toString();
        desc=des.getText().toString();
        advance=adv.getText().toString();


        if(TextUtils.isEmpty(mobile) || TextUtils.isEmpty(namehome) || uri==null || TextUtils.isEmpty(address) || TextUtils.isEmpty(rnt) || TextUtils.isEmpty(rooms) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(advance))
        {
            Toast.makeText(getContext(), "Please Provide All the Informations.", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
        else
        {
            storedata();
        }
    }

    private void storedata() {
        pd.setMessage( "Posting" );
        pd.show();
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate= currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("HH: mm: ss a");
        saveCurrentTime= currentTime.format(calendar.getTime());

        randomKey= saveCurrentDate+ saveCurrentTime;


        ///collectdata();
        if(uri!=null)
        {
            StorageReference reference= picOfPostHome.child(uri.getLastPathSegment()+ randomKey + ".jpg");
            final UploadTask uploadTask= reference.putFile(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e)
                {
                    ///pd.dismiss();
                    String message = e.toString();
                    Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(getContext(), "Product Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    ///pd.dismiss();

                    Task<Uri> urlTask = uploadTask.continueWithTask( new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then( Task<UploadTask.TaskSnapshot> task) throws Exception
                        {
                            if (!task.isSuccessful())
                            {
                                throw task.getException();
                            }

                            downloadUri = reference.getDownloadUrl().toString();
                            return reference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete( Task<Uri> task) {
                            if(task.isSuccessful()){
                                downloadUri=task.getResult().toString();
                                Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
                                updatedatabase();
                            }
                        }
                    });
                }
            });
        }
    }

    private void updatedatabase() {
        HashMap<String, Object> map= new HashMap<>();
        map.put("pId",randomKey);
        map.put("date",saveCurrentDate);
        map.put("time",saveCurrentTime);
        map.put("HomeName",namehome);
        map.put("Address",address);
        map.put("Rent",rnt);
        map.put("Mobile", mobile);
        map.put("Rooms",rooms);
        map.put("Image",downloadUri);
        map.put("Description",desc);
        map.put( "Advance",advance );
        map.put("Publisher", FirebaseAuth.getInstance().getCurrentUser().getUid());

        postDataRef.child(randomKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete( Task<Void> task) {
                if(task.isSuccessful()){

                    pd.dismiss();
                    Toast.makeText(getContext(), "Posted", Toast.LENGTH_SHORT).show();
                    name.setText( "" );
                    add.setText( "" );
                    rent.setText( "" );
                    mob.setText( "" );
                    room.setText( "" );
                    des.setText( "" );
                    adv.setText( "" );
                    startActivity( new Intent( getContext(), UserHome.class ) );
                }
                else{
                    pd.dismiss();
                    String msg=task.getException().toString();
                    Toast.makeText(getContext(), "Error:"+msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
