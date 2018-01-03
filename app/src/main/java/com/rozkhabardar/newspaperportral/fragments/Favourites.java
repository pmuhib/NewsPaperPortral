package com.rozkhabardar.newspaperportral.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.CompareItems;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.activities.WebActivity;
import com.rozkhabardar.newspaperportral.adapter.MyAdapter;
import com.rozkhabardar.newspaperportral.models.Items;
import com.rozkhabardar.newspaperportral.sharedpref.SharedPreference;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.saveslist;
import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class Favourites extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyAdapter.Clickable {
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    MyAdapter myAdapter;

    Set<Items> itemset;
    SharedPreference sharedPreference;
    ArrayList<Items> newitems;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_main,container,false);
        sharedPreference=new SharedPreference();

        refreshLayout= (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(getResources().getString(R.string.action_fav));
        saveslist=sharedPreference.getFavorites(getContext());
        checkdata();
        if(myAdapter!=null)
        {
            myAdapter.notifyDataSetChanged();
        }

    }

    private void checkdata() {
        if(saveslist==null || saveslist.size()<=0)
        {
            Utility.message(getContext(),"No Record Found");
        }
        else
        {
            itemset=new TreeSet<Items>(new CompareItems());
            newitems=new ArrayList<>();
            for(Items i:saveslist)
            {
                if(!itemset.add(i))
                {
                    newitems.add(i);
                }
            }
            saveslist.clear();
            saveslist.addAll(itemset);
            recyclerView= (RecyclerView)view.findViewById(R.id.recyler);
            myAdapter=new MyAdapter(getContext(),saveslist,"Favourites");
            myAdapter.submit(this);
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(myAdapter);
       }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(false);
        if(myAdapter!=null)
        {
            myAdapter.notifyDataSetChanged();
        }
        checkdata();

    }

    @Override
    public void click(View view, int Position) {
        String link=saveslist.get(Position).getLink();
        String title=saveslist.get(Position).getTitle();
        Intent it= new Intent(getContext(), WebActivity.class);
        it.putExtra("url",link);
        it.putExtra("title",title);
        Log.d("Muhib",link);
        startActivity(it);
    }
}
