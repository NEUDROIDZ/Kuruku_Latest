package com.brise.tron.alphaverse_reborn.userprofile;

public class Contact_Adapter {
    private String CustomerName,CustomerPhoto,CustomerContact,CustomerOwner,DateAdded,NotificationId;

    public Contact_Adapter() {
    }

    public Contact_Adapter(String customerName, String customerPhoto, String customerContact, String customerOwner, String dateAdded,String notificationId) {
        CustomerName = customerName;
        CustomerPhoto = customerPhoto;
        CustomerContact = customerContact;
        CustomerOwner = customerOwner;
        DateAdded = dateAdded;
        NotificationId = notificationId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerPhoto() {
        return CustomerPhoto;
    }

    public void setCustomerPhoto(String customerPhoto) {
        CustomerPhoto = customerPhoto;
    }

    public String getCustomerContact() {
        return CustomerContact;
    }

    public void setCustomerContact(String customerContact) {
        CustomerContact = customerContact;
    }

    public String getCustomerOwner() {
        return CustomerOwner;
    }

    public void setCustomerOwner(String customerOwner) {
        CustomerOwner = customerOwner;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(String notificationId) {
        NotificationId = notificationId;
    }
}
