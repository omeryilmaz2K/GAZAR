package com.devsgazar.gazar.Model;

public class Users {

    private String UserID, UserProfile, UserEmail;

    public Users(String userID, String userProfile, String userEmail) {
        UserID = userID;
        UserProfile = userProfile;
        UserEmail = userEmail;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserProfile() {
        return UserProfile;
    }

    public void setUserProfile(String userProfile) {
        UserProfile = userProfile;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }
}
