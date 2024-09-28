package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
TextView cng;
Button save;
EditText name,email,phone,add;
ProgressDialog pd;
private CircleImageView pro_pic1;
    private FirebaseUser cur_user;
    private Uri mImgUri;
    private StorageTask uploadTask;
    private StorageReference stroageRef;
    private Uri imageUri;
    private String downloadUri,image,myUrl;
    private String check="";
    private  int galleryPic = 1;
    private FirebaseAuth auth;

    private DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
/////Image diye db te save korbo
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        cng=(TextView) findViewById(R.id.cngPic);
        name=(EditText)findViewById( R.id.nameu );
        email=(EditText)findViewById( R.id.emailu );
        phone=(EditText)findViewById( R.id.cntNou) ;
        add=(EditText)findViewById( R.id.address1u );
        pd=new ProgressDialog(this);
        ///retrivePicture();
        pro_pic1 = findViewById(R.id.pro_pic);
        save=(Button)findViewById( R.id.saveu );
        stroageRef = FirebaseStorage.getInstance().getReference().child("Uploads");
        cur_user = FirebaseAuth.getInstance().getCurrentUser();
        auth= FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        retrivepicture();
        userinfoDisplay(pro_pic1,name,email,add,phone);
        cng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check="clicked";
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), galleryPic = 1);
            }
        });
        save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateProfile();
                }
            }
        } );

    }

    private void userinfoDisplay(CircleImageView pro_pic1, EditText name, EditText email, EditText add, EditText phone) {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (snapshot.hasChild("Image")) {
                    String image = snapshot.child("Image").getValue().toString();
                    Picasso.get().load(image).into(pro_pic1);
                }

                if (snapshot.hasChild("Address")) {
                    String address = snapshot.child("Address").getValue().toString();
                    add.setText(address);
                }
                if (snapshot.hasChild("Phone")) {
                    String cn = snapshot.child("Phone").getValue().toString();
                    phone.setText(cn);
                }
                if (snapshot.hasChild("Name")) {
                    String cn = snapshot.child("Name").getValue().toString();
                    name.setText(cn);
                }
                if (snapshot.hasChild("Email")) {
                    String cn = snapshot.child("Email").getValue().toString();
                    email.setText(cn);
                    email.setEnabled( false );
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditProfile.this,"Error",Toast.LENGTH_SHORT ).show();
            }
        });
    }

    private void updateProfile()
    {
        pd.setMessage( "Please Wait" );
        pd.show();
        HashMap<String,Object> map= new HashMap<>();
        map.put("Name",name.getText().toString());
        map.put("Email",email.getText().toString());
        map.put("Phone",phone.getText().toString());
        map.put("Address",add.getText().toString());
        ///  map.put("ImageUri",downloadUri);

        FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).updateChildren(map);
        Toast.makeText(EditProfile.this, "Profile Information Updated Successfully.", Toast.LENGTH_SHORT).show();
        pd.dismiss();
        finish();

    }


    private void userInfoSaved() {
        uploadImage();
    }

    private void uploadImage() {
        pd.setMessage("Uploading");
        pd.show();
        if(imageUri!=null)
        {
            final StorageReference file = stroageRef.child(System.currentTimeMillis() + ".jpg");
            UploadTask uploadTask = file.putFile(imageUri);
            uploadTask = file.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return file.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete( Task<Uri> task)
                        {
                            if (task.isSuccessful())
                            {
                                Uri downloadUrl = task.getResult();
                                myUrl = downloadUrl.toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String,Object> map= new HashMap<>();
                                map.put("Name",name.getText().toString());
                                map.put("Email",email.getText().toString());
                                map.put("Phone",phone.getText().toString());
                                map.put("Address",add.getText().toString());
                                map. put("Image", myUrl);
                                map.put("Number",String.valueOf(System.currentTimeMillis()));
                                FirebaseDatabase.getInstance().getReference().child("Users").child(cur_user.getUid()).updateChildren(map);

                                pd.dismiss();
                                Toast.makeText(EditProfile.this, "Profile Information Updated Successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                pd.dismiss();
                                Toast.makeText(EditProfile.this, "Error.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }


    private void retrivepicture() {
        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                if (snapshot.hasChild("Image")) {
                    String image = snapshot.child("Image").getValue().toString();
                    Picasso.get().load(image).into(pro_pic1);
                }

            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText( EditProfile.this,"Error",Toast.LENGTH_SHORT ).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryPic  && data!=null)
        {

            imageUri=data.getData();
            pro_pic1.setImageURI(imageUri);
        }
        else
        {
            Toast.makeText( EditProfile.this,"Something Is Wrong",Toast.LENGTH_SHORT ).show();
        }
    }
}