package com.brise.tron.alphaverse_reborn.userprofile;

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
import android.widget.TextView;

import com.brise.tron.alphaverse_reborn.R;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Contact_Recycler_Adapter extends RecyclerView.Adapter<Contact_Recycler_Adapter.ViewHolder> {
    private List<Contact_Adapter> contact_adapterList;
    private Context context;
    private Contact_Recycler_Adapter.OnItemClickListener contactListener;
    private int pos;


    public Contact_Recycler_Adapter(List<Contact_Adapter> mylist, FragmentActivity activity) {
        this.contact_adapterList = mylist;
        this.context = activity;
    }

    @NonNull
    @Override
    public Contact_Recycler_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtpe) {
        View K_Pascy = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_holder,parent,false);
        return new Contact_Recycler_Adapter.ViewHolder(K_Pascy);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact_Adapter myItems = contact_adapterList.get(position);
        pos = holder.getAdapterPosition();
        holder.contactName.setText(myItems.getCustomerName());
        holder.contactNumber.setText(myItems.getCustomerContact());
        Glide.with(context).load(myItems.getCustomerPhoto()).into(holder.contactPhoto);
    }

    @Override
    public int getItemCount() {
        return contact_adapterList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener
    {
        TextView contactName,contactNumber;
        CircleImageView contactPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            contactName = (TextView) itemView.findViewById(R.id.Contact_Name);
            contactNumber = (TextView) itemView.findViewById(R.id.Contact_Number);
            contactPhoto = (CircleImageView)itemView.findViewById(R.id.Contact_Photo);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if (contactListener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    contactListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Contact_Adapter mycontacts = contact_adapterList.get(pos);
            String name = mycontacts.getCustomerName();
            menu.setHeaderTitle("Perform Action On the Contact");
            MenuItem EDIT = menu.add(Menu.NONE,1,1,"EDIT " + name);
            MenuItem CHAT = menu.add(Menu.NONE,2,2,"CHAT WITH "+ name);
            MenuItem CALL = menu.add(Menu.NONE,3,3,"CALL "+ name);
            MenuItem DELETE = menu.add(Menu.NONE,4,4,"DELETE "+ name);

            EDIT.setOnMenuItemClickListener(this);
            CHAT.setOnMenuItemClickListener(this);
            CALL.setOnMenuItemClickListener(this);
            DELETE.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (contactListener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    int id = item.getItemId();

                    if (id == 1)
                    {
                        contactListener.on_EDIT_Click(position);
                    } else if (id == 2)
                    {
                        contactListener.on_CHAT_Click(position);
                    }else if (id == 3)
                    {
                        contactListener.on_CALL_Click(position);
                    }else if (id == 4)
                    {
                        contactListener.on_DELETE_Click(position);
                    }
                }
            }
            return false;
        }
    }

    public  interface  OnItemClickListener {
        void onItemClick(int position);
        void on_EDIT_Click(int position);
        void on_CHAT_Click(int position);
        void on_CALL_Click(int position);
        void on_DELETE_Click(int position);
    }
    public void setOnItemClickListener(Contact_Recycler_Adapter.OnItemClickListener listener)
    {
        contactListener = listener;
    }

}
