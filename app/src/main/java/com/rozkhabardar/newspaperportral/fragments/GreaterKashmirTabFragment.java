package com.rozkhabardar.newspaperportral.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.adapter.PageAdapter;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class GreaterKashmirTabFragment extends Fragment {
    View view;
    TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.greaterkashmirtabfragment,container,false);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_GK);
        tabLayout.addTab(tabLayout.newTab().setText("Kashmir"));
        tabLayout.addTab(tabLayout.newTab().setText("Srinagar"));
        tabLayout.addTab(tabLayout.newTab().setText("Jammu"));
        tabLayout.addTab(tabLayout.newTab().setText("Pir Panjal"));
        tabLayout.addTab(tabLayout.newTab().setText("Business"));
        tabLayout.addTab(tabLayout.newTab().setText("Health"));
        tabLayout.addTab(tabLayout.newTab().setText("Life & Style"));
        tabLayout.addTab(tabLayout.newTab().setText("Sports"));
        tabLayout.addTab(tabLayout.newTab().setText("India"));
        tabLayout.addTab(tabLayout.newTab().setText("Editorial"));
        tabLayout.addTab(tabLayout.newTab().setText("Opinion"));

        toolbar.setTitle("GK");

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager pager= (ViewPager) view.findViewById(R.id.pager_GK);
        PageAdapter adapter=new PageAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }
}
