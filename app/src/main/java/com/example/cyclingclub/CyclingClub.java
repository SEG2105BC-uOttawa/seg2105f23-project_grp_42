package com.example.cyclingclub;

public class CyclingClub {
    private String key;
    private User user;
    private String clubName;
    private String socialMediaLink;
    private String mainContact;
    private String phoneNumber;
    private String region;


    // Constructors (if needed)

    public String getKey(){return key;}
    public void setKey(String key){this.key=key;}


    // Getter and Setter for 'user'
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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



}
