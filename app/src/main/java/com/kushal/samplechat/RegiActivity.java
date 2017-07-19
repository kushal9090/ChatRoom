package com.kushal.samplechat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegiActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin , mSignin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);


        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mSignin = (Button) findViewById(R.id.signin);
        mLogin = (Button) findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();
       mLogin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               signup();

             //  Intent i = new Intent(RegiActivity.this , ContactsActivity.class);
               //startActivity(i);
           }
       });

       mSignin.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               signin();
           }
       });

    }

    private void signin() {
        final String email  = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    Intent i = new Intent(RegiActivity.this , ContactsActivity.class);
                    startActivity(i);
                }
                else {

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){

            OnAuthSuccess(mAuth.getCurrentUser());
        }

    }

    private void OnAuthSuccess(FirebaseUser currentUser) {
        startActivity(new Intent(RegiActivity.this, ContactsActivity.class));
        finish();
    }

    private void signup() {

        final String email  = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){


                    String uid = mAuth.getCurrentUser().getUid();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);

                    db.child("userid").setValue(uid);
                    db.child("email").setValue(email);

                    Intent i = new Intent(RegiActivity.this , ContactsActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}