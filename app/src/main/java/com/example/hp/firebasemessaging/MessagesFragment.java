package com.example.hp.firebasemessaging;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MessagesFragment extends Fragment implements View.OnClickListener {

    ArrayList<Item> message;
    RecyclerView rView;
    EditText eTxt;
    ImageView imgSend;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    String msg,m ,d ,Uid, timeStamp, dataS, name, number;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rView= view.findViewById(R.id.rView);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new LinearLayoutManager(getContext()));

        eTxt = view.findViewById(R.id.eTxt);
        imgSend = view.findViewById(R.id.imgSend);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        reference.child("Messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                message = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Item item = data.getValue(Item.class);
                    message.add(item);
                }
                Adapter adapter = new Adapter(message);
                rView.setAdapter(adapter);
                adapter.setOnClickL(new Adapter.OnClickL() {
                    @Override
                    public void onD(final int position) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Deleting").setMessage("Delete Message ?")
                                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delete(position);
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                return;
                            }
                        }).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imgSend.setOnClickListener(MessagesFragment.this);
    }

    @Override
    public void onClick(View v) {
        if(eTxt.getText().toString().equals(null))
        {
            return;
        }
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        String message = eTxt.getText().toString();
        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        Long date = System.currentTimeMillis();
        dataS = String.valueOf(date);

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Uid = firebaseUser.getUid();

        reference.child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){

                    UserData userData = data.getValue(UserData.class);
                     name = userData.getUserName();
                     number = userData.getUserNumber();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Item item = new Item(message, timeStamp, name);

        reference.child("Messages").child(reference.push().getKey()).setValue(item);
        eTxt.setText("");
    }

    public void delete(int position){
        Item item = message.get(position);
        m = item.getMessage();
        d = item.getDate();

        reference.child("Messages").orderByChild("Message").equalTo(msg).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {

                    Item itemf = child.getValue(Item.class);
                    String mf = itemf.getMessage();
                    String df = itemf.getDate();

                    if (m.equals(mf) && d.equals(df)){
                        child.getRef().setValue(null);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
