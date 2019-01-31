package com.brise.tron.alphaverse_reborn.business;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.homepage.FragmentsHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
public class BusinessHome extends Fragment implements BusinessHomeAdapter.menuSetup {
    public static final String prefered = "";
    String username,owner;
    private BusinessHomeAdapter adapter;
    private List<Business_Adapter> mybusinessList;
    CollectionReference establishment,itemsLocation;

    public BusinessHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View businessHome = inflater.inflate(R.layout.fragment_business_home, container, false);
        RecyclerView business = businessHome.findViewById(R.id.businesses);
        Button addBusiness = businessHome.findViewById(R.id.addBusiness);
        business.setHasFixedSize(true);
        business.setLayoutManager(new LinearLayoutManager(getActivity()));
        mybusinessList = new ArrayList<>();
        adapter = new BusinessHomeAdapter(mybusinessList,getActivity());
        business.setAdapter(adapter);
        owner = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        establishment = FirebaseFirestore.getInstance().collection("Users").document(owner).collection("Business Profile");
        itemsLocation = FirebaseFirestore.getInstance().collection("Items");
        SharedPreferences status = Objects.requireNonNull(getActivity()).getSharedPreferences(prefered, Context.MODE_PRIVATE);
        username = status.getString("userName",null);


        addBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username!= null)
                {
                    Intent busi = new Intent(getActivity(), FragmentsHolder.class);
                    busi.putExtra("fragmentID", "BusinessAdd");
                    startActivity(busi);
                }else
                {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("KURUKU REGISTRATION");
                    dialog.setMessage("To create an establishment you must provide KURUKU with some info about yourself. Do you wish to continue?");
                    dialog.setPositiveButton("yes continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent busi = new Intent(getActivity(), FragmentsHolder.class);
                            busi.putExtra("fragmentID", "NoUser");
                            startActivity(busi);
                        }
                    });
                    dialog.setNegativeButton("no not now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    final  AlertDialog register = dialog.create();
                    register.show();
                }


            }
        });

        establishment.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
             if (!Objects.requireNonNull(queryDocumentSnapshots).isEmpty())
             {
                 mybusinessList.clear();
                 List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                 for (DocumentSnapshot documentSnapshot: list)
                 {
                     Business_Adapter business_adapter = documentSnapshot.toObject(Business_Adapter.class);
                     Business_Adapter busineslist = new Business_Adapter();
                     if (business_adapter!= null)
                     {
                         String name = business_adapter.getShop_Name();
                         String owner = business_adapter.getShop_Owner();
                         String type = business_adapter.getShop_Type();
                         String location = business_adapter.getShop_Location();

                         busineslist.setShop_Name(name);
                         busineslist.setShop_Owner(owner);
                         busineslist.setShop_Type(type);
                         busineslist.setShop_Location(location);
                     }
                     mybusinessList.add(business_adapter);
                 }
                 adapter.notifyDataSetChanged();
                 adapter.setOnItemClickListener(BusinessHome.this);

             }
            }
        });


        return businessHome;
    }

    @Override
    public void onItemClick(int position) {
        Business_Adapter business_adapter = mybusinessList.get(position);
        String shopname = business_adapter.getShop_Name();
        Intent busi = new Intent(getActivity(), FragmentsHolder.class);
        busi.putExtra("fragmentID", "ShopHome");
        busi.putExtra("ShopName", shopname);
        startActivity(busi);
    }

    @Override
    public void onUpdateClick(int position) {
        Business_Adapter business_adapter = mybusinessList.get(position);
        String shopName, shopLocation,shopR;
        shopName = business_adapter.getShop_Name();
        shopLocation = business_adapter.getShop_Location();
        shopR = business_adapter.getShop_Reference();
        Intent busi = new Intent(getActivity(), FragmentsHolder.class);
        busi.putExtra("fragmentID", "BusinessHome");
        busi.putExtra("name", shopName);
        busi.putExtra("location", shopLocation);
        busi.putExtra("erence", shopR);
        startActivity(busi);
    }

    @Override
    public void onAddClick(int position) {
        Business_Adapter business_adapter = mybusinessList.get(position);
        String shopName = business_adapter.getShop_Name();

        Intent intent = new Intent(getActivity(), FragmentsHolder.class);
        intent.putExtra("fragmentID","ItemHome");
        intent.putExtra("name", shopName);
        startActivity(intent);

    }

    @Override
    public void onDeleteClick(int position) {
        Business_Adapter business_adapter = mybusinessList.get(position);
        final String Iname = business_adapter.getShop_Name();
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("DELETE ESTABLISHMENT ?");
        dialog.setMessage("Deletion is Permanent Do you wish to continue?");
        dialog.setPositiveButton("Yes Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                itemsLocation.whereEqualTo("item_Owner",Iname).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty())
                    {
                        Toast.makeText(getActivity(), "Sorry the Establishment Contains Items delete them first", Toast.LENGTH_LONG).show();
                    }
                    else{
                        establishment.document(Iname).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(getActivity(), "Establishment Deleted", Toast.LENGTH_LONG).show();
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
