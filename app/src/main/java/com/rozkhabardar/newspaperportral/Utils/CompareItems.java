package com.rozkhabardar.newspaperportral.Utils;

import com.rozkhabardar.newspaperportral.models.Items;

import java.util.Comparator;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CompareItems implements Comparator<Items> {
    @Override
    public int compare(Items a, Items b) {
        return a.getTitle().compareTo(b.getTitle());
    }
}
