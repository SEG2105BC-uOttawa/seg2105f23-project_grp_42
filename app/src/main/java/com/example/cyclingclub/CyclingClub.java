package com.example.cyclingclub;

import java.util.*;


public class CyclingClub {
    private String key;
    private User user;
    private String clubName;
    private String socialMediaLink;
    private String mainContact;
    private String phoneNumber;
    private String region;
    private String username;

    private List<Map<String, Object>> rateComments;


    // Constructors (if needed)

    // Constructor
    public CyclingClub() {
        this.rateComments = new ArrayList<>();
    }


    public String getKey(){return key;}
    public void setKey(String key){this.key=key;}


    // Getter and Setter for 'user'
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        //this.username = user.getUsername();
        this.user = user;
    }



    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }


    // Getter and Setter for 'socialMediaLink'
    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        this.socialMediaLink = socialMediaLink;
    }

    // Getter and Setter for 'mainContact'
    public String getMainContact() {
        return mainContact;
    }

    public void setMainContact(String mainContact) {
        this.mainContact = mainContact;
    }

    // Getter and Setter for 'phoneNumber'
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for 'region'
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public String getUsername() {     return username;}

    public void setUsername(String username) {this.username = username;}


    // Getter for the rateComments
    public List<Map<String, Object>> getRateComments() {
        return rateComments;
    }

    // Method to add a comment to the list or update comment from a user already exists
    public void addRateComment(String userName, String comment, int rate) {
        for (Map<String, Object> rateComment : rateComments) {
            if (rateComment.get("userName").equals(userName)) {
                // Comment with the same username already exists, update the comment and rate
                rateComment.put("comment", comment);
                rateComment.put("rate", rate);
                return;
            }
        }

        // Person with the given name doesn't exist, add a new person to the list
        Map<String, Object> newComment = new HashMap<>();
        newComment.put("userName", userName);
        newComment.put("comment", comment);
        newComment.put("rate", rate);
        rateComments.add(newComment);
    }




}
