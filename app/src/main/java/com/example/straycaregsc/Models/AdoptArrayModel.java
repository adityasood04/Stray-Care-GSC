package com.example.straycaregsc.Models;

import java.util.ArrayList;

public class AdoptArrayModel {
    ArrayList<AdoptPostsModel> adoptPostsArray = new ArrayList<>();

    public AdoptArrayModel() {
    }

    public ArrayList<AdoptPostsModel> getAdoptPostsArray() {
        return adoptPostsArray;
    }

    public void setAdoptPostsArray(ArrayList<AdoptPostsModel> adoptPostsArray) {
        this.adoptPostsArray = adoptPostsArray;
    }

}
