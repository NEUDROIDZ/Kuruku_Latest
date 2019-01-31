package com.brise.tron.alphaverse_reborn.items;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemHome extends Fragment implements ItemsRecycler_Adapter.OnItemClickListener {
    private static final int CALL_REQUEST = 1;
    String shopname,hotStatus,mycontact;
    private ItemsRecycler_Adapter adapter;
    private List<Item_Adapter> mylist;
    CollectionReference itemsLocation;


    public ItemHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ItemHome = inflater.inflate(R.layout.fragment_item_home, container, false);
        RecyclerView allItems = ItemHome.findViewById(R.id.my_Items);
        Button additem = ItemHome.findViewById(R.id.addItem);
        TextView shopName = ItemHome.findViewById(R.id.itemhome);
        allItems.setHasFixedSize(true);
        allItems.setLayoutManager(new LinearLayoutManager(getActivity()));

        mylist = new ArrayList<>();
        adapter = new ItemsRecycler_Adapter(mylist,getActivity());
        allItems.setAdapter(adapter);
        mycontact = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();

        itemsLocation = FirebaseFirestore.getInstance().collection("Items");
        Bundle getName = getArguments();
        if (getName != null) {
            shopname = getName.getString("estabname");

        }
        shopName.setText(String.format("Items in %s", shopname));

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle neo = new Bundle();
                neo.putString("INname",shopname);
                FragmentTransaction FMT = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                Item_Info tron = new Item_Info();
                tron.setArguments(neo);
                FMT.replace(R.id.pageHolder, tron);
                FMT.commit();

            }
        });
        itemsLocation.whereEqualTo("item_Owner",shopname).addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                            String initname = item_adapter.getOrig_Name();
                            String hmstate = item_adapter.getHotMarket_Status();


                            items.setItem_Name(iname);
                            items.setItem_Owner(iowner);
                            items.setItem_Price(iprice);
                            items.setItem_Comment(icomment);
                            items.setItem_Reference(iphoto);
                            items.setOwner_Contact(iocontact);
                            items.setOwner_Notification_Id(ionotifid);
                            items.setProfilePhoto(iouser);
                            items.setOrig_Name(initname);
                            items.setHotMarket_Status(hmstate);
                        }
                        mylist.add(item_adapter);
                    }
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(ItemHome.this);

                }
            }
        });

        return ItemHome;
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), "Normal Click at Position: "+ position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on_EDIT_Click(int position) {
        Item_Adapter chat_info = mylist.get(position);
        String price = chat_info.getItem_Price();
        String itemphoto = chat_info.getItem_Reference();
        String itemName = chat_info.getItem_Name();
        String itemOwner = chat_info.getItem_Owner();
        String comment = chat_info.getItem_Comment();
        String Iname = chat_info.getOrig_Name();

            Bundle tranx = new Bundle();

            tranx.putString("price", price);
            tranx.putString("item", itemName);
            tranx.putString("INname",itemOwner);
            tranx.putString("comment",comment);
            tranx.putString("photo",itemphoto);
            tranx.putString("initialN",Iname);

            FragmentTransaction chat_detail = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            Item_Info brise = new Item_Info();
            brise.setArguments(tranx);
            chat_detail.replace(R.id.pageHolder, brise);
            chat_detail.commit();

    }

    @Override
    public void on_HOTMARK_Click(int position) {
        Item_Adapter chat_info = mylist.get(position);
        String price = chat_info.getItem_Price();
        String itemphoto = chat_info.getItem_Reference();
        String itemName = chat_info.getItem_Name();
        String itemOwner = chat_info.getItem_Owner();
        String comment = chat_info.getItem_Comment();
        String Iname = chat_info.getOrig_Name();
        String notif = chat_info.getOwner_Notification_Id();
        String hotmark = "Positive";
        String ocontact = chat_info.getOwner_Contact();
        String ophoto = chat_info.getProfilePhoto();
        String oname = chat_info.getProfile_Name();

        Item_Adapter item_adapter = new Item_Adapter(itemName,itemOwner,price,comment,itemphoto,ocontact,notif,ophoto,Iname,hotmark,oname);
        itemsLocation.document(Iname).set(item_adapter);


    }

    @Override
    public void on_DELETE_Click(int position) {
        Item_Adapter chat_info = mylist.get(position);
        final String Iname = chat_info.getOrig_Name();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("DELETE ITEM ?");
        dialog.setMessage("Deletion is Permanent Do you wish to continue?");
        dialog.setPositiveButton("Yes Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemsLocation.document(Iname).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Item Deleted", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        dialog.setNegativeButton("No Abort", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final  AlertDialog register = dialog.create();
        register.show();

    }

}
