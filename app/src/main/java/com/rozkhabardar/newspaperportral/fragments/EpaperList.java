package com.rozkhabardar.newspaperportral.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.adapter.NewslistAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class EpaperList extends Fragment implements NewslistAdapter.Clickable {
    List<String> newslist=new ArrayList<>();
    View view;
    RecyclerView recyclerView;
    NewslistAdapter newslistAdapter;
    EditText etSearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.newspaperlist,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        recyclerView= (RecyclerView) view.findViewById(R.id.rec_newslist);
        etSearch= (EditText) view.findViewById(R.id.et_search);
        etSearch.setVisibility(View.GONE);
        return view;

    }
    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(getResources().getString(R.string.action_epaper));

        setuplist();
    }
    private void setuplist() {
        if(newslist!=null)
        {
            newslist.clear();
        }
        newslist.add("English");
        newslist.add("Urdu");
        newslist.add("Kashmiri");
        newslist.add("Hindi");
        newslistAdapter=new NewslistAdapter(newslist);
        newslistAdapter.submit(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newslistAdapter);

    }
    @Override
    public void clickitem(View v, int postion) {
        try {
            TextView txtname= (TextView) v.findViewById(R.id.txt_newspapers);
            String name=txtname.getText().toString();
            Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
            senddata(name);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    private void senddata(String name) {
        EpapersList newsPapersfrag=new EpapersList();
        Bundle bun = new Bundle();
        if (Utility.checknetwork(getContext()))
        {
            switch (name)
            {
                case "English":
                    bun.putString("Lang","English");
                    break;
                case "Urdu":
                    bun.putString("Lang","Urdu");
                    break;
                case "Kashmiri":
                    bun.putString("Lang","Kashmiri");
                    break;
                case "Hindi":
                bun.putString("Lang","Hindi");
                break;
            }
            newsPapersfrag.setArguments(bun);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,newsPapersfrag).addToBackStack(null).commit();

        }

        }
}
