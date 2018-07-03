package com.barbarakoduzi.patrolapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barbarakoduzi.patrolapp.Models.Gjobe;
import com.barbarakoduzi.patrolapp.R;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
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
        Gjobe gjobe = gjobat.get(position);

        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateFillimi = formatter.format(gjobe.getDataVenies());
        String dateMbarimi = formatter.format(gjobe.getAfatiPerfundimtar());


        holder.gjoba_titull.setText(gjobe.getTarga());
        holder.pershkrim.setText(gjobe.getLloji());
        holder.arsyeja.setText(gjobe.getArsyeja());
        holder.date_fillimi.setText(dateFillimi);
        holder.date_mbarimi.setText(dateMbarimi);
        holder.vlera.setText(gjobe.getVlera());
        holder.piket.setText(gjobe.getPiketUlura());
        holder.lloji.setText(gjobe.getLloji());

        if(gjobe.isPaguar()){
            holder.paguar.setBackgroundColor(ctx.getResources().getColor(R.color.primary));
            holder.paguarText.setText("PAGUAR");
        }

        {
            holder.paguar.setBackgroundColor(ctx.getResources().getColor(R.color.colorAccent));
            holder.paguarText.setText("PAPAGUAR");
        }


    }

    @Override
    public int getItemCount() {
        return gjobat.size();
    }



    public class GjobeViewHolder extends RecyclerView.ViewHolder{

        private TextView gjoba_titull, date_fillimi, date_mbarimi, vlera, pershkrim, lloji, piket, arsyeja, paguarText;
        private LinearLayout paguar;

        public GjobeViewHolder(View itemView) {
            super(itemView);
            gjoba_titull = itemView.findViewById(R.id.gjoba_titull);
            date_fillimi = itemView.findViewById(R.id.gjoba_date_fillimi);
            date_mbarimi = itemView.findViewById(R.id.gjoba_date_mbarimi);
            vlera = itemView.findViewById(R.id.gjoba_arsyeja);
            paguar = itemView.findViewById(R.id.paguar);
            pershkrim = itemView.findViewById(R.id.gjoba_pershkrim);
            lloji =itemView.findViewById(R.id.gjoba_lloji);
            piket  = itemView.findViewById(R.id.gjoba_piket_ulur);
            arsyeja = itemView.findViewById(R.id.gjoba_arsyeja);
            paguarText = itemView.findViewById(R.id.paguarText);
        }
    }

    public void setGjobat(List<Gjobe> gjobat) {
        this.gjobat = gjobat;
        this.notifyDataSetChanged();
    }
}
