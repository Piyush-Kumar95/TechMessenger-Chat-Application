package com.example.whatchat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatPage extends AppCompatActivity {

    String reciverUid,reciverName,SenderUID;
    TextView reciverNName;

    CardView sendbtn;
    EditText textmsg;

    RecyclerView messageRecycle;
    messageAdapter messagesAdapter;


    String senderRoom, reciverRoom;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;

    ArrayList<msgModelClass> messageArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_page);

        sendbtn = findViewById(R.id.sendbtnn);
        textmsg = findViewById(R.id.textmsg);
        messageRecycle = findViewById(R.id.msgadpter);

        messageArrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageRecycle.setLayoutManager(linearLayoutManager);

        messagesAdapter = new messageAdapter(ChatPage.this,messageArrayList);
        messageRecycle.setAdapter(messagesAdapter);




        reciverName = getIntent().getStringExtra("namee");
        reciverUid = getIntent().getStringExtra("uid");

        reciverNName = findViewById(R.id.recivername);
        reciverNName.setText(""+reciverName);

        SenderUID = FirebaseAuth.getInstance().getUid();

        senderRoom = SenderUID+reciverUid;
        reciverRoom = reciverUid+SenderUID;

        DatabaseReference chatReference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    msgModelClass messages = dataSnapshot.getValue(msgModelClass.class);
                    messageArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textmsg.getText().toString();

                if (message.isEmpty()){
                    Toast.makeText(ChatPage.this,"Enter Message First",Toast.LENGTH_SHORT).show();
                }
                textmsg.setText("");
                Date date = new Date();

                msgModelClass messages = new msgModelClass(message,SenderUID,date.getTime());
                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats").child(senderRoom).child("messages").push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats").child(reciverRoom).child("messages").push()
                                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });




            }
        });

    }
}