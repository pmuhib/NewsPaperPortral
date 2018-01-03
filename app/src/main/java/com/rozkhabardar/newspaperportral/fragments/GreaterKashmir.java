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

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.activities.WebActivity;
import com.rozkhabardar.newspaperportral.adapter.MyAdapter;
import com.rozkhabardar.newspaperportral.models.Items;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import io.fabric.sdk.android.Fabric;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.mainfeed;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class GreaterKashmir extends Fragment implements MyAdapter.Clickable, SwipeRefreshLayout.OnRefreshListener {
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    MyAdapter myAdapter;
    ArrayList<Items> feedintems=new ArrayList<Items>();
    String url,name;
    String status;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_main,container,false);
        Fabric.with(getActivity(), new Crashlytics(), new CrashlyticsNdk());
        Bundle bundle=getArguments();
        url = bundle.getString("Link");
        name=bundle.getString("Title");
        recyclerView= (RecyclerView)view.findViewById(R.id.recyler);
        refreshLayout= (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        myAdapter=new MyAdapter(getContext(),feedintems,"GreaterKashmir");
        myAdapter.submit(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        preapredata();
    }
    private void preapredata() {
        if(feedintems!=null) {
            feedintems.clear();
        }
        Utility.showpop(getActivity());
        parsemwithxml();
       /* ApiList retrofit= RetrofitBuilder.retrofitapisjson();
        Call<ApiResponse> apiResponseCall = retrofit.getfeed("api.json?rss_url=" + url);
        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.body()!=null) {
                    status = response.body().status;
                    if (status.equalsIgnoreCase("ok")) {
                        List<Items> feedItems = response.body().items;
                        for (int i = 0; i < feedItems.size(); i++) {
                            Items feediitems = new Items();
                            ArrayList<String> cat = feedItems.get(i).categories;
                            for (int j = 0; j < cat.size(); j++) {
                                Categories categories = new Categories();
                                categories.setKeys(cat.get(j));
                            }
                            feediitems.setTitle(feedItems.get(i).getTitle());
                            String desc = feedItems.get(i).getDescription();
                            String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                            Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
                            Matcher urlMatcher = pattern.matcher(desc);
                            while (urlMatcher.find()) {
                                String url = desc.substring(urlMatcher.start(0),
                                        urlMatcher.end(0));

                                if (url.contains(".jpg")) {
                                    feediitems.setImagelink(url);
                                    break;
                                }
                            }
                            desc = desc.replaceAll("<(.*?)>", "");//Removes all items in brackets
                            desc = desc.replaceAll("&ldpos;", "");
                            desc = desc.replaceAll("&ldquo;", "");
                            desc = desc.replaceAll("&nbsp;", "");
                            desc = desc.replaceAll("&amp;", "");
                            desc = desc.replaceAll("&rdpos;", "");
                            desc = desc.replaceAll("&rdquo;", "");
                            desc = desc.replaceAll("&rsqvo;", "");
                            desc = desc.replaceAll("&mdash;", "");
                            desc = desc.replaceAll("&lt;", "");
                            desc = desc.replaceAll("/p&gt;", "");

                            feediitems.setDescription(desc);
                            feediitems.setPubDate(feedItems.get(i).getPubDate());
                            feediitems.setLink(feedItems.get(i).getLink());
                            Log.d("Muhib", desc);
                            feedintems.add(feediitems);
                        }
                        myAdapter.notifyDataSetChanged();
                    } else {
                        parsemwithxml();
                        Toast.makeText(getContext(), "" + response.body().status + "\n" + response.body().message, Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    parsemwithxml();
                   // Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Utility.hidepopup();
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });*/

    }
    @Override
    public void click(View view, int Position) {
        // Toast.makeText(getContext(),"Hello",Toast.LENGTH_LONG).show();
        String link=feedintems.get(Position).getLink();
        String title=feedintems.get(Position).getTitle();
        Intent it= new Intent(getActivity(), WebActivity.class);
        it.putExtra("url",link);
        it.putExtra("title",title);
        Log.d("Muhib",link);
        startActivity(it);
    }
    private void parsemwithxml() {
        Parser parser = new Parser();
        parser.execute(url);
        parser.onFinish(new Parser.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<Article> list) {

                try {
                    for (int i=0;i<list.size();i++) {
                        String title = list.get(i).getTitle();
                        String desc = list.get(i).getDescription();
                        Items feediitems = new Items();
                        title = title.replaceAll("<(.*?)>", "");//Removes all items in brackets
                        title = title.replaceAll("&ldpos;", "");
                        title = title.replaceAll("&ldquo;", "");
                        title = title.replaceAll("&nbsp;", "");
                        title = title.replaceAll("&amp;", "");
                        title = title.replaceAll("&rdpos;", "");
                        title = title.replaceAll("&rdquo;", "");
                        title = title.replaceAll("&rsqvo;", "");
                        title = title.replaceAll("&mdash;", "");
                        title = title.replaceAll("&lt;", "");
                        title = title.replaceAll("/p&gt;", "");
                        title = title.replaceAll("rsquo;", "");
                        title = title.replaceAll("&quot;", "");

                        feediitems.setTitle(title);

                        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
                        Matcher urlMatcher = pattern.matcher(desc);
                        while (urlMatcher.find()) {
                            String url = desc.substring(urlMatcher.start(0),
                                    urlMatcher.end(0));

                            if (url.contains(".jpg")) {
                                feediitems.setImagelink(url);
                                break;
                            }
                        }
                        feediitems.setImagelink(list.get(i).getImage());
                        desc = desc.replaceAll("<(.*?)>", "");//Removes all items in brackets
                        desc = desc.replaceAll("&ldpos;", "");
                        desc = desc.replaceAll("&ldquo;", "");
                        desc = desc.replaceAll("&nbsp;", "");
                        desc = desc.replaceAll("&amp;", "");
                        desc = desc.replaceAll("&rdpos;", "");
                        desc = desc.replaceAll("&rdquo;", "");
                        desc = desc.replaceAll("&rsqvo;", "");
                        desc = desc.replaceAll("&mdash;", "");
                        desc = desc.replaceAll("&lt;", "");
                        desc = desc.replaceAll("/p&gt;", "");
                        desc = desc.replaceAll("rsquo;&", "");
                        desc = desc.replaceAll("&quot;", "");

                        feediitems.setDescription(desc);
                        feediitems.setPubDate(String.valueOf(list.get(i).getPubDate()));
                        feediitems.setLink(list.get(i).getLink());
                        Log.d("Muhib", desc);
                        feedintems.add(feediitems);
                        //     Toast.makeText(getContext(),""+title,Toast.LENGTH_LONG).show();
                        //   Log.d("title",title);
                        if(i==0)
                        {
                            mainfeed.add(feediitems);
                        }
                        Utility.hidepopup();
                    }
                    myAdapter.notifyDataSetChanged();
                } catch (PatternSyntaxException e) {
                    Crashlytics.logException(e);

                } catch (IllegalStateException e) {
                    Crashlytics.logException(e);
                }


            }

            @Override
            public void onError() {
                Log.d("Muhib","Error");
            }
        });
    }

    @Override
    public void onRefresh() {
        preapredata();
        refreshLayout.setRefreshing(false);
    }

}
        /*Call<Feed> feedCall=retrofit.getitems();
        feedCall.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                Utility.hidepopup();
                List<Feeditems> feedItems=response.body().getChannel().getFeeditemsList();
                for (int i=0;i<feedItems.size();i++)
                {
                    Feeditems feediitems=new Feeditems();

                    feediitems.setTitle(feedItems.get(i).getTitle());
                    String desc=feedItems.get(i).getDescription();
                    String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
                    Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
                    Matcher urlMatcher = pattern.matcher(desc);
                    while (urlMatcher.find())
                    {
                        String url = desc.substring(urlMatcher.start(0),
                                urlMatcher.end(0));

                        if(url.contains(".jpg"))
                        {
                            feediitems.setLink(url);
                            break;
                        }

                    }
                    desc=desc.replaceAll("<(.*?)>","");//Removes all items in brackets
                    desc=desc.replaceAll("&ldpos;","");
                    desc=desc.replaceAll("&ldquo;","");
                    desc=desc.replaceAll("&nbsp;","");
                    desc=desc.replaceAll("&amp;","");
                    desc=desc.replaceAll("&rdpos;","");
                    desc=desc.replaceAll("&rdquo;","");
                    desc=desc.replaceAll("&rsqvo;","");
                    desc=desc.replaceAll("&mdash;","");
                    for(int j=0;j<desc.length();j++) {
                        // desc.substring()
                    }
                    feediitems.setDescription(desc);
                    feediitems.setPubDate(feedItems.get(i).getPubDate());

                    Log.d("Muhib", desc);
                    feedintems.add(feediitems);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Utility.hidepopup();
                Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });*/