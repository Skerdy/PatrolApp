package com.barbarakoduzi.patrolapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.barbarakoduzi.patrolapp.Models.Gjobe;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class VerGjobeActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private TextView data_mbarimit, targa;

    private EditText lloji, arsyeja, piket_ulura, vlera;

    private ImageButton kalendar;

    private Gjobe gjobe;

    private String idShofer, idPolic, targaTxt;

    private String llojiGjobes, arsyejaTxt, pikeUlur, vleratxt;

    private Date data;
    private Button verGjobe;
    private FirebaseDatabase database;
    private DatabaseReference gjobat;
    private DatabaseReference shoferRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_gjobe);

        database = FirebaseDatabase.getInstance();
        shoferRef = database.getReference(CodesUtil.REFERENCE_SHOFER);
        idPolic = getIntent().getStringExtra(CodesUtil.POLIC_ID);
        idShofer = getIntent().getStringExtra(CodesUtil.SHOFER_ID);
        targaTxt = getIntent().getStringExtra(CodesUtil.TARGA);

        setupViews();

        verGjobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    gjobe = new Gjobe(idPolic,idShofer,llojiGjobes,pikeUlur,arsyejaTxt,vleratxt, new Date(),data, targaTxt, false);
                    gjobat = database.getReference(CodesUtil.REFERENCE_GJOBAT);
                    gjobat.push().setValue(gjobe);
                    shoferRef.child(idShofer).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Shofer shofer = dataSnapshot.getValue(Shofer.class);
                            shofer.zbritPiketEPatentesMe(Integer.parseInt(pikeUlur));
                            shoferRef.child(idShofer).setValue(shofer);

                            Intent intent = new Intent(VerGjobeActivity.this, PolicActivity.class );
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        });


    }

    private void setupViews(){

        data_mbarimit = findViewById(R.id.data_mbarimit);
        targa =findViewById(R.id.ver_gjobe_targe);
        targa.setText(targaTxt);
        lloji = findViewById(R.id.ver_gjobe_lloji);
        arsyeja = findViewById(R.id.ver_gjobe_arsyeja);
        piket_ulura =findViewById(R.id.ver_gjobe_piket);
        vlera = findViewById(R.id.ver_gjobe_vlera);
        kalendar =findViewById(R.id.kalendar);
        verGjobe = findViewById(R.id.ver_gjobe);

        Calendar now = Calendar.getInstance();
        final DatePickerDialog dpd = DatePickerDialog.newInstance(
                VerGjobeActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        kalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show(getFragmentManager(), "Zgjidhni nje date: ");
            }
        });

    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = "Ju zgjodhet daten "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        data = calendar.getTime();
        data_mbarimit.setText(date);
        data_mbarimit.setTextColor(getResources().getColor(R.color.black));
    }

    public boolean validate() {
         boolean valid = true;

         llojiGjobes = lloji.getText().toString();
         arsyejaTxt = arsyeja.getText().toString();
         pikeUlur = piket_ulura.getText().toString();
         vleratxt = vlera.getText().toString();



        if (llojiGjobes.isEmpty()) {
            lloji.setError("Fusni një te dhene");
            valid = false;
        } else {
            lloji.setError(null);
        }

        if (arsyejaTxt.isEmpty()) {
            arsyeja.setError("Fusni një te dhene");
            valid = false;
        } else {
            arsyeja.setError(null);
        }


        if (pikeUlur.isEmpty()) {
            piket_ulura.setError("Fusni një te dhene");
            valid = false;
        } else {
            piket_ulura.setError(null);
        }

        if (vleratxt.isEmpty()) {
            vlera.setError("Fusni një te dhene");
            valid = false;
        } else {
            vlera.setError(null);
        }

        if(data==null){
            data_mbarimit.setTextColor(getResources().getColor(R.color.md_red_50));
            valid = false;
        }
        return valid;
    }
}
