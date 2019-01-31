package com.brise.tron.alphaverse_reborn.messaging;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brise.tron.alphaverse_reborn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat_Holder extends Fragment {
    RecyclerView chat_item_holder;


    public Chat_Holder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Favour = inflater.inflate(R.layout.fragment_messaging, container, false);
        chat_item_holder = (RecyclerView)Favour.findViewById(R.id.holder_chat_list);

        return Favour;
    }

}
