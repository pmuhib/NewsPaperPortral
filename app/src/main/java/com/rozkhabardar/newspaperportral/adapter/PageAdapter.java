package com.rozkhabardar.newspaperportral.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rozkhabardar.newspaperportral.fragments.GreaterKashmir;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    int nooftabs;
    private String[] titles={"Kashmir","Srinagar"};
    public PageAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        nooftabs=tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        GreaterKashmir greaterKashmir=new GreaterKashmir();
        Bundle bundle=new Bundle();
        bundle.putString("Title", "GK");
        switch (position)
        {
            case 0:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=2" );
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 1:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=3");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 2:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=4");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 3:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=31");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 4:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=10");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 5:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=23");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 6:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=32");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 7:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=9");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 8:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=7");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 9:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=8");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 10:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=26");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            case 11:
                bundle.putString("Link","https://www.greaterkashmir.com/feed.aspx?cat_id=12");
                greaterKashmir.setArguments(bundle);
                return greaterKashmir;
            default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return nooftabs;
    }
}
