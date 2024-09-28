package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {
TextView t1;
EditText name,email,number,pass,add;
Button b1;
ImageView i;
ProgressDialog pd;
private boolean passwordshowing;
FirebaseAuth a;
private DatabaseReference rootRef;
int var=8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        t1=(TextView) findViewById(R.id.signinBtn);
        name=(EditText)findViewById(R.id.nameET);
        email=(EditText)findViewById(R.id.EmailET);
        number=(EditText)findViewById(R.id.numberET);
        pass=(EditText)findViewById(R.id.passwordET);
        add=(EditText)findViewById(R.id.adressET);
        i=(ImageView)findViewById(R.id.passwordIcon);
        pd=new ProgressDialog(this);
        b1=(Button)findViewById(R.id.signupBtn);
        a= FirebaseAuth.getInstance();
        rootRef= FirebaseDatabase.getInstance().getReference();

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordshowing)
                {
                    passwordshowing=false;
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    i.setImageResource(R.drawable.ic_lock_open);
                }
                else
                {
                    passwordshowing=true;
                    pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    i.setImageResource(R.drawable.ic_lock);
                }
                pass.setSelection(pass.length());
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nme=name.getText().toString();
                String mail=email.getText().toString();
                String num=number.getText().toString();
                String password=pass.getText().toString();
                String address=add.getText().toString();

                if(nme.isEmpty() || mail.isEmpty() || num.isEmpty()  || password.isEmpty() ||address.isEmpty() ||!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                {
                    Toast.makeText(SignUp.this,"Please Enter Valid Information",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(password.length()<8)
                {
                    Toast.makeText(SignUp.this,"Password Must Be Atleast 8 Characters",Toast.LENGTH_SHORT).show();
                    System.out.println(password.length());
                    return ;
                }
                else
                {
                    reguser(nme,mail,num,password,address);
                }
            }
        });
    }

    void reguser(String name,String email,String number,String pass,String address)
    {
        pd.setMessage("Please Wait");
        pd.show();
        a.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map= new HashMap<>();
                map.put("Name",name);
                map.put("Email",email);
                map.put("Phone",number);
                map.put("Password",pass);
                map.put("Address",address);
                map.put("id",a.getCurrentUser().getUid());


                rootRef.child("Users").child(a.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if(task.isSuccessful()){
                            pd.dismiss();
                            Intent intent= new Intent(SignUp.this, Login.class);
                            intent.putExtra("Mobile",number);
                            a.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(SignUp.this,"Please Verify Your Email Address",Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                            Toast.makeText(SignUp.this, "Successfully Registerd! Please login now", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                pd.dismiss();
                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }
}