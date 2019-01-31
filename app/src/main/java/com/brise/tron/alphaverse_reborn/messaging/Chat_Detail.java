package com.brise.tron.alphaverse_reborn.messaging;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.utilities.SendNotifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat_Detail extends Fragment implements Chat_Adapter.OnItemClickListener {
    public static final String username = "";
    EditText message_content;
    Button send_button;
    RecyclerView chat_holder;
    String currentUser, mirrorUser, time, date,ItemPhoto,ItemName,ItemOwnerPhoto,NotificationId,ItemOwnerName,myName,ShopName;
    CollectionReference sender, receiver;
    private List<Chat_Detail_Adapter_sent> mychats;
    private Chat_Adapter chat_detail_recycler_adapter;

    public Chat_Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentUser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        Bundle getcon = getArguments();
        if (getcon != null)
        {
            mirrorUser = getcon.getString("ownerContact");
            ItemPhoto = getcon.getString("itemPhoto");
            ItemName = getcon.getString("itemName");
            ItemOwnerPhoto = getcon.getString("ownerPhoto");
            ShopName = getcon.getString("owner");
            NotificationId = getcon.getString("ownerNotifId");
            ItemOwnerName = getcon.getString("ownerName");
        }
        SharedPreferences status = Objects.requireNonNull(getActivity()).getSharedPreferences(username, Context.MODE_PRIVATE);
        myName = status.getString("userName",null);
        receiver = FirebaseFirestore.getInstance().collection("Users").document(mirrorUser).collection("Messages").document("Chats with "+ myName).collection("Content");
        sender = FirebaseFirestore.getInstance().collection("Users").document(currentUser).collection("Messages").document("Chats with "+ ItemOwnerName).collection("Content");

        View Remedy = inflater.inflate(R.layout.fragment_chat__detail, container, false);
        message_content = Remedy.findViewById(R.id.send_topic);
        send_button = Remedy.findViewById(R.id.send_buton);
        chat_holder = Remedy.findViewById(R.id.chat_detail_list);

        chat_holder.setHasFixedSize(true);
        final LinearLayoutManager Lmanager = new LinearLayoutManager(getActivity());
        chat_holder.setLayoutManager(Lmanager);

        FindDateTime();

        mychats = new ArrayList<>();
        chat_detail_recycler_adapter = new Chat_Adapter(mychats, getActivity());
        chat_holder.setAdapter(chat_detail_recycler_adapter);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendText();
            }
        });

        sender.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!Objects.requireNonNull(queryDocumentSnapshots).isEmpty())
                {
                    mychats.clear();

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot: list)
                    {
                        Chat_Detail_Adapter_sent someAdapter = documentSnapshot.toObject(Chat_Detail_Adapter_sent.class);
                        Chat_Detail_Adapter_sent chats = new Chat_Detail_Adapter_sent();
                        if (someAdapter != null)
                        {
                            String Sid = someAdapter.getSender_Id();
                            String cont = someAdapter.getMessage_Content();
                            String timest = someAdapter.getChat_Time_Stamp();
                            String iphot = someAdapter.getPhoto_Id();
                            String iname = someAdapter.getItem_Name();
                            String ownerNum = someAdapter.getCheck_Contact();
                            String admin = someAdapter.getAdmin();
                            String otherid = someAdapter.getOtherID();

                            chats.setSender_Id(Sid);
                            chats.setMessage_Content(cont);
                            chats.setChat_Time_Stamp(timest);
                            chats.setPhoto_Id(iphot);
                            chats.setItem_Name(iname);
                            chats.setCheck_Contact(ownerNum);
                            chats.setAdmin(admin);
                            chats.setOtherID(otherid);
                        }

                        mychats.add(someAdapter);

                    }

                    chat_detail_recycler_adapter.notifyDataSetChanged();
                    chat_detail_recycler_adapter.setOnItemClickListener(Chat_Detail.this);

                }
                }
        });


        return Remedy;
    }

    private void sendText() {
        String Message_Content, Chat_Time_Stamp,itPhoto,item_name,my_name,Check_Contact;
        Message_Content = message_content.getText().toString();
        Chat_Time_Stamp = date + " - " + time;


        if (!TextUtils.isEmpty(Message_Content)) {

            String TXTRN = String.valueOf(System.currentTimeMillis());

            Chat_Detail_Adapter_sent mr_tron = new Chat_Detail_Adapter_sent(myName,ItemName, Message_Content, Chat_Time_Stamp,ItemPhoto,mirrorUser,TXTRN,ItemOwnerName);


            sender.document(TXTRN).set(mr_tron);
            receiver.document(TXTRN).set(mr_tron);

            message_content.setText("");
            Toast.makeText(getActivity(), "message sent", Toast.LENGTH_LONG).show();

            new SendNotifications(Message_Content,"new message from "+ myName,NotificationId);

        } else {
            Toast.makeText(getActivity(), "Nothing to send", Toast.LENGTH_LONG).show();
        }

    }

    private void FindDateTime() {

        Calendar timestamp = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeformat = new SimpleDateFormat("HH : mm : ss");
        time = timeformat.format(timestamp.getTime());
        date = DateFormat.getDateInstance(DateFormat.FULL).format(timestamp.getTime());
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void on_Delete_Click(int position) {
        Chat_Detail_Adapter_sent Chat_Admin = mychats.get(position);
        String chat_ID = Chat_Admin.getAdmin();

        if (chat_ID != null)
        {
            sender.document(chat_ID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getActivity(), "Chat Deleted", Toast.LENGTH_LONG).show();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public void on_Forward_Click(int position) {

    }
}
