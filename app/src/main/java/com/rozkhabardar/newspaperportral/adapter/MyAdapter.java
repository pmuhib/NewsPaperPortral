package com.rozkhabardar.newspaperportral.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.models.Items;
import com.rozkhabardar.newspaperportral.sharedpref.SharedPreference;
import com.bumptech.glide.Glide;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static com.rozkhabardar.newspaperportral.activities.NavigationDrawer.saveslist;
import static io.fabric.sdk.android.services.network.UrlUtils.urlEncode;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Viewholder>{
    ArrayList<Items> feeditemsList,savelis;
    Context context;
    Clickable clickable;
    int lastpositon= -1;
    String name;
    SharedPreference sharedPreference;
    public static String arrayName[]={"Braid","braid","Killed","killed"};
    CircleMenu circleMenu;
    public interface Clickable
    {
        void click(View view,int Position);
    }
    public MyAdapter(Context context,ArrayList<Items> feeditemsList,String name) {
        this.feeditemsList = feeditemsList;
        this.context=context;
        this.name=name;
        sharedPreference=new SharedPreference();
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.newsrow,parent,false);
        return new Viewholder(row);
    }

    @Override
    public void onBindViewHolder(final Viewholder holder, final int position) {

        final Items feeditems=feeditemsList.get(position);
        holder.title.setText(feeditems.getTitle());
        holder.description.setText(feeditems.getDescription());
        holder.pubdate.setText(feeditems.getPubDate());

        if(name.equalsIgnoreCase("Favourites"))
        {
            holder.btfav.setImageResource(android.R.drawable.ic_menu_delete);
            holder.btfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setCancelable(false);
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure you want to delete this item?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sharedPreference.removeFavorites(context,feeditems);
                            saveslist.remove(feeditems);
                            notifyDataSetChanged();
                            MyAdapter.this.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();


                }
            });
        }
        else
        {
            holder.btfav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkfavitem(feeditems))
                    {
                        holder.btfav.setImageResource(android.R.drawable.btn_star_big_off);
                        sharedPreference.removeFavorites(context,feeditems);
                        saveslist.remove(feeditems);
                        notifyDataSetChanged();
                        MyAdapter.this.notifyDataSetChanged();
                        Utility.message(context,"Removed From Favourites");

                    }
                    else
                    {
                        holder.btfav.setImageResource(android.R.drawable.btn_star_big_on);
                        sharedPreference.addFavorite(context,feeditems);
                        Utility.message(context,"Added to Favourites");
                    }

                }
            });

        }

       /* holder.circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.share,R.drawable.cross)
                .addSubMenu(Color.parseColor("#25bcff"),R.drawable.ic_menu_home)
                .addSubMenu(Color.parseColor("#25bcff"),R.drawable.ic_menu_gallery);
           holder.circleMenu.setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int i) {
                        Utility.message(context,"You");
                    }
                });*/
        String descr=feeditems.getDescription();

        for( int i=0;i<arrayName.length;i++)
        {
            if(descr.contains(arrayName[i]))
            {
                holder.backgrnd.setBackgroundColor(Color.parseColor("#8caf111c"));
            }
        }
/*        if(name.equalsIgnoreCase("JobFeeds"))
        {
            String link=feeditems.getLink();
            int index1=link.indexOf("www.");
            int index2=link.indexOf(".com");
            String nam=link.substring(index1,index2);
            holder.sitename.setVisibility(View.VISIBLE);
            holder.sitename.setText(nam);
        }*/
        holder.btshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Utility.message(context,"Coming Soon");
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                View view=LayoutInflater.from((context)).inflate(R.layout.sharepopup,null);

                circleMenu= (CircleMenu) view.findViewById(R.id.circle_menu);
                circleMenu.setMainMenu(Color.parseColor("#CDCDCD"),R.drawable.plus,R.drawable.cross)
                        .addSubMenu(Color.parseColor("#FFFFFF"),R.drawable.ic_whatsapp)
                        .addSubMenu(Color.parseColor("#FFFFFF"), R.drawable.ic_twitter)
                        .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                            @Override
                            public void onMenuSelected(int i) {
                                if (i == 0) {
                                    Utility.message(context, "Whatsapp");
                                    PackageManager pm = context.getPackageManager();
                                    try {
                                        Intent it = new Intent(Intent.ACTION_SEND);
                                        PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                                        it.setPackage("com.whatsapp");
                                        it.putExtra(Intent.EXTRA_TEXT, "Shared through Roz Khabardar App");
                                        it.putExtra(Intent.EXTRA_TEXT, feeditems.getTitle().toString());
                                        it.putExtra(Intent.EXTRA_TEXT, feeditems.getLink().toString());
                                        it.setType("text/plain");
                                        context.startActivity(Intent.createChooser(it, "Share with"));
                                    } catch (Exception e) {
                                        Utility.message(context, "WhatsApp not Installed");
                                    }
                                }
                                if (i == 1) {
                                    Utility.message(context, "Twitter");
                                    PackageManager pm = context.getPackageManager();
                                    try {
                                        String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s",
                                        urlEncode("Shared through Roz Khabardar App"),
                                                urlEncode(feeditems.getTitle().toString()),
                                                urlEncode(feeditems.getLink().toString()));
                                        Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

                                        PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                                        /*it.setPackage("com.whatsapp");
                                        it.putExtra(Intent.EXTRA_TEXT, "Shared through Roz Khabardar App");
                                        it.putExtra(Intent.EXTRA_TEXT, feeditems.getTitle().toString());
                                        it.putExtra(Intent.EXTRA_TEXT, feeditems.getLink().toString());
                                        it.setType("text/plain");
                                        context.startActivity(Intent.createChooser(it, "Share with"));*/
                                        context.startActivity(it);

                                        /*Intent it=new Intent(Intent.ACTION_SEND);
                                        PackageInfo info = pm.getPackageInfo("com.twitter.android", PackageManager.GET_META_DATA);
                                        it.setPackage("com.twitter.android");
                                        it.putExtra(Intent.EXTRA_TEXT, feeditems.getTitle().toString());
                                        //+"\n"+feeditems.getLink().toString()+"\n"+"Shared through Roz Khabardar App");
                                        it.putExtra(Intent.EXTRA_TEXT,feeditems.getLink().toString() );
                                       it.putExtra(Intent.EXTRA_TEXT, "Shared through Roz Khabardar App");
                                        it.setType("image*//*");

                                        context.startActivity(Intent.createChooser(it, "Share with"));*/
                                    } catch (Exception e) {
                                        Utility.message(context, "Twitter not Installed");
                                    }
                                }
                            }
                });
                setupshare();
                builder.setView(view);
                builder.setCancelable(true);
                Dialog dialog=builder.create();
                dialog.show();
            }
        });
        if(!name.equalsIgnoreCase("Favourites")) {

            if (checkfavitem(feeditems)) {
                holder.btfav.setImageResource(android.R.drawable.star_big_on);
            } else {
                holder.btfav.setImageResource(android.R.drawable.star_big_off);

            }
        }
        String url=feeditems.getImagelink();
        if(url!=null) {
            if (!url.isEmpty()) {
                Glide.with(context).load(url).into(holder.img);
            }
        }
        else
        {
            Glide.with(context).load(R.drawable.newspaper).into(holder.img);

        }
        if (position>lastpositon) {
            Utility.animate(holder,true);
        }
        else
        {
            Utility.animate(holder,false);
        }
        lastpositon=position;
    }



    private void setupshare() {

    }

    public boolean checkfavitem(Items checkitem)
        {
            boolean check=false;
            List<Items> itemsList=sharedPreference.getFavorites(context);
            if(itemsList!=null)
            {
                for (Items items:itemsList)
                {
                    if(items.getLink().equalsIgnoreCase(checkitem.getLink()))
                    {
                        check=true;
                        break;
                    }
                }
            }
            return check;
        }

    public void submit(Clickable clickable)
    {
        this.clickable=clickable;
    }
    @Override
    public int getItemCount() {
        return feeditemsList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout backgrnd;
        TextView title,description,pubdate,sitename;
        ImageView img;
        ImageView btfav,btshare;
        CircleMenu circleMenu;;
        public Viewholder(View itemView) {
            super(itemView);
            backgrnd= (LinearLayout) itemView.findViewById(R.id.ll_back);
            title= (TextView) itemView.findViewById(R.id.txt_title);
         //  sitename= (TextView) itemView.findViewById(R.id.sitename);
            description= (TextView) itemView.findViewById(R.id.txt_desc);
            pubdate= (TextView) itemView.findViewById(R.id.txt_pubdate);
            img= (ImageView) itemView.findViewById(R.id.img_news);
            btfav= (ImageView) itemView.findViewById(R.id.txtfav);
           btshare= (ImageView) itemView.findViewById(R.id.txtshar);
            circleMenu= (CircleMenu) itemView.findViewById(R.id.circle_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickable!=null)
            {
                clickable.click(v,getPosition());
            }
        }
    }

}

