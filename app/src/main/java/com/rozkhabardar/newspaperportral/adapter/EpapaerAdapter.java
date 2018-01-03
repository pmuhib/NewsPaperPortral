package com.rozkhabardar.newspaperportral.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Utils.Utility;
import com.rozkhabardar.newspaperportral.models.EpaperItemModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class EpapaerAdapter extends RecyclerView.Adapter<EpapaerAdapter.Viewitem> {

    Context context;
    ArrayList<EpaperItemModel> itemsList;
   Clicks clickable;
    int lastpositon= -1;

    public EpapaerAdapter(Context context, ArrayList<EpaperItemModel> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    public interface Clicks
    {
        void click(View view,int Position);
    }
    @Override
    public Viewitem onCreateViewHolder(ViewGroup parent, int viewType) {
    View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.epaperrow,parent,false);
        return new Viewitem(row);
    }

    @Override
    public void onBindViewHolder(final Viewitem holder, int position) {
        EpaperItemModel items=itemsList.get(position);
        holder.name.setText(items.name);
        String url=items.imagelink;
        Glide.with(context).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.img.setImageResource(R.drawable.noimage);
                holder.bar.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.bar.setVisibility(View.GONE);
                return false;
            }
        }).into(holder.img);
        if (position>lastpositon) {
            Utility.animate(holder,true);
        }
        else
        {
            Utility.animate(holder,false);
        }
        lastpositon=position;

    }
    public void submit(Clicks clickable)
    {
        this.clickable=clickable;
    }
    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class Viewitem extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView name;
        ProgressBar bar;
        public Viewitem(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.epaper_photo);
            name= (TextView) itemView.findViewById(R.id.epaper_name);
            bar= (ProgressBar) itemView.findViewById(R.id.progressBar);
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
