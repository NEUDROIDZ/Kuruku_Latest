package com.brise.tron.alphaverse_reborn.items;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brise.tron.alphaverse_reborn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Item_Detail extends Fragment {


    public Item_Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View chaji = inflater.inflate(R.layout.fragment_item__detail, container, false);


        return chaji;
    }

}
