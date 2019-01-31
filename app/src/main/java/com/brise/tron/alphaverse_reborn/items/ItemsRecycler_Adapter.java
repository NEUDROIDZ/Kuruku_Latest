package com.brise.tron.alphaverse_reborn.items;

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
import com.bumptech.glide.Glide;

import java.util.List;

public class ItemsRecycler_Adapter extends RecyclerView.Adapter<ItemsRecycler_Adapter.ViewHolder> {

   private List<Item_Adapter> mylist;
   private Context context;
   private OnItemClickListener mListener;


    public ItemsRecycler_Adapter(List<Item_Adapter> mylist, FragmentActivity activity) {
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
        holder.itName.setText(myItems.getItem_Name());
        holder.itOwner.setText( myItems.getItem_Owner());
        holder.itPrice.setText(myItems.getItem_Price());
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
            MenuItem EDIT = menu.add(Menu.NONE,1,1,"EDIT ITEM");
            MenuItem HOTMARK = menu.add(Menu.NONE,2,2,"ADD TO HOT MARKET");
            MenuItem DELETE = menu.add(Menu.NONE,3,3,"DELETE ITEM");

            EDIT.setOnMenuItemClickListener(this);
            HOTMARK.setOnMenuItemClickListener(this);
            DELETE.setOnMenuItemClickListener(this);
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
                   mListener.on_EDIT_Click(position);
                  } else if (id == 2)
                  {
                      mListener.on_HOTMARK_Click(position);
                  }else if (id == 3)
                  {
                      mListener.on_DELETE_Click(position);
                  }
                }
            }
            return false;
        }
    }

    public  interface  OnItemClickListener {
        void onItemClick(int position);
        void on_EDIT_Click(int position);
        void on_HOTMARK_Click(int position);
        void on_DELETE_Click(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
     mListener = listener;
    }

}
