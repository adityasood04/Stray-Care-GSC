package com.example.straycaregsc.Models;

import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class PostModel {

    String imageUrl;
    String id;
    String caption;
    String description;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    int likes;

    public String getUser() {

        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String user;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public PostModel() {
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
