package com.brise.tron.alphaverse_reborn.homepage;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.brise.tron.alphaverse_reborn.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Zerox extends Fragment {
    EditText querry;
    Spinner filter;
    Button execute;


    public Zerox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View zerox = inflater.inflate(R.layout.fragment_zerox, container, false);
       querry = (EditText)zerox.findViewById(R.id.searchTopichome);
       filter = (Spinner)zerox.findViewById(R.id.spinnerhome);
       execute = (Button) zerox.findViewById(R.id.findhome);
       execute.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent zerox = new Intent(getActivity(), FragmentsHolder.class);
               zerox.putExtra("fragmentID","Zerox");
               startActivity(zerox);
           }
       });
       return zerox;
    }

}
