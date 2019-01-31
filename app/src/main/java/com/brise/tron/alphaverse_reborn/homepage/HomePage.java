package com.brise.tron.alphaverse_reborn.homepage;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.business.Business_Info;
import com.brise.tron.alphaverse_reborn.items.ItemHome;
import com.brise.tron.alphaverse_reborn.items.Item_Adapter;
import com.brise.tron.alphaverse_reborn.messaging.Chat_Detail;
import com.brise.tron.alphaverse_reborn.userprofile.Contact_Adapter;
import com.brise.tron.alphaverse_reborn.userprofile.UserHome;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage extends Fragment implements Recycler_Adapter.OnItemClickListener{
    private static final int CALL_REQUEST = 1;
    public static final String USER_STATE = "MY_PREVIOUS_STATE";
    EditText topic;
    Button launch;
    CollectionReference customers;
    String item_owner_contact,mycontact,time,date;
    private Recycler_Adapter adapter;
    private List<Item_Adapter> mylist;


    public HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Home = inflater.inflate(R.layout.fragment_home_page, container, false);
        CollectionReference itemsLocation = FirebaseFirestore.getInstance().collection("Items");
        customers = FirebaseFirestore.getInstance().collection("Customers");

        topic = (EditText)Home.findViewById(R.id.searchTopic);
        launch = (Button)Home.findViewById(R.id.find);
        RecyclerView recyclerView = (RecyclerView) Home.findViewById(R.id.ItemList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FindDateTime();

        mylist = new ArrayList<>();
        adapter = new Recycler_Adapter(mylist,getActivity());
        recyclerView.setAdapter(adapter);
        mycontact = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        
        itemsLocation.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty())
                {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    mylist.clear();
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
                    adapter.setOnItemClickListener(HomePage.this);

                }
            }
        });

        return Home;
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
            Bundle tranx = new Bundle();
            tranx.putString("ownerContact",ownercontact);
            tranx.putString("itemPhoto", itemphoto);
            tranx.putString("itemName", itemName);
            tranx.putString("owner",itemOwner);
            tranx.putString("ownerNotifId",ownerNotifid);
            tranx.putString("ownerPhoto",userPhoto );
            tranx.putString("ownerName",profileName);

            FragmentTransaction chat_detail = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            Chat_Detail brise = new Chat_Detail();
            brise.setArguments(tranx);
            chat_detail.replace(R.id.pageHolder, brise);
            chat_detail.commit();

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
        Bundle neo = new Bundle();
        neo.putString("estabname", shopName);
        FragmentTransaction FMT = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ItemHome tron = new ItemHome();
        tron.setArguments(neo);
        FMT.replace(R.id.pageHolder, tron);
        FMT.commit();
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
