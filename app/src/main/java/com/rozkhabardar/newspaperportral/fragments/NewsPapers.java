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
public class NewsPapers extends Fragment implements NewslistAdapter.Clickable, TextView.OnEditorActionListener {
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
         newslist= Arrays.asList(getResources().getStringArray(R.array.newspapers));
        Collections.sort(newslist,String.CASE_INSENSITIVE_ORDER);
         etSearch= (EditText) view.findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(this);
        addtextlistener();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(getResources().getString(R.string.action_newspapers));
        setuplist();
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
                if (name.equalsIgnoreCase("Greater Kashmir")) {
                    gotogk();
                } else {
                    switch (name) {
                        case "Kashmir Observer":
                            bun.putString("Link", "https://kashmirobserver.net/rss.xml");
                            break;
                        case "Rising Kashmir":
                            bun.putString("Link", "http://www.risingkashmir.com/rss");
                            break;
                        case "Urdu Aftab Daily Newspaper":
                            bun.putString("Link", "http://feeds.feedburner.com/dailyaftab?format=xml");
                            break;
                        case "Kashmir Quick News":
                            bun.putString("Link", "http://www.kashmirquicknews.com/feeds/posts/default?alt=rss");
                            break;
                        case "State Times":
                            bun.putString("Link", "http://news.statetimes.in/feed/");
                            break;
                        case "Daily Excelsior":
                            bun.putString("Link", "http://www.dailyexcelsior.com/feed/");
                            break;
                        case "Kashmir Global":
                            bun.putString("Link", "http://kashmirglobal.com/feed");
                            break;
                        case "Kashmir Sports Watch":
                            bun.putString("Link", "http://www.ksportswatch.com/feed/");
                            break;
                        case "Kashmir Glory":
                            bun.putString("Link", "http://kashmirglory.com/feed/");
                            break;
                        case "Fast Kashmir":
                            bun.putString("Link", "http://www.fastkashmir.com/feed/");
                            break;
                        case "The Kashmir Pulse":
                            bun.putString("Link", "https://kashmirpulse.com/feed");
                            break;
                        case "Kashmir Reader":
                            bun.putString("Link", "http://feeds.feedburner.com/kashmir-reader?format=xml");
                            break;
                        case "JK Newspoint":
                            bun.putString("Link", "http://jknewspoint.com/feed/");
                            break;
                        case "Kashmir Media Watch":
                            bun.putString("Link", "http://kashmirmediawatch.com/feed");
                            break;
                        case "Kashmir Watch":
                            bun.putString("Link", "http://kashmirwatch.com/feed/");
                            break;
                        case "France 24":
                            bun.putString("Link", "http://www.france24.com/en/tag/kashmir/rss");
                            break;
                        case "The Northlines":
                            bun.putString("Link", "http://www.thenorthlines.com/feed/");
                            break;
                        case "KNS-Kashmir News Services":
                            bun.putString("Link", "http://feeds.feedburner.com/KashmirNewsService?format=xml");
                            break;
                        case "The Indian Express":
                            bun.putString("Link", "http://indianexpress.com/about/kashmir/feed/");
                            break;
                        case "State Observer":
                            bun.putString("Link", "http://www.stateobserver.com/feed/");
                            break;
                        case "The Shadow":
                            bun.putString("Link", "http://theshadow.in/feed/");
                            break;
                        case "U4UVoice":
                            bun.putString("Link", "http://u4uvoice.com/feed/");
                            break;
                        case "Kashmir Life":
                            bun.putString("Link", "http://kashmirlife.net/feed/");
                            break;
                        case "Free Press Kashmir":
                            bun.putString("Link", "http://freepresskashmir.com/feed/");
                            break;
                        case "CNS-Current News Service":
                            bun.putString("Link", "http://cnskashmir.com/feed/");
                            break;
                        case "With Kashmir":
                            bun.putString("Link", "http://withkashmir.org/feed/");
                            break;
                        case "The Tribune":
                            bun.putString("Link", "http://www.tribuneindia.com/rss/feed.aspx?cat_id=5");
                            break;
                        case "Voice of Valley":
                            bun.putString("Link", "http://voiceofvalley.com/feed/");
                            break;
                        case "Kashmir Vision":
                            bun.putString("Link", "http://kashmirvision.in/Feed/rss.xml");
                            break;
                        case "Kashmir Age":
                            bun.putString("Link", "http://kashmirage.net/feed/");
                            break;
                        case "Kashmir Convener":
                            bun.putString("Link", "http://kashmirconvener.com/index.php/feed/");
                            break;
                        case "Brighter Kashmir":
                            bun.putString("Link", "http://brighterkashmir.com/feed/");
                            break;
                        case "Kashmir Thunder":
                            bun.putString("Link", "http://kashmirthunder.com/feed/");
                            break;
                        case "Daily Gadyal":
                            bun.putString("Link", "http://dailygadyal.com/feed/");
                            break;
                        case "Wadi Ki Awaz":
                            bun.putString("Link", "http://wadikiawaz.org/feed/");
                            break;
                        case "Trending Kashmir":
                            bun.putString("Link", "https://trendngkashmir.com/feed/");
                            break;
                        case "The Kashmir Scenario":
                            bun.putString("Link", "http://thekashmirscenario.com/feed/");
                            break;
                        case "Kashmir Post":
                            bun.putString("Link", "http://kashmirpost.org/feed/");
                            break;
                        case "JKNN":
                            bun.putString("Link", "http://jknn.net/feed/");
                            break;
                        case "SACH NEWS":
                            bun.putString("Link", "http://jksachnews.com/feed/");
                            break;
                        case "Kashmir News Trust":
                            bun.putString("Link", "http://www.kashmirnewstrust.com/feed/");
                            break;
                        case "Greater Jammu":
                            bun.putString("Link", "http://greaterjammu.com/feed/");
                            break;
                        case "The Rehmat":
                            bun.putString("Link", "http://therehmat.com/feed/");
                            break;
                        case "JandK Headlines":
                            bun.putString("Link", "https://jandkheadlines.com/feed/");
                            break;
                        case "Kashmir Media Service":
                         //   bun.putString("Link", "https://kmsnewsorg.wordpress.com/feed/");
                            bun.putString("Link", "http://kmsnews.org/news/feed/");
                            break;
                        case "Vaadi Online":
                            bun.putString("Link", "http://www.vaadionline.in/feed/");
                            break;
                        case "Jehlum Post":
                            bun.putString("Link", "http://www.jehlumpost.com/index.php?format=feed&type=rss");
                            break;
                        case "Good Morning Kashmir":
                            bun.putString("Link", "http://feeds.feedburner.com/GoodMorningKashmir");
                            break;
                        case "Amar Ujala Hindi News":
                            bun.putString("Link","http://www.amarujala.com/rss/srinagar.xml");
                            break;
                        case "Cross Town News":
                            bun.putString("Link", "http://www.crosstownnews.in/rss.xml");
                            break;
                        case "Jagran Hindi News":
                            bun.putString("Link", "http://rss.jagran.com/local/jammu-and-kashmir/srinagar.xml");
                            break;
                        case "Kashmir Today":
                            bun.putString("Link","https://kashmir.today/feed/");
                            break;
                        case "Kashmir Patriot":
                            bun.putString("Link","http://kashmirpatriot.com/feed/");
                            break;
                        case "Kashmir Voice":
                            bun.putString("Link","http://kashmirvoice.org/index.php/feed/");
                            break;
                        case "JK DAILY NEWS":
                            bun.putString("Link","http://jkdailynews.com/feed/");
                            break;
                        case "Tral Times":
                            bun.putString("Link","http://traltimes.com/feed/");
                            break;
                        case "Humans Of Jandk":
                            bun.putString("Link","https://humansofjandk.com/feed");
                            break;
                        case "Real Kashmir News":
                            bun.putString("Link","https://realkashmirnews041.wixsite.com/realkashmirnews/feed.xml");
                            break;
                        case "THE TIMES OF KASHMIR":
                            bun.putString("Link","http://thetimesofkashmir.com/feed/");
                            break;
                        case "Geo News Kashmir":
                            bun.putString("Link","https://geonewshec.wordpress.com/feed/");
                            break;
                        case "The INS News":
                            bun.putString("Link","https://theins.in/feed/");
                            break;
                        case "JK NNI News":
                            bun.putString("Link","http://www.jknni.com/feed/");
                            break;
                        case "Kashmir Essence":
                            bun.putString("Link","http://kashmiressence.news/feed/");
                            break;
                        case "Kashmir Daily News":
                            bun.putString("Link","http://www.kashmirdailynews.com/feed/");
                            break;
                        case "Live Kashmir News":
                            bun.putString("Link","http://www.lkashmirnews.in/feeds/posts/default");
                            break;
                        case "Razor Times[RNN]":
                            bun.putString("Link","https://razortimes.wordpress.com/feed/");
                            break;
                        case "JandK News Service":
                            bun.putString("Link","http://jknewsservice.com/feed/");
                            break;
                        case "Jammu Kashmir Online News":
                            bun.putString("Link","https://jkonlinenews.wordpress.com/feed/");
                            break;
                        case "Kashmir News":
                            bun.putString("Link","http://www.kashmirnews.co.in/feed/");
                            break;
                        case "True News Asia":
                            bun.putString("Link","https://truenews.asia/feed/");
                            break;
                        case "Kashmir Reflector":
                            bun.putString("Link","http://www.kashmirreflector.com/feeds/posts/default");
                            break;
                        case "Kashmir News Zone":
                            bun.putString("Link","http://www.kashmirnewszone.com/feed/");
                            break;
                        case "Era Of Kashmir":
                            bun.putString("Link","http://eraofkashmir.com/feed/");
                            break;
                        case "Kashmir News Wire":
                            bun.putString("Link","http://kashmirnewswire.com/feed/");
                            break;
                        case "JK Evening Mail":
                            bun.putString("Link","http://www.jkeveningmail.com/feeds/posts/default");
                            break;
                        case "NEWZ KASHMIR":
                            bun.putString("Link","http://www.newzkashmir.com/feed/");
                            break;
                        case "Chinar News":
                            bun.putString("Link","https://www.blogger.com/feeds/7562697165118248523/posts/default");
                            break;
                        case "JandK Now":
                            bun.putString("Link","http://www.jandknow.com/feed");
                            break;
                        case "Only Kashmir":
                            bun.putString("Link","http://onlykashmir.in/category/state/feed/");
                            break;
                    }
                    feedgrabber.setArguments(bun);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.subview_container, feedgrabber).addToBackStack(null).commit();
                }
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

