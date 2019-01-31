package com.brise.tron.alphaverse_reborn.business;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.items.Item_Info;
import com.brise.tron.alphaverse_reborn.userprofile.User_Adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class Business_Info extends Fragment {
    public static final String username = "";
    EditText sname,loction,shopreference;
    TextView dispaly;
    Button saveShop;
    Spinner type;
    DatabaseReference Bprofile;
    FirebaseFirestore dB;
    CollectionReference establishment;
    String owner,ownername;


    public Business_Info() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View miracle = inflater.inflate(R.layout.fragment_business__info, container, false);
        sname = (EditText)miracle.findViewById(R.id.businessname);
        loction = (EditText)miracle.findViewById(R.id.businessLocation);
        shopreference = (EditText)miracle.findViewById(R.id.Breference);
        saveShop = (Button)miracle.findViewById(R.id.saveshop);
        type = (Spinner)miracle.findViewById(R.id.shoptype);
        dispaly = (TextView)miracle.findViewById(R.id.Htitle);
        dB = FirebaseFirestore.getInstance();
        owner = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        Bprofile = FirebaseDatabase.getInstance().getReference().child("Users").child(owner).child("Business Profile");
        establishment = FirebaseFirestore.getInstance().collection("Users").document(owner).collection("Business Profile");

        SharedPreferences status = Objects.requireNonNull(getActivity()).getSharedPreferences(username, Context.MODE_PRIVATE);
        ownername = status.getString("userName",null);

        Bundle shopUpdate = getArguments();
        if (shopUpdate != null)
        {
            String name = shopUpdate.getString("Nname");

            if (name != null)
            {
                sname.setText(name);
                loction.setText(shopUpdate.getString("Lname"));
                shopreference.setText(shopUpdate.getString("Rname"));
                dispaly.setTextSize(16);
                dispaly.setText(String.format("Editing  %s", name));
            }
        }
        saveShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveShop();
            }
        });

        return miracle;
    }

    private void SaveShop() {
        String Shop_Name,Shop_Owner,Shop_Location,Shop_Reference,Creation_Date,Shop_Type;

        Shop_Name = sname.getText().toString().trim();
        Shop_Owner = ownername;
        Shop_Reference = shopreference.getText().toString().trim();
        Shop_Location = loction.getText().toString().trim();
        Calendar calendar = Calendar.getInstance();
        Creation_Date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        Shop_Type = type.getSelectedItem().toString();

        if (!TextUtils.isEmpty(Shop_Name))
        {
            if (!TextUtils.isEmpty(Shop_Location))
            {
                if (!TextUtils.isEmpty(Shop_Reference))
                {
                    Business_Adapter business_adapter = new Business_Adapter(Shop_Name,Shop_Owner,Shop_Location,Shop_Reference,Creation_Date,Shop_Type);
                    establishment.document(Shop_Name).set(business_adapter);
                    sname.setText("");
                    loction.setText("");
                    shopreference.setText("");
                    Toast.makeText(getActivity(), "Business Successfully Created", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "Please make sure all fields contain requested info", Toast.LENGTH_LONG).show();
                }

            }
            else {
                Toast.makeText(getActivity(), "Please make sure all fields contain requested info", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Please make sure all fields contain requested info", Toast.LENGTH_LONG).show();
        }




    }

}
