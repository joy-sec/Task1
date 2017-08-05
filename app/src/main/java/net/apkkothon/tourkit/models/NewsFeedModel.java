package net.apkkothon.tourkit.models;



public class NewsFeedModel {
    private String profileName;

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFeedMgs() {
        return feedMgs;
    }

    public void setFeedMgs(String feedMgs) {
        this.feedMgs = feedMgs;
    }

    public String getFeedImage() {
        return feedImage;
    }

    public void setFeedImage(String feedImage) {
        this.feedImage = feedImage;
    }

    private String profileImage;
    private String time;
    private String feedMgs;
    private String feedImage;
}
