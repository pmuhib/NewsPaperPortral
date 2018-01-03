package com.rozkhabardar.newspaperportral.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.activities.WebActivity;
import com.rozkhabardar.newspaperportral.adapter.EpapaerAdapter;
import com.rozkhabardar.newspaperportral.models.EpaperItemModel;

import java.util.ArrayList;
import java.util.Calendar;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class EpapersList extends Fragment implements TextView.OnEditorActionListener, EpapaerAdapter.Clicks {
    View view;
    RecyclerView recyclerView;
    EpapaerAdapter epaperlistAdapter;
    EditText etSearch;
    private GridLayoutManager lLayout;
    ArrayList<EpaperItemModel> itemsArrayList;
    String gkdates,aftabdates,language;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.newspaperlist,container,false);
        Fabric.with(getActivity(), new Crashlytics(), new CrashlyticsNdk());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Bundle bundle=getArguments();
        language=bundle.getString("Lang");
        recyclerView= (RecyclerView) view.findViewById(R.id.rec_newslist);
        lLayout=new GridLayoutManager(getActivity(),2);
         itemsArrayList=getItemsArrayList();
        etSearch= (EditText) view.findViewById(R.id.et_search);
        etSearch.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setuplist();

    }

    private void setuplist() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayout);
        epaperlistAdapter=new EpapaerAdapter(getContext(),itemsArrayList);
        epaperlistAdapter.submit(this);
        recyclerView.setAdapter(epaperlistAdapter);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
    private ArrayList<EpaperItemModel> getItemsArrayList()
    {
        String gkjammudate,convenerdate;
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int date=calendar.get(Calendar.DATE);
        convenerdate=String.valueOf(date)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
        if(date<10 )
        {
            gkjammudate="0"+String.valueOf(date)+String.valueOf(month)+String.valueOf(year);
            if(month<10)
            {
                gkjammudate="0"+String.valueOf(date)+"0"+String.valueOf(month)+String.valueOf(year);/*07092017*/
            }
        }
        else
        {
            gkjammudate=String.valueOf(date)+String.valueOf(month)+String.valueOf(year);
            if(month<10)
            {
                gkjammudate=String.valueOf(date)+"0"+String.valueOf(month)+String.valueOf(year);/*17092017*/
            }
        }
        gkdates= String.valueOf(date)+String.valueOf(month)+String.valueOf(year);  /*792017,7102017*/
        aftabdates=String.valueOf(date)+"_"+String.valueOf(month)+"_"+String.valueOf(year);  /*7_9_2017,7_10_2017*/
        ArrayList<EpaperItemModel> allitems=new ArrayList<>();
        try {
            if(language.equalsIgnoreCase("English"))
            {
                allitems.add(new EpaperItemModel("Greater Kashmir(Srinagar Edition)","http://epaper.greaterkashmir.com/epaperimages/"+gkdates+"/"+gkdates+"-md-hr-1l.jpg"));
                allitems.add(new EpaperItemModel("Greater Kashmir(Jammu Edition)","http://digitalpaper.ezinemart.com/greaterkasmirjammu/epaperimages/"+gkjammudate+"/page-1l.png"));
                allitems.add(new EpaperItemModel("Daily Kashmir Images","http://epaper.dailykashmirimages.com/"+gkdates+"/dpages/"+aftabdates+"_"+1+".jpg"));
                allitems.add(new EpaperItemModel("Mirror Of Kashmir","http://mirrorofkashmir.in/"+gkdates+"/dpages/"+aftabdates+"_1.jpg"));
                allitems.add(new EpaperItemModel("Kashmir Reader","http://epaper.kashmirreader.net/epaperimages//"+gkjammudate+"//"+gkjammudate+"-md-hr-1.jpg"));
                allitems.add(new EpaperItemModel("Kashmir Convener","http://epaper.kashmirconvener.com/imagesbkend/KC-"+convenerdate+"-1.jpg"));
                allitems.add(new EpaperItemModel("Early Times","http://epaper.earlytimes.in/epaperadmin/photos/large/47241511020172416.jpg"));
                allitems.add(new EpaperItemModel("Kashmir Times(Srinagar Edition)","http://srinagar.kashmirtimes.com/epaperadmin/photos/large/4464016810201740368.jpg"));
                allitems.add(new EpaperItemModel("Kashmir Times(Jammu Edition)","http://epaper.kashmirtimes.in/epaperadmin/photos/large/947016591020170853.jpg"));
                allitems.add(new EpaperItemModel("Brighter kashmir","http://epaper.brighterkashmir.com/PaperImages/1/ab57d4cd-f3c9-4091-8fcd-4b9d5baf6057.jpeg"));

                allitems.add(new EpaperItemModel("Kashmir Observer","file:///android_asset/observer.gif"));
                allitems.add(new EpaperItemModel("Rising Kashmir","file:///android_asset/rising.gif"));
                allitems.add(new EpaperItemModel("Kashmir Glory","file:///android_asset/glory.gif"));
                allitems.add(new EpaperItemModel("Journey Line News Paper","http://epaper.journeyline.in/epaperadmin/photos/large/715041710201749899.jpg"));
                allitems.add(new EpaperItemModel("Daily Kashmir Frontie","http://www.kashmirfrontier.com/"+gkdates+"/dpages/"+aftabdates+"_1.jpg"));


            }
            else if(language.equalsIgnoreCase("Urdu"))
            {
                allitems.add(new EpaperItemModel("Daily Aftab","http://www.epaper-dailyaftab.com/"+gkdates+"/dpages/"+aftabdates+"_1.jpg"));
                allitems.add(new EpaperItemModel("Kashmir Uzma","http://epaper.kashmiruzma.net/epaperimages/"+gkdates+"/"+gkdates+"-md-hr-1l.jpg"));
                allitems.add(new EpaperItemModel("Srinagar News","http://www.srinagarnews.org/"+gkdates+"/dpages/"+aftabdates+"_1.jpg"));
                allitems.add(new EpaperItemModel("Buland Kashmir","http://bulandkashmir.com/PaperImages/1/b3090964-76cb-40e8-8972-609e1de713d2.jpeg"));
                allitems.add(new EpaperItemModel("Daily Roshni","file:///android_asset/roshini.gif"));
                allitems.add(new EpaperItemModel("Srinagar Times","http://www.srinagartimes.net/pixs/page_files/2000%20X%203250/JPEG/1.jpg"));
                allitems.add(new EpaperItemModel("Daily Chattan","http://chattanonline.com/epaperadmin/photos/large/3994916110201748383.jpg"));
            }
            else if(language.equalsIgnoreCase("Kashmiri"))
            {
                allitems.add(new EpaperItemModel("Sangarmal","file:///android_asset/sangarmal.gif"));

            }
            else if(language.equalsIgnoreCase("Hindi"))
            {
                allitems.add(new EpaperItemModel("Dainik Kashmir Times","http://www.dainikkashmirtimes.com/epaperadmin/photos/large/43035162510201735336.jpg"));
                allitems.add(new EpaperItemModel("Jammu Prabhat","http://www.jammuprabhat.com/epaperadmin/photos/large/47632162510201732414.jpg"));

            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        return  allitems;
    }

    @Override
    public void click(View view, int Position) {
        try {
            Calendar calendar=Calendar.getInstance();
            int years=calendar.get(Calendar.YEAR);
            int months=calendar.get(Calendar.MONTH)+1;
            int dates=calendar.get(Calendar.DATE);
            String name=itemsArrayList.get(Position).name;
            Utility.message(getContext(),name);
            Intent it= new Intent(getActivity(), WebActivity.class);
            it.putExtra("title",name);


            switch (name)
            {
                /*English*/
                case "Greater Kashmir(Srinagar Edition)":
                        it.putExtra("url","http://epaper.greaterkashmir.com/");
                    break;
                case "Greater Kashmir(Jammu Edition)":
                it.putExtra("url","http://digitalpaper.ezinemart.com/greaterkasmirjammu/");
                break;
                case "Kashmir Convener":
                it.putExtra("url","http://epaper.kashmirconvener.com/");
                break;
                case "Brighter kashmir":
                it.putExtra("url","http://epaper.brighterkashmir.com/");
                break;
                case "Kashmir Observer":
                    it.putExtra("url","https://kashmirobserver.net/epaper?utm_source=nb&utm_medium=ko");
                    break;
                case "Mirror Of Kashmir":
                it.putExtra("url","http://mirrorofkashmir.in/"+gkdates+"/default.asp");
                break;
                case "Rising Kashmir":
                it.putExtra("url","http://epaper.risingkashmir.com/");
                break;
                case "Daily Kashmir Frontie":
                it.putExtra("url","http://www.kashmirfrontier.com/"+gkdates+"/default.asp");
                break;
                case "Kashmir Reader":
                it.putExtra("url","http://epaper.kashmirreader.net/");
                break;
                case "Early Times":
                it.putExtra("url","http://epaper.earlytimes.in/");
                break;
                case "Kashmir Times(Srinagar Edition)":
                it.putExtra("url","http://srinagar.kashmirtimes.com/index.aspx");
                break;
                case "Kashmir Times(Jammu Edition)":
                it.putExtra("url","http://epaper.kashmirtimes.in/");
                break;
                case "Kashmir Glory":
                    it.putExtra("url","http://epaper.kashmirglory.com/");
                    break;
                case "Daily Kashmir Images":
                    it.putExtra("url","http://epaper.dailykashmirimages.com/"+dates+months+years+"/default.asp");
                    break;
                case "Journey Line News Paper":
                    it.putExtra("url","http://epaper.journeyline.in/");
                    break;


                /*Urdu*/
                case "Daily Aftab":
                    it.putExtra("url","http://www.epaper-dailyaftab.com/"+dates+months+years+"/default.asp");
                    break;
                case "Kashmir Uzma":
                    it.putExtra("url","http://epaper.kashmiruzma.net/");
                    break;

                case "Buland Kashmir":
                    it.putExtra("url","http://bulandkashmir.com/");
                    break;
                case "Daily Roshni":
                    it.putExtra("url","http://www.dailyroshni.net/");
                    break;
                case "Srinagar Times":
                    it.putExtra("url","http://www.srinagartimes.net/index.html");
                    break;
                case "Srinagar News":
                it.putExtra("url","http://www.srinagarnews.org/"+gkdates+"/default.asp");
                break;
                case "Daily Chattan":
                    it.putExtra("url","http://www.chattanonline.com/index.aspx");
                    break;


                /* Kashmiri*/
                case "Sangarmal":
                    it.putExtra("url","http://sangarmal.com/EPaper.aspx");
                    break;

                /*Hindi*/
                case "Dainik Kashmir Times":
                it.putExtra("url","http://www.dainikkashmirtimes.com/");
                break;
                case "Jammu Prabhat":
                    it.putExtra("url","http://www.jammuprabhat.com/");
                    break;
            }
            startActivity(it);
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
    }
}
