package com.kushal.samplechat;

import android.content.Intent;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Locale;

public class ContactsActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private Users mUsers;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private TextView mName , mMessage;
    private RelativeLayout mLayout;
    private EditText messageTB;
    private FloatingActionButton send , logout;
    private RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
        String key = mRef.getKey();


        mLayout = (RelativeLayout) findViewById(R.id.chatrl);

        mName = (TextView) findViewById(R.id.nameTV);
        mMessage =  (TextView) findViewById(R.id.mshIV);

        messageTB = (EditText) findViewById(R.id.message);
        send = (FloatingActionButton) findViewById(R.id.send);
        logout = (FloatingActionButton) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutuser();
            }
        });

        recycle = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recycle.setLayoutManager(linearLayoutManager);




        chat();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message();
            }
        });

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                  Usersinfo user = dataSnapshot.getValue(Usersinfo.class);

                    String name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                 //   String msg = user.getMessage();

                    mName.setText(name);
                  //  mMessage.setText(msg);

                }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void logoutuser() {

       FirebaseAuth.getInstance().signOut();

        Intent log = new Intent(ContactsActivity.this , RegiActivity.class);
        startActivity(log);
        finish();
    }


    private Users user;
    private void message() {
     //  String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
       String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String message =  messageTB.getText().toString();
      //  mAuth = FirebaseAuth.getInstance();
        DatabaseReference messages = FirebaseDatabase.getInstance().getReference().child("message").push();
        //        messages.child("name").setValue(user.getName());
        messages.child("email").setValue(email);
        messages.child("message").setValue(message);

        messageTB.setText("");

    }
    private FirebaseRecyclerAdapter<Message , MsgVH> mAdapter;
    private void chat() {

         mRef = FirebaseDatabase.getInstance().getReference();

        Query messageQuery = mRef.child("message");

       mAdapter = new FirebaseRecyclerAdapter<Message, MsgVH>(Message.class , R.layout.item_message , MsgVH.class , messageQuery) {
           @Override
           protected void populateViewHolder(final MsgVH viewHolder,final Message message,final int position) {

               viewHolder.bindToMessage(message, new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                   }
               });
           }
       };

       recycle.setAdapter(mAdapter);
    }

}
