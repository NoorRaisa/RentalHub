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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginOrg extends AppCompatActivity {
    TextView t1,t2;
    Button b1;
    EditText e1,e2;
    ImageView i;
    FirebaseAuth mauth;
    ProgressDialog pd;
    private boolean passwordshowing=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_org);
        mauth=FirebaseAuth.getInstance();
        t1=(TextView) findViewById(R.id.signUpBtnorg);
        b1=(Button)findViewById(R.id.logInBtnorg);
        e1=(EditText)findViewById( R.id.emailETorg );
        t2=(TextView)findViewById( R.id.forgetPasswordbtnorg );
        e2=(EditText)findViewById( R.id.passwordETorg);
        i=(ImageView)findViewById( R.id.passwordIconorg );
        pd=new ProgressDialog(this);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginOrg.this,SignUpOrg.class);
                startActivity(intent);
            }
        });

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordshowing)
                {
                    passwordshowing=false;
                    e2.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
        t2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginOrg.this,cngpassorg.class);
                startActivity(intent);
            }
        } );
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=e1.getText().toString();
                String pass=e2.getText().toString();
                if(email.isEmpty() || pass.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(LoginOrg.this,"Please Enter a Valid Email or Password",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(pass.length()<8)
                {
                    Toast.makeText(LoginOrg.this,"Password Must Be Atleast 8 Characters",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else
                {
                    pd.setMessage("Please Wait");
                    pd.show();
                    mauth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        ///if(mAuth.getCurrentUser().isEmailVerified())
                                        //{
                                        Intent intent = new Intent(LoginOrg.this, OrgHome.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        pd.dismiss();

                                        /// }
                                        // Sign in success, update UI with the signed-in user's information

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(LoginOrg.this, "Please Verify Your Email ID",
                                                Toast.LENGTH_SHORT).show();
                                        pd.dismiss();

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            pd.dismiss();
                            Toast.makeText(LoginOrg.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}