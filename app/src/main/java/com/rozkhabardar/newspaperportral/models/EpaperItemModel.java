package com.rozkhabardar.newspaperportral.models;

import java.io.Serializable;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class EpaperItemModel implements Serializable {
    public String name;
    public String imagelink;

    public EpaperItemModel(String name, String imagelink) {
        this.name = name;
        this.imagelink = imagelink;
    }
}
