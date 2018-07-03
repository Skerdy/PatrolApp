package com.barbarakoduzi.patrolapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barbarakoduzi.patrolapp.Models.Gjobe;
import com.barbarakoduzi.patrolapp.R;

import java.util.List;

public class GjobatAdapter extends RecyclerView.Adapter<GjobatAdapter.GjobeViewHolder> {

    private List<Gjobe> gjobat;
    private Context ctx;

    public GjobatAdapter(Context ctx, List<Gjobe> gjobat){
        this.ctx = ctx;
        this.gjobat = gjobat;
    }



    @NonNull
    @Override
    public GjobeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gjobe_item, parent, false);
        return  new GjobeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GjobeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return gjobat.size();
    }



    public class GjobeViewHolder extends RecyclerView.ViewHolder{

        public GjobeViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setGjobat(List<Gjobe> gjobat) {
        this.gjobat = gjobat;
        this.notifyDataSetChanged();
    }
}
