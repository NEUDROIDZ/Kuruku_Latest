package com.brise.tron.alphaverse_reborn.messaging;

public class Chat_Detail_Adapter_sent {

    private String Sender_Id,Item_Name,Message_Content,Chat_Time_Stamp,Photo_Id,Check_Contact,Admin,OtherID;


    public Chat_Detail_Adapter_sent() {
    }

    public Chat_Detail_Adapter_sent(String sender_Id, String item_Name, String message_Content, String chat_Time_Stamp, String photo_Id , String check_Contact,String admin,String otherID) {
        Sender_Id = sender_Id;
        Item_Name = item_Name;
        Message_Content = message_Content;
        Chat_Time_Stamp = chat_Time_Stamp;
        Photo_Id = photo_Id;
        Check_Contact = check_Contact;
        Admin = admin;
        OtherID = otherID;
    }

    public String getSender_Id() {
        return Sender_Id;
    }

    public void setSender_Id(String sender_Id) {
        Sender_Id = sender_Id;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getMessage_Content() {
        return Message_Content;
    }

    public void setMessage_Content(String message_Content) {
        Message_Content = message_Content;
    }

    public String getChat_Time_Stamp() {
        return Chat_Time_Stamp;
    }

    public void setChat_Time_Stamp(String chat_Time_Stamp) {
        Chat_Time_Stamp = chat_Time_Stamp;
    }

    public String getPhoto_Id() {
        return Photo_Id;
    }

    public void setPhoto_Id(String photo_Id) {
        Photo_Id = photo_Id;

    }

    public String getCheck_Contact() {
        return Check_Contact;
    }

    public void setCheck_Contact(String check_Contact) {
        Check_Contact = check_Contact;
    }

    public String getAdmin() {
        return Admin;
    }

    public void setAdmin(String admin) {
        Admin = admin;
    }

    public String getOtherID() {
        return OtherID;
    }

    public void setOtherID(String otherID) {
        OtherID = otherID;
    }
}
