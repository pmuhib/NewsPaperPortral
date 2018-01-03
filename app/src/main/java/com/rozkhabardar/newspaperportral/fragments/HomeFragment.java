package com.rozkhabardar.newspaperportral.fragments;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.CompareItems;
import com.rozkhabardar.newspaperportral.activities.WebActivity;
import com.rozkhabardar.newspaperportral.adapter.MyAdapter;
import com.rozkhabardar.newspaperportral.models.Items;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import io.fabric.sdk.android.Fabric;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.mainfeed;
import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.toolbar;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class HomeFragment extends Fragment implements MyAdapter.Clickable {
    View view;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
        Set<Items> itemset;
    ArrayList<Items> newitems;
    LinearLayoutManager layoutManager;
    TextView topheadlines;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.homefrag,container,false);
        Fabric.with(getActivity(), new Crashlytics(), new CrashlyticsNdk());
        topheadlines= (TextView) view.findViewById(R.id.txt_topheadlines);
        newitems=new ArrayList<>();
        itemset=new TreeSet<Items>(new CompareItems());
      /*  itemset=new HashSet<>();
        itemset.addAll(mainfeed);
        mainfeed.clear();
        mainfeed.addAll(itemset);*/

        for(Items i:mainfeed)
        {
            topheadlines.setVisibility(View.VISIBLE);
            if(!itemset.add(i))
            {
                newitems.add(i);
            }
        }
        mainfeed.clear();
        mainfeed.addAll(itemset);
        recyclerView= (RecyclerView) view.findViewById(R.id.main_recy);
        myAdapter=new MyAdapter(getContext(),mainfeed,"HomeFragment");
        myAdapter.submit(this);
        layoutManager=new LinearLayoutManager(getContext());

       /* if(mainfeed.size()<1)
        {
          layoutManager=new LinearLayoutManager(getContext());

        }
        else
        {
         layoutManager=new LinearLayoutManager(getActivity())
            {
                @Override
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {

                    LinearSmoothScroller smoothScroller=new LinearSmoothScroller(getActivity())
                    {
                        private static final float SPEED = 1000f;// Change this                value (default=25f)

                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return SPEED/displayMetrics.densityDpi;
                        }
                    };
                    smoothScroller.setTargetPosition(position);
                    startSmoothScroll(smoothScroller);

                }
            };
        }*/

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(myAdapter);
        setupimages();
       // getdata();


        return view;

    }

    private void back() {

    }

    @Override
    public void onResume() {
        super.onResume();

        toolbar.setTitle(R.string.app_name);
        autoScroll();
    }
    public void autoScroll(){
        final int speedScroll =5000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == myAdapter.getItemCount())
                    count =0;
                if(count <= myAdapter.getItemCount()){
                    recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this,speedScroll);
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

   /* private void getdata() {

        if(mainfeed!=null)
        {
            for(int i=0;i<mainfeed.size();i++)
            {
                Utility.message(getContext(),""+mainfeed.get(i).getTitle());
            }
        }
        else
        {
            Utility.message(getContext(),"Empty");
        }
    }*/

    private void setupimages()
    {
        AssetManager assetManager=getResources().getAssets();

        try {
            Random random=new Random();
            int indexImage=random.nextInt(6)+1;
            InputStream ims=assetManager.open(indexImage+".jpg");
            Drawable d=Drawable.createFromStream(ims,null);
            ImageView imageView= (ImageView) view.findViewById(R.id.img_photos);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void click(View view, int Position) {

        try {
            String link=mainfeed.get(Position).getLink();
            String title=mainfeed.get(Position).getTitle();
            Intent it= new Intent(getActivity(), WebActivity.class);
            it.putExtra("url",link);
            it.putExtra("title",title);
            Log.d("Muhib",link);
            startActivity(it);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }



}
