package com.barbarakoduzi.patrolapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.barbarakoduzi.patrolapp.Activities.VerGjobeActivity;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public class ShoferAdapter extends RecyclerView.Adapter<ShoferAdapter.ShoferViewHolder> {
    private List<Shofer> shoferList;
    private Context ctx;
    private String policeId;
    private Map<String,Shofer> shoferMap;

    public ShoferAdapter (Context ctx , List<Shofer> shoferList, String policeId){
        this.ctx = ctx;
        this.shoferList = shoferList;
        this.policeId = policeId;
    }

    @NonNull
    @Override
    public ShoferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shofer_item, parent, false);
        return  new ShoferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoferViewHolder holder, int position) {
        final Shofer shofer = shoferList.get(position);
        holder.targa.setText(shofer.getTarga());
        holder.piketPatente.setText(shofer.getPikePatente());
        holder.verGjobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idShofer = getShoferKeyClicked(shofer.getTarga());
                Intent intent = new Intent(ctx, VerGjobeActivity.class);
                intent.putExtra(CodesUtil.SHOFER_ID, idShofer);
                intent.putExtra(CodesUtil.POLIC_ID, policeId);
                intent.putExtra(CodesUtil.TARGA, shofer.getTarga());
                ctx.startActivity(intent);
            }
        });
    }

    private String getShoferKeyClicked(String targa){
        for (String key : shoferMap.keySet()) {
            if(targa.equals(shoferMap.get(key).getTarga())){
                return key;
            }


        }
        return "null";
    }

    @Override
    public int getItemCount() {
        return shoferList.size();
    }

    public class  ShoferViewHolder extends RecyclerView.ViewHolder {

        private TextView piketPatente;
        private TextView targa;
        private Button verGjobe;


        public ShoferViewHolder(View itemView) {
            super(itemView);
            piketPatente = itemView.findViewById(R.id.shofer_piket);
            targa = itemView.findViewById(R.id.shofer_targa);
            verGjobe = itemView.findViewById(R.id.shofer_vendos_gjobe);

        }
    }

    public void setShoferList(List<Shofer> shoferList) {
        this.shoferList = shoferList;
        this.notifyDataSetChanged();
    }

    public void setPoliceId(String policeId) {
        this.policeId = policeId;
    }

    public void setShoferMap(Map<String, Shofer> shoferMap) {
        this.shoferMap = shoferMap;
    }
}
