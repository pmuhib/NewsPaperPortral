package com.rozkhabardar.newspaperportral.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.adapter.NewslistAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.fabric.sdk.android.Fabric;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class Magazines extends Fragment implements TextView.OnEditorActionListener, NewslistAdapter.Clickable {
    List<String> newslist=new ArrayList<>();
    View view;
    RecyclerView recyclerView;
    NewslistAdapter newslistAdapter;
    EditText etSearch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.newspaperlist,container,false);
        Fabric.with(getActivity(), new Crashlytics(), new CrashlyticsNdk());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        recyclerView= (RecyclerView) view.findViewById(R.id.rec_newslist);
        newslist= Arrays.asList(getResources().getStringArray(R.array.magazines));
        Collections.sort(newslist,String.CASE_INSENSITIVE_ORDER);
        etSearch= (EditText) view.findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(this);

        addtextlistener();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        setuplist();

        toolbar.setTitle(getResources().getString(R.string.action_magazines));

    }

    private void setuplist() {
        newslistAdapter=new NewslistAdapter(newslist);
        newslistAdapter.submit(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(newslistAdapter);

    }

    private void addtextlistener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                addthis(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void clickitem(View v, int postion) {
        TextView txtname= (TextView) v.findViewById(R.id.txt_newspapers);
        String name=txtname.getText().toString();
        Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
        senddata(name);
    }

    public  void addthis(CharSequence s)
    {
        s=s.toString().toLowerCase();
        List<String> list= new ArrayList<String>();
        for (int i=0;i<newslist.size();i++)
        {
            String match=newslist.get(i).toString().toLowerCase();
            if(match.contains(s))
            {
                list.add(newslist.get(i));
            }
        }
        if(list.isEmpty())
        {
            Utility.message(getContext(),"No record Found");

        }
        else {

            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            newslistAdapter = new NewslistAdapter(list);
            newslistAdapter.submit(this);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
            recyclerView.setAdapter(newslistAdapter);
            newslistAdapter.notifyDataSetChanged();
        }
    }
    private void senddata(String name) {

        try {
            if (Utility.checknetwork(getContext())) {
                FeedgrabberFragment feedgrabber = new FeedgrabberFragment();
                Bundle bun = new Bundle();
                bun.putString("Title", name);

                switch (name) {
                    case "Lost Kashmiri History":
                        bun.putString("Link", "http://lostkashmirihistory.com/feed/");
                        break;
                    case "Kashmir Magazine":
                        bun.putString("Link", "http://www.kashmirmagazine.net/feed/");
                        break;
                    case "The Kashmir Walla":
                        bun.putString("Link", "http://thekashmirwalla.com/feed/");
                        break;
                    case "Kashmir Scan":
                        bun.putString("Link", "http://kashmirscan.com/feed/");
                        break;
                    case "News Kashmir Magazine":
                        bun.putString("Link", "http://www.newskashmirmagazine.com/feed/");
                        break;
                    case "Kashmir Life(Weekly Magazine from Kashmir)":
                        bun.putString("Link", "http://kashmirlife.net/tag/weekly-magazine-from-kashmir/feed/");
                        break;
                    case "Kashmiri Prisoners":
                        bun.putString("Link","https://www.blogger.com/feeds/6446855205529749029/posts/default");
                        break;
                    case "UN Resolutions":
                        bun.putString("Link","https://www.kmsnews.org/archives/category/freedom-struggle/un-resolutions/feed/");
                        break;
                    case "Kashmir First News":
                        bun.putString("Link","https://kashmirfirstnews.blogspot.com/feeds/posts/default");
                        break;
                    case "Kashmir Oral History":
                        bun.putString("Link","https://kashmiroralhistory.org/feed/");
                        break;
                    case "Healing Kashmir":
                        bun.putString("Link","http://healingkashmir.org/latestNews/feed/");
                        break;
                    case "Search Kashmir":
                        bun.putString("Link","http://feeds.feedburner.com/SearchKashmir");
                        break;
                    case "Green Kashmir":
                        bun.putString("Link","https://www.greenkashmir.org/feed.xml");
                        break;
                    case "Kashmir Forum":
                        bun.putString("Link","https://www.kashmirforum.org/feed/");
                        break;
                }
                feedgrabber.setArguments(bun);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, feedgrabber).addToBackStack(null).commit();

            }
            else
            {
                Snackbar.make(getActivity().findViewById(android.R.id.content),"Check Your Internet Connection",Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Crashlytics.logException(e);

        }
    }

    private void gotogk() {
        GreaterKashmirTabFragment tabFragment=new GreaterKashmirTabFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, tabFragment).addToBackStack(null).commit();

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        int id=v.getId();
        switch (id)
        {
            case R.id.et_search:
                if(actionId== EditorInfo.IME_ACTION_SEARCH)
                {
                    InputMethodManager methodManager= (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                    addthis(v.getText());

                }
        }
        return false;
    }
}

