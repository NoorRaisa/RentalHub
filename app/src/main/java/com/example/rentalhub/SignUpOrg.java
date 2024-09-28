package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

public class SignUpOrg extends AppCompatActivity {
    TextView t1;
    EditText name,email,number,pass,add;
    Button b1;
    ImageView i;
    ProgressDialog pd;
    private boolean passwordshowing;
    private DatabaseReference rootRef;
    FirebaseAuth a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_org);
        t1=(TextView) findViewById(R.id.signinBtnorgs);
        name=(EditText)findViewById(R.id.nameETorgs);
        email=(EditText)findViewById(R.id.emailETorgs);
        number=(EditText)findViewById(R.id.numberETorgs);
        pass=(EditText)findViewById(R.id.passwordETorgs);
        i=(ImageView)findViewById(R.id.passwordIconorgs);
        pd=new ProgressDialog(this);
        b1=(Button)findViewById(R.id.signupBtnorgs);
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
            public void onClick(View view) {
                Intent intent=new Intent(SignUpOrg.this,LoginOrg.class);
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


                if(nme.isEmpty() || mail.isEmpty() || num.isEmpty()  || password.isEmpty()  ||!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
                {
                    Toast.makeText(SignUpOrg.this,"Please Enter Valid Information",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(password.length()<8)
                {
                    Toast.makeText(SignUpOrg.this,"Password Must Be Atleast 8 Characters",Toast.LENGTH_SHORT).show();
                    System.out.println(password.length());
                    return ;
                }
                else
                {
                    reguser(nme,mail,num,password);
                }
            }
        });
    }
    void reguser(String name ,String email,String number,String pass)
    {
        pd.setMessage("Please Wait");
        pd.show();
        a.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>(){

            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map= new HashMap<>();
                map.put("Name",name);
                map.put("Email",email);
                map.put("Phone",number);
                map.put("Password",pass);
                map.put("id",a.getCurrentUser().getUid());


                rootRef.child("Org Users").child(a.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void>task) {
                        if(task.isSuccessful()){
                            pd.dismiss();
                            Intent intent= new Intent(SignUpOrg.this, LoginOrg.class);
                            intent.putExtra("Mobile",number);
                            /*a.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast return;
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });*/
                            Toast.makeText(SignUpOrg.this, "Successfully Registerd! Please login now", Toast.LENGTH_SHORT).show();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                Toast.makeText(SignUpOrg.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}