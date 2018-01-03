package com.rozkhabardar.newspaperportral.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rozkhabardar.newspaperportral.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class NewslistAdapter extends RecyclerView.Adapter<NewslistAdapter.ViewHolder> {
    List<String> list=new ArrayList<>();

    public NewslistAdapter(List<String> list) {
        this.list = list;
    }
    Clickable clicks;



    public interface Clickable
    {
        void clickitem(View v,int postion);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.newspaperlistrow,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.listitem.setText(list.get(position));

    }
    public void submit(Clickable clicks)
    {
        this.clicks=clicks;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listitem;
        public ViewHolder(View itemView) {
            super(itemView);
            listitem= (TextView) itemView.findViewById(R.id.txt_newspapers);
            listitem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clicks!=null)
            {
                clicks.clickitem(v,getPosition());
            }
        }
    }
}
