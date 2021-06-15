package ar.edu.itba.example.gymateapp.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo {
    @Expose
    @SerializedName("username")
    private String username;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("avatarUrl")
    private String avatarUrl;

    private Drawable profileImg;

    public UserInfo(String username, String password, String avatarUrl) {
        this.username = username;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "userame: "+username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Drawable getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(Drawable profileImg) {
        this.profileImg = profileImg;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}
