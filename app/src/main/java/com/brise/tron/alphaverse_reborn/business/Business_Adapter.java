package com.brise.tron.alphaverse_reborn.business;

public class Business_Adapter {
    private String Shop_Name,Shop_Owner,Shop_Location,Shop_Reference,Creation_Date,Shop_Type;

    public Business_Adapter() {
    }

    public Business_Adapter(String shop_Name, String shop_Owner, String shop_Location, String Shop_Reference, String creation_Date,String shop_Type) {
        this.Shop_Name = shop_Name;
        this.Shop_Owner = shop_Owner;
        this.Shop_Location = shop_Location;
        this.Shop_Reference = Shop_Reference;
        this.Creation_Date = creation_Date;
        this.Shop_Type = shop_Type;
    }


    public String getShop_Name() {
        return Shop_Name;
    }

    public String getShop_Owner() {
        return Shop_Owner;
    }

    public String getShop_Location() {
        return Shop_Location;
    }

    public String getShop_Reference() {
        return Shop_Reference;
    }

    public String getCreation_Date() {
        return Creation_Date;
    }

    public String getShop_Type() {
        return Shop_Type;
    }

    public void setShop_Name(String shop_Name) {
        Shop_Name = shop_Name;
    }

    public void setShop_Owner(String shop_Owner) {
        Shop_Owner = shop_Owner;
    }

    public void setShop_Location(String shop_Location) {
        Shop_Location = shop_Location;
    }

    public void setShop_Type(String shop_Type) {
        Shop_Type = shop_Type;
    }

}
