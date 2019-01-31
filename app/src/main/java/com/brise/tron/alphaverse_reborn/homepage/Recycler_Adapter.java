package com.brise.tron.alphaverse_reborn.homepage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.items.Item_Adapter;
import com.bumptech.glide.Glide;

import java.util.List;

public class Recycler_Adapter extends RecyclerView.Adapter<Recycler_Adapter.ViewHolder> {

   private List<Item_Adapter> mylist;
   private Context context;
   private OnItemClickListener mListener;


    public Recycler_Adapter(List<Item_Adapter> mylist, FragmentActivity activity) {
        this.mylist = mylist;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtpe) {
        View K_Pascy = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container,parent,false);
        return new ViewHolder(K_Pascy);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item_Adapter myItems = mylist.get(position);
        holder.itName.setText( myItems.getItem_Name());
        holder.itOwner.setText( myItems.getItem_Owner());
        holder.itPrice.setText( myItems.getItem_Price());
        holder.itcomment.setText( myItems.getItem_Comment());
        Glide.with(context).load(myItems.getItem_Reference()).into(holder.itphoto);

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener
    {
         TextView itName, itPrice, itOwner, itcomment;
         ImageView itphoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itName = (TextView) itemView.findViewById(R.id.name);
            itPrice = (TextView) itemView.findViewById(R.id.price);
            itOwner = (TextView) itemView.findViewById(R.id.owner);
            itcomment = (TextView) itemView.findViewById(R.id.coment);
            itphoto = (ImageView) itemView.findViewById(R.id.item_photo);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null)
            {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION)
            {
                mListener.onItemClick(position);
            }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Perform Action On the Item");
            MenuItem chat = menu.add(Menu.NONE,1,1,"Contact Seller via KURUKU Chat");
            MenuItem call = menu.add(Menu.NONE,2,2,"Contact Seller via Phone Call");
            MenuItem sms = menu.add(Menu.NONE,3,3,"Contact Seller via SMS");
            MenuItem viewdetails = menu.add(Menu.NONE,4,4,"View Item Details");
            MenuItem viewshop = menu.add(Menu.NONE,5,5,"View the Whole Shop");
            MenuItem addtocontacts = menu.add(Menu.NONE,6,6,"Add Seller To CustomerList");

            chat.setOnMenuItemClickListener(this);
            call.setOnMenuItemClickListener(this);
            sms.setOnMenuItemClickListener(this);
            viewdetails.setOnMenuItemClickListener(this);
            viewshop.setOnMenuItemClickListener(this);
            addtocontacts.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                  int id = item.getItemId();

                  if (id == 1)
                  {
                   mListener.on_chat_Click(position);
                  } else if (id == 2)
                  {
                      mListener.on_call_Click(position);
                  }else if (id == 3)
                  {
                      mListener.on_sms_Click(position);
                  }else if (id == 4)
                  {
                      mListener.on_ViewDetails_Click(position);
                  }else if (id == 5)
                  {
                      mListener.on_ViewShop_Click(position);
                  }else if (id == 6)
                  {
                      mListener.on_AddToContact_Click(position);
                  }
                }
            }
            return false;
        }
    }

    public  interface  OnItemClickListener {
        void onItemClick(int position);
        void on_chat_Click(int position);
        void on_call_Click(int position);
        void on_sms_Click(int position);
        void on_ViewDetails_Click(int position);
        void on_ViewShop_Click(int position);
        void on_AddToContact_Click(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
     mListener = listener;
    }

}
