package com.example.rentalhub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class cngpassorg extends AppCompatActivity {
    EditText e1;
    Button b1;
    String email;
    FirebaseAuth mAuth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cngpassorg );
        e1 = (EditText) findViewById(R.id.cngpassorg);
        b1 = (Button) findViewById(R.id.cngbtnorg);
        pd=new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }
    private void validateData()
    {
        email=e1.getText().toString();
        if(email.isEmpty() ||!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(cngpassorg.this,"Invalid Email",Toast.LENGTH_SHORT).show();
        }
        else
        {
            forgetpass();
        }
    }
    private void forgetpass()
    {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(cngpassorg.this,"Check Your Mail",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(cngpassorg.this,LoginOrg.class));
                }
                else
                {
                    Toast.makeText(cngpassorg.this,"Something Is Wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}