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
import com.barbarakoduzi.patrolapp.Models.PerdoruesShofer;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public class ShoferAdapter extends RecyclerView.Adapter<ShoferAdapter.ShoferViewHolder> {
    private List<PerdoruesShofer> shoferList;
    private Context ctx;
    private String policeId;
    private Map<String,Shofer> shoferMap;

    public ShoferAdapter (Context ctx , List<PerdoruesShofer> shoferList, String policeId){
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
        final PerdoruesShofer shofer = shoferList.get(position);
        holder.targa.setText(shofer.getShofer().getTarga());
        holder.piketPatente.setText(shofer.getShofer().getPikePatente());
        holder.emer.setText(shofer.getEmer());
        holder.mbiemer.setText(shofer.getMbiemer());
        holder.email.setText(shofer.getEmail());
        holder.verGjobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idShofer = getShoferKeyClicked(shofer.getShofer().getTarga());
                Intent intent = new Intent(ctx, VerGjobeActivity.class);
                intent.putExtra(CodesUtil.SHOFER_ID, idShofer);
                intent.putExtra(CodesUtil.POLIC_ID, policeId);
                intent.putExtra(CodesUtil.TARGA, shofer.getShofer().getTarga());
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

        private TextView piketPatente, emer, mbiemer, email;
        private TextView targa;
        private Button verGjobe;


        public ShoferViewHolder(View itemView) {
            super(itemView);
            piketPatente = itemView.findViewById(R.id.shofer_piket);
            targa = itemView.findViewById(R.id.shofer_targa);
            verGjobe = itemView.findViewById(R.id.shofer_vendos_gjobe);
            emer = itemView.findViewById(R.id.shofer_emer);
            mbiemer = itemView.findViewById(R.id.shofer_mbiemer);
            email = itemView.findViewById(R.id.shofer_email);

        }
    }

    public void setShoferList(List<PerdoruesShofer> shoferList) {
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
