package com.rozkhabardar.newspaperportral.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.models.Items;

import java.io.Serializable;
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

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AndroidBmService extends Service {
    List<String> links = new ArrayList<String>();
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.ask.service.displayevent";
    private final Handler handler = new Handler();
    ArrayList<Items> feedintems=new ArrayList<Items>();
    Intent intent;
    int counter = 0;
    String url;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent=new Intent(BROADCAST_ACTION);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

    }

    @Override
    public void onStart(Intent intent, int startId) {
        handler.removeCallbacks((sendUpdatesToUI));
        handler.postDelayed(sendUpdatesToUI,10000);
    }
    private Runnable sendUpdatesToUI=new Runnable()
    {
        @Override
        public void run() {
            getnews();
            handler.postDelayed(this,10000);
        }
    };

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }

    public void getnews() {
        links = Arrays.asList(getResources().getStringArray(R.array.latestnews));
        Bundle args = new Bundle();
        args.putSerializable("Slist", (Serializable) links);
        intent.putExtra("bundle",args);
        sendBroadcast(intent);
        /*for (int i = 0; i < links.size(); i++) {
            url = links.get(i);
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

                            String image=list.get(i).getImage();
                            // String image1=list.get(i).get;
                            if(image!=null )
                                if(!image.isEmpty())
                                {
                                    feediitems.setImagelink(image);
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
                            desc = desc.replaceAll("&gt;", "");

                            feediitems.setDescription(desc);
                            feediitems.setPubDate(String.valueOf(list.get(i).getPubDate()));
                            feediitems.setLink(list.get(i).getLink());
                            Log.d("Muhib", desc);
                            feedintems.add(feediitems);

                        }
                        Collections.sort(feedintems, new Comparator<Items>() {
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
                        });
                        Bundle args = new Bundle();
                        args.putSerializable("Slist",feedintems);
                        intent.putExtra("bundle",args);
                        sendBroadcast(intent);
                    }
                    catch (Exception e)
                    {
                        Crashlytics.logException(e);
                    }
                }

                @Override
                public void onError() {
                    Log.d("Muhib","Error");

                }
            });
        }*/
    }
}
