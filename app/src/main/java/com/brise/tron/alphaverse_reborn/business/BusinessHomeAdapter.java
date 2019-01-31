package com.brise.tron.alphaverse_reborn.business;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.brise.tron.alphaverse_reborn.R;

import java.util.List;

public class BusinessHomeAdapter extends RecyclerView.Adapter<BusinessHomeAdapter.ViewHolder> {
    private List<Business_Adapter> business_adapterList;
    private Context context;
    private menuSetup mlistener;

    public BusinessHomeAdapter(List<Business_Adapter> business_adapterList, Context context) {
        this.business_adapterList = business_adapterList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View businessRecycler = LayoutInflater.from(parent.getContext()).inflate(R.layout.businessholder,parent,false);
        return new ViewHolder(businessRecycler);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business_Adapter mybusiness = business_adapterList.get(position);
        holder.name.setText(String.format("Establishment Name: %s", mybusiness.getShop_Name()));
        holder.owner.setText(String.format("Establishment Owner: %s", mybusiness.getShop_Owner()));
        holder.type.setText(String.format("Establishment Type: %s", mybusiness.getShop_Type()));
        holder.location.setText(String.format("Establishment Location: %s", mybusiness.getShop_Location()));
         
    }

    @Override
    public int getItemCount() {
        return business_adapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView name,owner,type,location;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.business_name);
            owner = (TextView)itemView.findViewById(R.id.business_owner);
            type = (TextView)itemView.findViewById(R.id.business_type);
            location = (TextView)itemView.findViewById(R.id.business_location);
            ratingBar = (RatingBar)itemView.findViewById(R.id.business_rating);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mlistener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    mlistener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.setHeaderTitle("Choose an action");
            MenuItem update = contextMenu.add(Menu.NONE,1,1,"Edit Establishment");
            MenuItem add = contextMenu.add(Menu.NONE,2,2,"Add Items");
            MenuItem delete = contextMenu.add(Menu.NONE,3,3,"Delete Establishment");
            update.setOnMenuItemClickListener(this);
            add.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if (mlistener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    int id = menuItem.getItemId();
                    if (id == 1) {
                        mlistener.onUpdateClick(position);
                    }else if (id == 2) {
                        mlistener.onAddClick(position);
                    }else if (id == 3) {
                        mlistener.onDeleteClick(position);
                    }
                }
            }
            return false;
        }
    }
    public interface menuSetup
    {
        void onItemClick(int position);
        void onUpdateClick(int position);
        void onAddClick(int position);
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(menuSetup listener)
    {
     mlistener = listener;
    }
}
