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
    import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    import java.text.Format;
    import java.text.SimpleDateFormat;
    import java.util.List;
    import java.util.Map;

    public class GjobatShoferAdapter extends  RecyclerView.Adapter<GjobatShoferAdapter.GjobeViewHolder> {

            private List<Gjobe> gjobat;
            private Context ctx;
            private boolean paguar;
            private Map<String,Gjobe> gjobeMap;

            private DatabaseReference gjobatRef;
            private FirebaseDatabase database;

       public GjobatShoferAdapter(Context ctx, List<Gjobe> gjobat, Map<String, Gjobe> gjobeMap){
            this.ctx = ctx;
            this.gjobat = gjobat;
            this.paguar =paguar;
            this.gjobeMap = gjobeMap;
            database =FirebaseDatabase.getInstance();
            gjobatRef = database.getReference(CodesUtil.REFERENCE_GJOBAT);
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
            final Gjobe gjobe = gjobat.get(position);
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateFillimi = formatter.format(gjobe.getDataVenies());
            String dateMbarimi = formatter.format(gjobe.getAfatiPerfundimtar());
            holder.gjoba_titull.setText(gjobe.getTarga());
            holder.arsyeja.setText(gjobe.getArsyeja());
            holder.date_fillimi.setText(dateFillimi);
            holder.date_mbarimi.setText(dateMbarimi);
            holder.vlera.setText(gjobe.getVlera());
            holder.piket.setText(gjobe.getPiketUlura());
            holder.lloji.setText(gjobe.getLloji());

            if(gjobe.isPaguar()){
            holder.paguar.setBackgroundColor(ctx.getResources().getColor(R.color.primary));
            holder.paguarText.setText(" E PAGUAR");
            }
            else
            {
            holder.paguar.setBackgroundColor(ctx.getResources().getColor(R.color.colorAccent));
            holder.paguarText.setText("PAGUAJ");
            }

            holder.paguar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //logjika per pagesen e gjobes
                String gjobeId = ktheIdEGjobesNgaGjoba(gjobe);
                gjobe.setPaguar(true);
                gjobatRef.child(gjobeId).setValue(gjobe);
                }
            });

            }

    @Override
    public int getItemCount() {
            return gjobat.size();
            }



            private String ktheIdEGjobesNgaGjoba(Gjobe gjobe){
            for(String key : gjobeMap.keySet()){
                if(gjobeMap.get(key).equals(gjobe)){
                    return key;
                }
            }
            return "veprim_I_Gabuar";
            }


    public class GjobeViewHolder extends RecyclerView.ViewHolder{

        private TextView gjoba_titull, date_fillimi, date_mbarimi, vlera, lloji, piket, arsyeja, paguarText;
        private LinearLayout paguar;

        public GjobeViewHolder(View itemView) {
            super(itemView);
            gjoba_titull = itemView.findViewById(R.id.gjoba_titull);
            date_fillimi = itemView.findViewById(R.id.gjoba_date_fillimi);
            date_mbarimi = itemView.findViewById(R.id.gjoba_date_mbarimi);
            vlera = itemView.findViewById(R.id.gjoba_leke);
            paguar = itemView.findViewById(R.id.paguar);
            lloji =itemView.findViewById(R.id.gjoba_lloji);
            piket  = itemView.findViewById(R.id.gjoba_piket_ulur);
            arsyeja = itemView.findViewById(R.id.gjoba_arsyeja);
            paguarText = itemView.findViewById(R.id.paguarText);
        }


    }

        public void setGjobat(List<Gjobe> gjobat, Map<String, Gjobe> gjobeMap) {
            this.gjobat = gjobat;
            this.gjobeMap = gjobeMap;
            this.notifyDataSetChanged();
        }


    }