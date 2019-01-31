package com.brise.tron.alphaverse_reborn.items;

public class Item_Adapter {
    private  String Item_Name,Item_Owner,Item_Price,Item_Comment,Item_Reference,Owner_Contact,Owner_Notification_Id,ProfilePhoto,Orig_Name,HotMarket_Status,Profile_Name;
    public Item_Adapter() {
    }

    public Item_Adapter(String item_Name, String item_Owner, String item_Price, String item_Comment, String item_Reference, String owner_Contact,String owner_Notification_Id,String profilePhoto,String orig_Name,String hotMarket_Status,String profile_Name) {
        Item_Name = item_Name;
        Item_Owner = item_Owner;
        Item_Price = item_Price;
        Item_Comment = item_Comment;
        Item_Reference = item_Reference;
        Owner_Contact = owner_Contact;
        Owner_Notification_Id = owner_Notification_Id;
        ProfilePhoto = profilePhoto;
        Orig_Name = orig_Name;
        HotMarket_Status = hotMarket_Status;
        Profile_Name = profile_Name;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getItem_Owner() {
        return Item_Owner;
    }

    public void setItem_Owner(String item_Owner) {
        Item_Owner = item_Owner;
    }

    public String getItem_Price() {
        return Item_Price;
    }

    public void setItem_Price(String item_Price) {
        Item_Price = item_Price;
    }

    public String getItem_Comment() {
        return Item_Comment;
    }

    public void setItem_Comment(String item_Comment) {
        Item_Comment = item_Comment;
    }

    public String getItem_Reference() {
        return Item_Reference;
    }

    public void setItem_Reference(String item_Reference) {
        Item_Reference = item_Reference;
    }

    public String getOwner_Contact() {
        return Owner_Contact;
    }

    public void setOwner_Contact(String owner_Contact) {
        Owner_Contact = owner_Contact;
    }

    public String getOwner_Notification_Id() {
        return Owner_Notification_Id;
    }

    public void setOwner_Notification_Id(String owner_Notification_Id) {
        Owner_Notification_Id = owner_Notification_Id;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getOrig_Name() {
        return Orig_Name;
    }

    public void setOrig_Name(String orig_Name) {
        Orig_Name = orig_Name;
    }

    public String getHotMarket_Status() {
        return HotMarket_Status;
    }

    public void setHotMarket_Status(String hotMarket_Status) {
        HotMarket_Status = hotMarket_Status;
    }

    public String getProfile_Name() {
        return Profile_Name;
    }

    public void setProfile_Name(String profile_Name) {
        Profile_Name = profile_Name;
    }
}
