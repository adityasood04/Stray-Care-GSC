package com.example.straycaregsc;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GlobalPostsModel {
    ArrayList<PostModel> postsArray = new ArrayList<>();


    public GlobalPostsModel() {
        this.postsArray = postsArray;
    }

    public ArrayList<PostModel> getPostsArray() {
        return postsArray;
    }

    public void setPostsArray(ArrayList<PostModel> postsArray) {
        this.postsArray = postsArray;
    }
}
