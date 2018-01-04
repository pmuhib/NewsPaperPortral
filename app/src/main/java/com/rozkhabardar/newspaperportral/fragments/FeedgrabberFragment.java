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
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.activities.WebActivity;
import com.rozkhabardar.newspaperportral.adapter.MyAdapter;
import com.rozkhabardar.newspaperportral.models.Items;
import com.rozkhabardar.newspaperportral.webservices.ApiList;
import com.rozkhabardar.newspaperportral.webservices.ApiResponse;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

import com.rozkhabardar.newspaperportral.webservices.RetrofitBuilder;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.mainfeed;
import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class FeedgrabberFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MyAdapter.Clickable {
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    MyAdapter myAdapter;
    ArrayList<Items> feedintems=new ArrayList<Items>();
    String name,url,Apikey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_main,container,false);
        Fabric.with(getActivity(), new Crashlytics(), new CrashlyticsNdk());
        Apikey="c945ddexcq3c2jtakiztsgzpayvnfsidwela0njo";
        Bundle bundle=getArguments();
        name=bundle.getString("Title");
        url=bundle.getString("Link");
        recyclerView= (RecyclerView)view.findViewById(R.id.recyler);
        refreshLayout= (SwipeRefreshLayout)view.findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        myAdapter=new MyAdapter(getContext(),feedintems,"FeedgrabberFragment");
        myAdapter.submit(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(name);
        preapredata();
    }
    private void preapredata() {
        if (feedintems != null) {
            feedintems.clear();
        }
        if(Utility.checknetwork(getContext())) {
            Utility.showpop(getActivity());
            //   parsemwithxml();
            ApiList retrofit = RetrofitBuilder.retrofitapisjson();
            Call<ApiResponse> apiResponseCall = retrofit.getfeed("api.json?rss_url=" + url,Apikey);
            apiResponseCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    try {

                        if(response.body()!=null) {
                            String status = response.body().status;
                            if (status.equalsIgnoreCase("ok")) {
                                ArrayList<Items> feedItems = response.body().items;
                                if (feedItems.size() < 1) {
                                    parsemwithxml();
                                } else {

                                    for (int i = 0; i < feedItems.size(); i++) {
                                        Items feediitems = new Items();
                                        String title = feedItems.get(i).getTitle();

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
                                        title = title.replaceAll("&quot;", "");


                                        feediitems.setTitle(title);
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
                                        String content = feedItems.get(i).getContent();
                                        Matcher urlMatcher1 = pattern.matcher(content);
                                        while (urlMatcher.find()) {
                                            String url = content.substring(urlMatcher.start(0),
                                                    urlMatcher.end(0));

                                            if (url.contains(".jpg")) {
                                                feediitems.setImagelink(url);
                                                break;
                                            }
                                        }
                                        String image = feedItems.get(i).getThumbnail();
                                        String image1 = feedItems.get(i).getImagelink();
                                        if (image != null || !image.isEmpty()) {
                                            feediitems.setImagelink(image);
                                        } else if (image1 != null || !image1.isEmpty()) {
                                            feediitems.setImagelink(image1);
                                        }

                                        desc = desc.replaceAll("<(.*?)>", "");//Removes all items in brackets
                                        desc = desc.replaceAll("&ldpos;", "");
                                        desc = desc.replaceAll("&ldquo;", "");
                                        desc = desc.replaceAll("&nbsp;", "");
                                        desc = desc.replaceAll("&amp;", "");
                                        desc = desc.replaceAll("&rdpos;", "");
                                        desc = desc.replaceAll("&rdquo;", "");
                                        desc = desc.replaceAll("&rsqvo;", "");
                                        desc = desc.replaceAll("&rsquo;", "");
                                        desc = desc.replaceAll("&mdash;", "");
                                        desc = desc.replaceAll("&lt;", "");
                                        desc = desc.replaceAll("/p&gt;", "");
                                        desc = desc.replaceAll("&quot;", "");


                                        feediitems.setDescription(desc);
                                        feediitems.setPubDate(feedItems.get(i).getPubDate());
                                        feediitems.setLink(feedItems.get(i).getLink());
                                        Log.d("Muhib", desc);
                                        feedintems.add(feediitems);
                                        if (i == 0) {
                                            mainfeed.add(feediitems);
                                        }
                                    }
                                    myAdapter.notifyDataSetChanged();
                                    Utility.hidepopup();
                                }
                            } else {
                                parsemwithxml();
                                Utility.message(getContext(),"Please Wait...");
                                //     Toast.makeText(getContext(), "" + response.body().status + "\n" + response.body().message, Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            parsemwithxml();
                        }
                    }
                    catch (Exception e)
                    {
                        Crashlytics.logException(e);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Utility.hidepopup();
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Utility.message(getContext(), "No Internet connection found");
        }
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
                        title = title.replaceAll("&rsquo;", "");
                        title = title.replaceAll("&mdash;", "");
                        title = title.replaceAll("&lt;", "");
                        title = title.replaceAll("/p&gt;", "");
                        title = title.replaceAll("&quot;", "");
                        title = title.replaceAll("&#038;", "");
                        title = title.replaceAll("&#", "");
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
                  /*  String content=list.get(i).getContent();
                    Pattern pattern1 = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);

                    Matcher urlMatcher1 = pattern1.matcher(content);
                    while (urlMatcher1.find()) {
                        String url = content.substring(urlMatcher.start(0),
                                urlMatcher.end(0));

                        if (url.contains(".jpg")) {
                            feediitems.setImagelink(url);
                            break;
                        }
                    }*/
                        String image=list.get(i).getImage();
                        // String image1=list.get(i).get;
                        if(image!=null )
                            if(!image.isEmpty())
                            {
                                feediitems.setImagelink(image);
                            }
                   /* else if(image1!=null || !image1.isEmpty())
                    {
                        feediitems.setImagelink(image1);
                    }*/
                        //    feediitems.setImagelink(list.get(i).getImage());
                        desc = desc.replaceAll("<(.*?)>", "");//Removes all items in brackets
                        desc = desc.replaceAll("&ldpos;", "");
                        desc = desc.replaceAll("&ldquo;", "");
                        desc = desc.replaceAll("&nbsp;", "");
                        desc = desc.replaceAll("&amp;", "");
                        desc = desc.replaceAll("&rdpos;", "");
                        desc = desc.replaceAll("&rdquo;", "");
                        desc = desc.replaceAll("&rsqvo;", "");
                        desc = desc.replaceAll("&rsquo;", "");
                        desc = desc.replaceAll("&mdash;", "");
                        desc = desc.replaceAll("&lt;", "");
                        desc = desc.replaceAll("/p&gt;", "");
                        desc = desc.replaceAll("&quot;;", "");

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
                }
                catch (Exception e)
                {
                    Utility.message(getContext(),"Network Error");
                    Crashlytics.logException(e);
                }

            }

            @Override
            public void onError() {

                Utility.hidepopup();
                Utility.message(getActivity(),"Error");
                Log.d("Muhib","Error");
            }
        });
    }


    @Override
    public void click(View view, int Position) {
        // Toast.makeText(getContext(),"Hello",Toast.LENGTH_LONG).show();
        String link=feedintems.get(Position).getLink();
        String title=feedintems.get(Position).getTitle();
        Intent it= new Intent(getContext(), WebActivity.class);
        it.putExtra("url",link);
        it.putExtra("title",title);
        Log.d("Muhib",link);
        startActivity(it);
    }

    @Override
    public void onDetach() {

        super.onDetach();
    }

    @Override
    public void onRefresh() {
        preapredata();
        refreshLayout.setRefreshing(false);
    }
    /*ApiList retrofit = RetrofitBuilder.retrofitapisxml();
    Call<Feed> feedCall=retrofit.getitems();
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

}
