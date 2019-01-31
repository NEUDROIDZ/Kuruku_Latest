package com.brise.tron.alphaverse_reborn.messaging;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brise.tron.alphaverse_reborn.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

public class Chat_Adapter extends RecyclerView.Adapter<Chat_Adapter.Chat_List> {
    private List<Chat_Detail_Adapter_sent> chatlist;
    private Context context;
    private String myname;
    private OnItemClickListener clickListener;


    public Chat_Adapter(List<Chat_Detail_Adapter_sent> chatlist, Context context) {
        this.chatlist = chatlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Chat_List onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mychat = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_chat,parent,false);
        myname = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        return new Chat_List(mychat);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull Chat_List hold, int pos) {



        Chat_Detail_Adapter_sent Clayout = chatlist.get(pos);
        String otherUser = Clayout.getCheck_Contact();
        if (!Objects.equals(myname, otherUser))
        {
            hold.myLayout.setVisibility(View.VISIBLE);
            hold.myLayout1.setVisibility(View.GONE);
            hold.Sname.setText(String.format("Chat To: %s", Clayout.getOtherID()));
            hold.Rcontent.setText(Clayout.getMessage_Content());
            hold.Rtime.setText(Clayout.getChat_Time_Stamp());
            hold.Stopic.setText(String.format("Lets Chat about this %s", Clayout.getItem_Name()));
            Glide.with(context).load(Clayout.getPhoto_Id()).into(hold.Rimage);

        }else {
            hold.myLayout.setVisibility(View.GONE);
            hold.myLayout1.setVisibility(View.VISIBLE);
            hold.Sname1.setText(String.format("Chat From: %s", Clayout.getSender_Id()));
            hold.Rcontent1.setText(Clayout.getMessage_Content());
            hold.Rtime1.setText(Clayout.getChat_Time_Stamp());
            hold.Stopic1.setText(String.format("Lets Chat about this %s", Clayout.getItem_Name()));
            Glide.with(context).load(Clayout.getPhoto_Id()).into(hold.Rimage1);
        }


    }

    @Override
    public int getItemCount() {
        return chatlist.size();
    }

    public class Chat_List extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener {
        private TextView Sname,Stopic,Rcontent,Rtime,Sname1,Stopic1,Rcontent1,Rtime1;
        private ImageView Rimage,Rimage1;
        private LinearLayout myLayout,myLayout1;
        public Chat_List(@NonNull View itemView) {
            super(itemView);

            Sname = itemView.findViewById(R.id.sender_name);
            Stopic = itemView.findViewById(R.id.chat_topic);
            Rcontent = itemView.findViewById(R.id.received_text);
            Rtime = itemView.findViewById(R.id.received_time);
            Rimage = itemView.findViewById(R.id.received_image);
            myLayout = itemView.findViewById(R.id.Chat_Container);

            Sname1 = itemView.findViewById(R.id.sender_name1);
            Stopic1 = itemView.findViewById(R.id.chat_topic1);
            Rcontent1 = itemView.findViewById(R.id.received_text1);
            Rtime1 = itemView.findViewById(R.id.received_time1);
            Rimage1 = itemView.findViewById(R.id.received_image1);
            myLayout1 = itemView.findViewById(R.id.Chat_Container1);


            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (clickListener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    int id = item.getItemId();

                    if (id == 1)
                    {
                        clickListener.on_Delete_Click(position);
                    } else if (id == 2)
                    {
                        clickListener.on_Forward_Click(position);
                    }
                }
            }
            return false;
        }

        @Override
        public void onClick(View v) {

            if (clickListener != null)
            {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION)
                {
                    clickListener.onItemClick(position);
                }
            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Perform Action On the chat");
            MenuItem Delete = menu.add(Menu.NONE,1,1,"DELETE");
            MenuItem Forward = menu.add(Menu.NONE,2,2,"FORWARD");


            Delete.setOnMenuItemClickListener(this);
            Forward.setOnMenuItemClickListener(this);

        }
    }

    public  interface  OnItemClickListener {
        void onItemClick(int position);
        void on_Delete_Click(int position);
        void on_Forward_Click(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        clickListener = listener;
    }
}
