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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    Button b1;
    TextView t1,t2;
    EditText e1,e2,e3;
    ImageView i;
    FirebaseAuth mAuth,auth;
    private boolean passwordshowing;
    String mobile;
    String cn;

    private DatabaseReference databaseReference;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        b1=(Button) findViewById(R.id.logInBtn);
        t1=(TextView)findViewById(R.id.signUpBtn);
        t2=(TextView)findViewById(R.id.forgetPasswordbtn);
        e1=(EditText)findViewById(R.id.numberET);
        e2=(EditText)findViewById(R.id.passwordET);
        ///e3=(EditText)findViewById( R.id.ET );
        i=(ImageView)findViewById(R.id.passwordIcon);
        pd= new ProgressDialog(this);
        auth=FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        ///mobile=e3.getText().toString();

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,cngpass.class);
                startActivity(intent);
            }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordshowing)
                {
                    passwordshowing=false;
                    e2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    i.setImageResource(R.drawable.ic_lock_open);
                }
                else
                {
                    passwordshowing=true;
                    e2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    i.setImageResource(R.drawable.ic_lock);
                }
                e2.setSelection(e2.length());
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=e1.getText().toString();
                String pass=e2.getText().toString();
                if(email.isEmpty() || pass.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(Login.this,"Please Enter a Valid Email or Password",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(pass.length()<8)
                {
                    Toast.makeText(Login.this,"Password Must Be Atleast 8 Characters",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else
                {
                    pd.setMessage("Please Wait");
                    pd.show();
                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        if(mAuth.getCurrentUser().isEmailVerified()) {
                                            String ph;
                                            ///ph=e3.getText().toString();
                                            Intent intent = new Intent( Login.this, UserHome.class );
                                            ///intent.putExtra("Mobile",ph);
                                            ///intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity( intent );
                                            finish();
                                            pd.dismiss();
                                        }
                                        else {
                                            // If sign in fails, display a message to the user.

                                            Toast.makeText(Login.this, "Please Verify Your Email ID",
                                                    Toast.LENGTH_SHORT).show();
                                            pd.dismiss();

                                        }

                                        // Sign in success, update UI with the signed-in user's information

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            pd.dismiss();
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                            });

                }
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });
    }

}