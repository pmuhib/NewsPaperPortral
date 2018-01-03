package com.rozkhabardar.newspaperportral.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.adapter.LatestNewsAdapter;
import com.rozkhabardar.newspaperportral.adapter.MyAdapter;
import com.rozkhabardar.newspaperportral.models.Items;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.fabric.sdk.android.Fabric;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class SearchNews extends Fragment implements MyAdapter.Clickable {
    View view;
    RecyclerView recyclerView;
    LatestNewsAdapter myAdapter;
    ArrayList<Items> feedintems = new ArrayList<Items>();
    List<String> links = new ArrayList<String>();
    String name, url, Apikey;
    ArrayList<Items> feedintemsAc = new ArrayList<Items>();
    int m=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.searchnews, container, false);
        Fabric.with(getActivity(), new Crashlytics(), new CrashlyticsNdk());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyler);
        links = Arrays.asList(getResources().getStringArray(R.array.latestnews));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar.setTitle(getResources().getString(R.string.action_search));
        setuplatestnews();
        if(myAdapter==null)
        {
            myAdapter = new LatestNewsAdapter(getContext(), feedintems, "Search News");
            recyclerView.setAdapter(myAdapter);
            myAdapter.submit(this);

        }
        else
        {
            myAdapter.notifyDataSetChanged();
        }

    }

    private void setuplatestnews() {

        for (int i = 0; i < links.size(); i++) {
            url = links.get(i);
            if (Utility.checknetwork(getContext()))
            {
                Utility.showpop(getActivity());
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
                                desc = desc.replaceAll("&mdash;", "");
                                desc = desc.replaceAll("&lt;", "");
                                desc = desc.replaceAll("/p&gt;", "");

                                feediitems.setDescription(desc);
                                Date date=list.get(i).getPubDate();

                                feediitems.setPubDate(String.valueOf(date));
                                feediitems.setLink(list.get(i).getLink());
                                Log.d("Muhib", desc);
                                feedintems.add(feediitems);
                                //     Toast.makeText(getContext(),""+title,Toast.LENGTH_LONG).show();
                                //   Log.d("title",title);
                                if(i==0)
                                {
                                    //mainfeed.add(feediitems);
                                }
                            }
                        /*   Collections.sort(feedintems, new Comparator<Items>() {
                                @Override
                                public int compare(Items I1, Items I2) {
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date1;
                                    Date date2;
                                    try {
                                        date1=format.parse(I1.getPubDate().split(" ")[0]);
                                        date2=format.parse(I2.getPubDate().split(" ")[0]);
                                        //  Utility.message(getContext(),"Welcome");
                                    }
                                    catch (Exception e)
                                    {
                                        throw new IllegalArgumentException("Could not parse date!", e);
                                    }
                                    return (I2.getPubDate()).compareTo(I1.getPubDate());
                                }
                            });*/
                            m=m+1;
                            Utility.message(getContext(), "" + m);
                            Log.d("Number", "" + m);

                            if(m>10) {

                                Collections.sort(feedintems, new Comparator<Items>() {
                                    @Override
                                    public int compare(Items I1, Items I2) {
                                       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date1;
                                        Date date2;
                                        try {
                                            date1=format.parse(I1.getPubDate().split(" ")[0]);
                                            date2=format.parse(I2.getPubDate().split(" ")[0]);
                                            //  Utility.message(getContext(),"Welcome");
                                        }
                                        catch (Exception e)
                                        {
                                            throw new IllegalArgumentException("Could not parse date!", e);
                                        }
                                       */ return (I2.getPubDate()).compareTo(I1.getPubDate());
                                    }
                                });
                                Utility.hidepopup();
                                myAdapter.notifyDataSetChanged();
                            }
                        }
                        catch (Exception e)
                        {
                            Log.d("Error",""+e);
                            Crashlytics.logException(e);

                            //              Utility.message(getContext(),"Network Error");
                        }
                    finally {

                        }
                    }

                    @Override
                    public void onError() {
                    //    Utility.hidepopup();
//                        Utility.message(getActivity(),"Error");
                        Log.d("Muhib","Error");
                    }
                });
            }
            else
                {
                Utility.message(getContext(), "No Internet connection found");
            }
        }

    }

    @Override
    public void click(View view, int Position) {

    }
}


