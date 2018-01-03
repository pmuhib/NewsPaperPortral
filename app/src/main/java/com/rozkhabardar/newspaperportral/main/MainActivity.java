package com.rozkhabardar.newspaperportral.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.adapter.MyAdapter;
import com.rozkhabardar.newspaperportral.models.Items;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyAdapter.Clickable, SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    MyAdapter myAdapter;
    ArrayList<Items> feedintems=new ArrayList<Items>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= (RecyclerView) findViewById(R.id.recyler);
        refreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        myAdapter=new MyAdapter(getApplicationContext(),feedintems,"MainActivity");
        myAdapter.submit(this);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        preapredata();

    }
    private void preapredata() {
        if(feedintems!=null) {
        feedintems.clear();
        }
        Utility.showpop(this);
       // ApiList retrofit= RetrofitBuilder.retrofitapis("http://www.dailyexcelsior.com");
      /*  Call<Feed> feedCall=retrofit.getitems();
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
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        });*/
    }

    @Override
    public void click(View view, int Position) {
        Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {

        preapredata();
        refreshLayout.setRefreshing(false);
    }
}
