package com.brise.tron.alphaverse_reborn.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.business.Business_Info;
import com.brise.tron.alphaverse_reborn.items.ItemHome;
import com.brise.tron.alphaverse_reborn.items.Item_Info;
import com.brise.tron.alphaverse_reborn.messaging.Chat_Detail;
import com.brise.tron.alphaverse_reborn.userprofile.UserHome;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentsHolder extends AppCompatActivity {
    CircleImageView Pphoto;
    TextView Pname;
    LinearLayout holdpager,statusbar;
    String destination, shopName, shpn, shpl, shpr, hotmarket, item_Owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_holder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        setSupportActionBar(toolbar);
        holdpager = (LinearLayout) findViewById(R.id.pageHolder);
        statusbar = (LinearLayout)findViewById(R.id.profileHolder);
        Pphoto = (CircleImageView)findViewById(R.id.Profilphoto);
        Pname = (TextView)findViewById(R.id.profilename);
        statusbar.setVisibility(View.GONE);

        Intent direction = getIntent();
        destination = direction.getStringExtra("fragmentID");
        shopName = direction.getStringExtra("ShopName");
        hotmarket = direction.getStringExtra("hotmarket");
        item_Owner = direction.getStringExtra("hustler");
        shpn = direction.getStringExtra("name");
        shpl = direction.getStringExtra("location");
        shpr = direction.getStringExtra("erence");
        String itemName = direction.getStringExtra("itemName");
        String itemOwner = direction.getStringExtra("owner");
        String itemPhoto = direction.getStringExtra("itemPhoto");
        String ownerContact = direction.getStringExtra("ownerContact");
        String ownerNotifId = direction.getStringExtra("ownerNotifId");
        String ownerName = direction.getStringExtra("ownerName");
        String ownerPhoto = direction.getStringExtra("ownerPhoto");

        switch (destination) {
            case "ItemHome": {
                Bundle neo = new Bundle();
                neo.putString("INname", shopName);
                neo.putString("hotmark", hotmarket);
                neo.putString("owner", item_Owner);
                FragmentTransaction FMT = getSupportFragmentManager().beginTransaction();
                Item_Info tron = new Item_Info();
                tron.setArguments(neo);
                FMT.replace(R.id.pageHolder, tron);
                FMT.commit();
                break;
            }
            case "BusinessHome": {
                Bundle neo = new Bundle();
                neo.putString("Nname", shpn);
                neo.putString("Lname", shpl);
                neo.putString("Rname", shpr);
                FragmentTransaction FMT = getSupportFragmentManager().beginTransaction();
                Business_Info tron = new Business_Info();
                tron.setArguments(neo);
                FMT.replace(R.id.pageHolder, tron);
                FMT.commit();
                break;
            }
            case "BusinessAdd": {
                FragmentTransaction FMT = getSupportFragmentManager().beginTransaction();
                Business_Info tron = new Business_Info();
                FMT.replace(R.id.pageHolder, tron);
                FMT.commit();
                break;
            }
            case "NoUser": {
                FragmentTransaction FMT = getSupportFragmentManager().beginTransaction();
                FMT.replace(R.id.pageHolder, new UserHome());
                FMT.commit();
                break;
            }
            case "ShopHome": {
                Bundle neo = new Bundle();
                neo.putString("estabname", shopName);
                FragmentTransaction FMT = getSupportFragmentManager().beginTransaction();
                ItemHome tron = new ItemHome();
                tron.setArguments(neo);
                FMT.replace(R.id.pageHolder, tron);
                FMT.commit();
                break;
            }
            case "Zerox": {
                FragmentTransaction FMT = getSupportFragmentManager().beginTransaction();
                FMT.replace(R.id.pageHolder, new HomePage());
                FMT.commit();
                break;
            }
            case "ChatView": {
                Bundle tranx = new Bundle();
                tranx.putString("ownerContact",ownerContact);
                tranx.putString("itemPhoto", itemPhoto);
                tranx.putString("itemName", itemName);
                tranx.putString("owner",itemOwner);
                tranx.putString("ownerNotifId",ownerNotifId);
                tranx.putString("ownerPhoto",ownerPhoto );
                tranx.putString("ownerName",ownerName);

                FragmentTransaction chat_detail = getSupportFragmentManager().beginTransaction();
                Chat_Detail brise = new Chat_Detail();
                brise.setArguments(tranx);
                chat_detail.replace(R.id.pageHolder, brise);
                chat_detail.commit();
                break;
            } case "ViewShop": {
                Bundle neo = new Bundle();
                neo.putString("estabname", shopName);
                FragmentTransaction FMT = getSupportFragmentManager().beginTransaction();
                ItemHome tron = new ItemHome();
                tron.setArguments(neo);
                FMT.replace(R.id.pageHolder, tron);
                FMT.commit();
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
