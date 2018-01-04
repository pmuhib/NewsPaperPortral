package com.rozkhabardar.newspaperportral.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.models.Items;
import com.rozkhabardar.newspaperportral.webservices.ApiList;
import com.rozkhabardar.newspaperportral.webservices.ApiResponse;
import com.rozkhabardar.newspaperportral.webservices.RetrofitBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import io.fabric.sdk.android.Fabric;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.mainfeed;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AndroidBmService extends Service {
    List<String> links = new ArrayList<String>();
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.ask.service.displayevent";
    private final Handler handler = new Handler();
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/xml");
    ArrayList<Items> feedintems=new ArrayList<Items>();
    Intent intent;
    int counter = 0;
    String url,Apikey;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent=new Intent(BROADCAST_ACTION);
        Apikey="c945ddexcq3c2jtakiztsgzpayvnfsidwela0njo";
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        handler.removeCallbacks((sendUpdatesToUI));
        handler.postDelayed(sendUpdatesToUI,1000);
    }
    private Runnable sendUpdatesToUI=new Runnable()
    {
        @Override
        public void run() {
            getnews();
            handler.postDelayed(this,1000);
        }
    };

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
    }

    public void getnews() {
        links = Arrays.asList(getResources().getStringArray(R.array.latestnews));
        final OkHttpClient okHttpClient = new OkHttpClient();
        for (int i = 0; i < links.size(); i++)
        {
            url = links.get(i);
            final Request request=new Request.Builder().url(url).build();
            okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    String msg = e.getMessage();
                    Log.d("TAG", "onFailure: "+msg);
                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    String msg = response.body().string();
                  //  Log.d("TAG", "onFailure: "+msg);
                    XmlToJson xmlToJson = new XmlToJson.Builder(msg).build();
                    // convert to a JSONObject
                    JSONObject jsonObject = xmlToJson.toJson();
                    try {
                        JSONObject jsonObject1=jsonObject.getJSONObject("rss");
                        JSONObject jsonObject2=jsonObject1.getJSONObject("channel");

                            JSONArray items=jsonObject2.getJSONArray("item");
                            for(int i=0;i<items.length();i++)
                            {
                                JSONObject item=items.getJSONObject(i);
                                Items feediitems = new Items();
                                String title = item.getString("title");

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
                                String desc = item.getString("description");
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
                                String content =item.getString("content");
                                Matcher urlMatcher1 = pattern.matcher(content);
                                while (urlMatcher.find()) {
                                    String url = content.substring(urlMatcher.start(0),
                                            urlMatcher.end(0));

                                    if (url.contains(".jpg")) {
                                        feediitems.setImagelink(url);
                                        break;
                                    }
                                }
                              /*  String image = feedItems.get(i).getThumbnail();
                                String image1 = feedItems.get(i).getImagelink();
                                if (image != null || !image.isEmpty()) {
                                    feediitems.setImagelink(image);
                                } else if (image1 != null || !image1.isEmpty()) {
                                    feediitems.setImagelink(image1);
                                }*/

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
                                feediitems.setPubDate(item.getString("pubDate"));
                                feediitems.setLink(item.getString("link"));
                                Log.d("Muhib", desc);
                                feedintems.add(feediitems);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    intent.putExtra("bundle",feedintems);
                    sendBroadcast(intent);
                }
            });
        }
    }
}
