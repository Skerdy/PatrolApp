package com.barbarakoduzi.patrolapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.barbarakoduzi.patrolapp.Fragments.TutorialPane;
import com.barbarakoduzi.patrolapp.Fragments.TutorialPaneShofer;
import com.barbarakoduzi.patrolapp.Models.Perdorues;
import com.barbarakoduzi.patrolapp.Models.Polic;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.barbarakoduzi.patrolapp.Utils.MySharedPref;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class TutorialActivityShofer extends AppCompatActivity {
    static final int NUM_PAGES = 4;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private LinearLayout circles;
    private Button done;
    private ImageButton next;
    private FragmentTransaction fragmentTransaction;
    boolean isOpaque = true;

    private String emer = "";
    private String mbiemer = "";
    private String targa = "";
    private String piket = CodesUtil.PIKET_PATENTE_DEFAULT;

    private MySharedPref mySharedPref;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference perdoruesReference, shoferReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        TutorialPane tutorialPane = TutorialPane.newInstance(R.layout.activity_tutorial,-1);
        fragmentTransaction = getSupportFragmentManager().beginTransaction().add(tutorialPane,"pane");
        setContentView(R.layout.github_test);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mySharedPref = new MySharedPref(this);
        next = ImageButton.class.cast(findViewById(R.id.next));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem() + 1, true);
            }
        });

        done = Button.class.cast(findViewById(R.id.done));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                    endTutorial();
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //See note above for why this is needed
                if(position == NUM_PAGES - 1 && positionOffset > 0){
                    if(isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }

                } else {
                    if(!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.primary));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if(position == NUM_PAGES - 1){
                    next.setVisibility(View.GONE);
                    done.setVisibility(View.VISIBLE);
                }else if(position < NUM_PAGES - 1){
                    next.setVisibility(View.VISIBLE);
                    done.setVisibility(View.GONE);
                }else if(position == NUM_PAGES){
                    //ideja fillestare eshte qe ne scroll djathatas te perfundoje tutorial por e kam caktivizuar
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Unused
            }
        });

        buildCircles();
    }

    private boolean validate() {
        boolean flag =true;
        if(emer == null || emer.length()==0){
            Toast.makeText(this, "Ju lutem fusni nje emer", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(mbiemer == null || mbiemer.length()==0){
            if(flag)
                Toast.makeText(this, "Ju lutem fusni nje mbiemer", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        if(targa == null || targa.length()==0){
            if(flag)
                Toast.makeText(this, "Ju lutem fusni nje targe", Toast.LENGTH_SHORT).show();
            flag=false;
        }
        return flag;
    }

    /*
        The last fragment is transparent to enable the swipe-to-finish behaviour seen on Google's apps
        So our viewpager circle indicator needs to show NUM_PAGES - 1
     */
    private void buildCircles(){

        circles = LinearLayout.class.cast(findViewById(R.id.circles));
        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);
        for(int i = 0 ; i < NUM_PAGES - 1 ; i++){

            ImageView circle = new ImageView(this);
            circle.setImageResource(R.drawable.circle);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);

        }

        setIndicator(0);
    }

    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = (ImageView) circles.getChildAt(i);
                if(i == index){
                    circle.setImageResource(R.drawable.circle_selected);
                }else {
                    circle.setImageResource(R.drawable.circle);
                }
            }
        }
    }

    private void endTutorial(){
        Log.d("Skerdi", "po behet ruajtja");
        //shkruaj te dhenat e marra ne FirebaseDatabase per policin e loguar;
        perdoruesReference = database.getReference(CodesUtil.REFERENCE_PERDORUES).child(mAuth.getCurrentUser().getUid());
        shoferReference = database.getReference(CodesUtil.REFERENCE_SHOFER);
        final String shoferId = shoferReference.push().getKey();
        Shofer shofer = new Shofer(piket,targa);
        shofer.setFcm(FirebaseInstanceId.getInstance().getToken());
        shofer.setVleraPara("2000");

        shoferReference.child(shoferId).setValue(shofer);

        perdoruesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Perdorues perdorues = new Perdorues(Integer.parseInt(dataSnapshot.child("rol").getValue().toString()), dataSnapshot.child("email").getValue().toString());
                perdorues.setEmer(emer);
                perdorues.setMbiemer(mbiemer);
                perdorues.setIdProfil(shoferId);
                perdoruesReference.setValue(perdorues);
                Log.d("Skerdi", "U be ruajtja");
                Intent intent = new Intent(TutorialActivityShofer.this, ShoferActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            TutorialPaneShofer tp = null;
            switch(position){
                case 0:
                    tp = TutorialPaneShofer.newInstance(R.layout.fragment_tutorial_one,0);
                    break;
                case 1:
                    tp = TutorialPaneShofer.newInstance(R.layout.fragment_tutorial_two,1);
                    break;
                case 2:
                    tp = TutorialPaneShofer.newInstance(R.layout.fragment_tutorial_three_shofer,2);
                    break;
                case 3:
                    tp = TutorialPaneShofer.newInstance(R.layout.fragment_tutorial_four,3);
                    break;
            }
            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
        }
    }

    public void setEmer(String emer) {
        this.emer = emer;
    }

    public void setMbiemer(String mbiemer) {
        this.mbiemer = mbiemer;
    }


    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getPiket() {
        return piket;
    }

    public void setPiket(String piket) {
        this.piket = piket;
    }
}
