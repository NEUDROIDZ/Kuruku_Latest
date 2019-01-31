package com.brise.tron.alphaverse_reborn.items;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.homepage.FragmentsHolder;
import com.brise.tron.alphaverse_reborn.homepage.HomePage;
import com.brise.tron.alphaverse_reborn.homepage.Recycler_Adapter;
import com.brise.tron.alphaverse_reborn.messaging.Chat_Detail;
import com.brise.tron.alphaverse_reborn.userprofile.Contact_Adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
public class HotMarket extends Fragment implements Recycler_Adapter.OnItemClickListener {
    private static final int CALL_REQUEST = 1;
    public static final String USER_STATE = "MY_PREVIOUS_STATE";
    EditText topic;
    Button launch;
    CollectionReference customers;
    String item_owner_contact,mycontact,time,date;
    private Recycler_Adapter adapter;
    private List<Item_Adapter> mylist;

    public HotMarket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View hotMarket = inflater.inflate(R.layout.fragment_hot_market, container, false);
        RecyclerView HotItems = hotMarket.findViewById(R.id.my_hot_Items);
        Button additem = hotMarket.findViewById(R.id.addhotItem);
        final String owner = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        HotItems.setHasFixedSize(true);
        HotItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        CollectionReference itemsLocation = FirebaseFirestore.getInstance().collection("Items");
        customers = FirebaseFirestore.getInstance().collection("Customers");
        FindDateTime();

        mylist = new ArrayList<>();
        adapter = new Recycler_Adapter(mylist,getActivity());
        HotItems.setAdapter(adapter);
        mycontact = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), FragmentsHolder.class);
                intent.putExtra("fragmentID","ItemHome");
                intent.putExtra("hotmarket","Positive");
                intent.putExtra("ShopName", owner);
                startActivity(intent);
            }
        });


        itemsLocation.whereEqualTo("hotMarket_Status","Positive").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (!Objects.requireNonNull(queryDocumentSnapshots).isEmpty())
                {
                    mylist.clear();
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot: list)
                    {
                        Item_Adapter item_adapter = documentSnapshot.toObject(Item_Adapter.class);
                        Item_Adapter items = new Item_Adapter();
                        if (item_adapter!= null)
                        {
                            String iname = item_adapter.getItem_Name();
                            String iowner = item_adapter.getItem_Owner();
                            String iprice = item_adapter.getItem_Price();
                            String icomment = item_adapter.getItem_Comment();
                            String iphoto = item_adapter.getItem_Reference();
                            String iocontact = item_adapter.getOwner_Contact();
                            String ionotifid = item_adapter.getOwner_Notification_Id();
                            String iouser = item_adapter.getProfilePhoto();
                            String iousername = item_adapter.getProfile_Name();
                            String iorigName = item_adapter.getOrig_Name();
                            String ihotstate = item_adapter.getHotMarket_Status();



                            items.setItem_Name(iname);
                            items.setItem_Owner(iowner);
                            items.setItem_Price(iprice);
                            items.setItem_Comment(icomment);
                            items.setItem_Reference(iphoto);
                            items.setOwner_Contact(iocontact);
                            items.setOwner_Notification_Id(ionotifid);
                            items.setProfilePhoto(iouser);
                            items.setProfile_Name(iousername);
                            items.setOrig_Name(iorigName);
                            items.setHotMarket_Status(ihotstate);

                        }
                        mylist.add(item_adapter);
                    }
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(HotMarket.this);

                }
            }
        });
        return hotMarket;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Normal Click at Position: "+ position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on_chat_Click(int position) {
        Item_Adapter chat_info = mylist.get(position);
        String ownercontact = chat_info.getOwner_Contact();
        String itemphoto = chat_info.getItem_Reference();
        String itemName = chat_info.getItem_Name();
        String itemOwner = chat_info.getItem_Owner();
        String ownerNotifid = chat_info.getOwner_Notification_Id();
        String userPhoto = chat_info.getProfilePhoto();
        String profileName = chat_info.getProfile_Name();

        if (!Objects.equals(mycontact, ownercontact))
        {
            Intent tranx = new Intent(getActivity(), FragmentsHolder.class);
            tranx.putExtra("fragmentID","ChatView");
            tranx.putExtra("ownerContact", ownercontact);
            tranx.putExtra("itemPhoto", itemphoto);
            tranx.putExtra("itemName", itemName);
            tranx.putExtra("owner",itemOwner);
            tranx.putExtra("ownerNotifId",ownerNotifid);
            tranx.putExtra("ownerPhoto",userPhoto);
            tranx.putExtra("ownerName",profileName);
            startActivity(tranx);
        }
        else {
            Toast.makeText(getActivity(), "You Are The Owner Of This Item", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void on_call_Click(int position) {
        Item_Adapter selectedItem = mylist.get(position);
        item_owner_contact = selectedItem.getOwner_Contact();
        String itemname = selectedItem.getItem_Name();


        if (!Objects.equals(mycontact, item_owner_contact))
        {
            Toast.makeText(getActivity(), "calling "+ item_owner_contact +" for " + itemname, Toast.LENGTH_SHORT).show();
            Call_Item_Owner();

        }
        else {
            Toast.makeText(getActivity(), "You Are The Owner Of This Item", Toast.LENGTH_SHORT).show();
        }

    }

    private void Call_Item_Owner() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},CALL_REQUEST);

        }else {
            String launch = "tel: " + item_owner_contact;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(launch)));

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Call_Item_Owner();
            }else {
                Toast.makeText(getActivity(), "Permission To Call " + item_owner_contact , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void on_sms_Click(int position) {

        Toast.makeText(getActivity(), "Sms the seller Click at Position: "+ position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on_ViewDetails_Click(int position) {

        Toast.makeText(getActivity(), "See Item Details Click at Position: "+ position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on_ViewShop_Click(int position) {
        Item_Adapter chat_info = mylist.get(position);
        String shopName = chat_info.getItem_Owner();
        Intent intent = new Intent(getActivity(), FragmentsHolder.class);
        intent.putExtra("fragmentID","ViewShop");
        intent.putExtra("ShopName", shopName);
        startActivity(intent);
    }

    @Override
    public void on_AddToContact_Click(int position) {
        Item_Adapter chat_info = mylist.get(position);
        String userContact = chat_info.getOwner_Contact();
        String userName = chat_info.getProfile_Name();
        String userNotifid = chat_info.getOwner_Notification_Id();
        String userPhoto = chat_info.getProfilePhoto();
        String AddedDate = date + " - " + time;
        if (!Objects.equals(mycontact, userContact)) {

            Contact_Adapter contact_adapter = new Contact_Adapter(userName, userPhoto, userContact, mycontact, AddedDate, userNotifid);
            customers.document(userName).set(contact_adapter);
        }else {
            Toast.makeText(getActivity(), "You Are The Owner Of This Item", Toast.LENGTH_SHORT).show();
        }
    }
    private void FindDateTime() {

        Calendar timestamp = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeformat = new SimpleDateFormat("HH : mm : ss");
        time = timeformat.format(timestamp.getTime());
        date = DateFormat.getDateInstance(DateFormat.FULL).format(timestamp.getTime());
    }

}
