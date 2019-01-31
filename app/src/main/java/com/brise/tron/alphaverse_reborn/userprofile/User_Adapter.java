package com.brise.tron.alphaverse_reborn.userprofile;

public class User_Adapter {

    private String User_Name,Phone_Number, EmailAddress,Residence,Profile_Photo,SignUp_Date,NotificationID;

    public User_Adapter() {
    }

    public User_Adapter(String user_Name, String phone_Number, String emailAddress, String residence,String profile_Photo, String signUp_Date,String notificationID) {
        this.User_Name = user_Name;
        this.Phone_Number = phone_Number;
        this.EmailAddress = emailAddress;
        this.Residence = residence;
        this.Profile_Photo = profile_Photo;
        this.SignUp_Date = signUp_Date;
        this.NotificationID = notificationID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public String getResidence() {
        return Residence;
    }

    public String getProfile_Photo() {
        return Profile_Photo;
    }

    public String getSignUp_Date() {
        return SignUp_Date;
    }

    public String getNotificationID() {
        return NotificationID;
    }
}
