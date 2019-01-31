package com.brise.tron.alphaverse_reborn.userprofile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.items.ItemHome;
import com.brise.tron.alphaverse_reborn.items.Item_Adapter;
import com.brise.tron.alphaverse_reborn.items.ItemsRecycler_Adapter;
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
public class CustomerList extends Fragment implements Contact_Recycler_Adapter.OnItemClickListener {
    private static final int CALL_REQUEST = 1;
    String contactName,hotStatus,myNumber;
    private Contact_Recycler_Adapter adapter;
    private List<Contact_Adapter> mylist;
    CollectionReference contactsLocation;
    private RecyclerView listView;

    public CustomerList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contacts = inflater.inflate(R.layout.fragment_contacts, container, false);
        listView = (RecyclerView) contacts.findViewById(R.id.Contact_list);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myNumber = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        contactsLocation = FirebaseFirestore.getInstance().collection("Customers");

        mylist = new ArrayList<>();
        adapter = new Contact_Recycler_Adapter(mylist,getActivity());
        listView.setAdapter(adapter);

        contactsLocation.whereEqualTo("customerOwner",myNumber).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (!Objects.requireNonNull(queryDocumentSnapshots).isEmpty())
                {
                    mylist.clear();
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot documentSnapshot: list)
                    {
                        Contact_Adapter contact_adapter = documentSnapshot.toObject(Contact_Adapter.class);
                        Contact_Adapter contacts = new Contact_Adapter();
                        if (contact_adapter!= null)
                        {
                            String Cname = contact_adapter.getCustomerName();
                            String Cowner = contact_adapter.getCustomerOwner();
                            String Cnumber = contact_adapter.getCustomerContact();
                            String Cphoto = contact_adapter.getCustomerPhoto();
                            String Cdate = contact_adapter.getDateAdded();
                            String Cnotifid = contact_adapter.getNotificationId();


                            contacts.setCustomerName(Cname);
                            contacts.setCustomerOwner(Cowner);
                            contacts.setCustomerContact(Cnumber);
                            contacts.setCustomerPhoto(Cphoto);
                            contacts.setDateAdded(Cdate);
                            contacts.setNotificationId(Cnotifid);
                        }
                        mylist.add(contact_adapter);
                    }
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(CustomerList.this);

                }
            }
        });
        return contacts;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void on_EDIT_Click(int position) {

    }

    @Override
    public void on_CHAT_Click(int position) {

    }

    @Override
    public void on_CALL_Click(int position) {

    }

    @Override
    public void on_DELETE_Click(int position) {

    }
}
