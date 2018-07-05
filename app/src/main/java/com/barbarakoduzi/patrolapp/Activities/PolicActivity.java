package com.barbarakoduzi.patrolapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.barbarakoduzi.patrolapp.Fragments.GjobatFragment;
import com.barbarakoduzi.patrolapp.Fragments.HomePolic;
import com.barbarakoduzi.patrolapp.Fragments.ListaShoferFragment;
import com.barbarakoduzi.patrolapp.Models.PerdoruesPolic;
import com.barbarakoduzi.patrolapp.Models.PerdoruesShofer;
import com.barbarakoduzi.patrolapp.Models.Polic;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class PolicActivity extends AppCompatActivity {


    private Drawer result;
    private Toolbar toolbar;
    private AccountHeader headerResult;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private DatabaseReference  perdoruesRef, loggedUserProfileReference;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private PerdoruesPolic perdoruesPolic;
    private boolean bejVeprim = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupFirebaseAndGetCurrentUser();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FirebaseID", refreshedToken);
    }

    private void setupFirebaseAndGetCurrentUser(){

        auth =FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();
        perdoruesRef =database.getReference(CodesUtil.REFERENCE_PERDORUES);
        perdoruesRef.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final String emer = dataSnapshot.child("emer").getValue().toString();
                final String mbiemer = dataSnapshot.child("mbiemer").getValue().toString();
                final Integer rol = Integer.parseInt(dataSnapshot.child("rol").getValue().toString());
                final String profileId = dataSnapshot.child("idProfil").getValue().toString();
                final String email = dataSnapshot.child("email").getValue().toString();

                loggedUserProfileReference = database.getReference(CodesUtil.REFERENCE_POLIC).child(profileId);

                loggedUserProfileReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String titulli = dataSnapshot.child("titulli").toString();
                        String grada = dataSnapshot.child("grada").toString();
                        Polic polic = new Polic(titulli, grada);
                        perdoruesPolic = new PerdoruesPolic(emer,mbiemer,rol,profileId,email,polic);
                        bejVeprim =true;
                        headerResult.removeProfile(0);
                        headerResult.addProfile(
                                new ProfileDrawerItem().withName(perdoruesPolic.getEmer()+" " + perdoruesPolic.getMbiemer()).withEmail(perdoruesPolic.getEmail()).withIcon(getResources().getDrawable(R.drawable.ic_icons_police)),0);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        toolbar.setTitle("Home");
        setTitle("Home");
        setupNavigationDrawer();
        initHomeFragment();
    }

    private void setupNavigationDrawer() {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem itemHome = new PrimaryDrawerItem().withIdentifier(0).withName("Home").withIcon(R.drawable.ic_icon__home_normal);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Gjej Shofer").withIcon(R.drawable.ic_icon_gjej_driver);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("Historiku i gjobave").withIcon(R.drawable.ic_icon_gjoba_history);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(5).withName("Settings").withIcon(R.drawable.ic_icon_settings);
        SecondaryDrawerItem logut = new SecondaryDrawerItem().withIdentifier(5).withName("Dil").withIcon(R.drawable.ic_icons_logout);
        //nis me Home Fragment ne OnCreate
        // Create the AccountHeader


         headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.nav_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Police police").withEmail("police@test").withIcon(getResources().getDrawable(R.drawable.ic_icons_police))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

//create the drawer and remember the `Drawer` result object
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        itemHome, item1, item3,
                        new DividerDrawerItem(),
                        settings,logut
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, final int position, IDrawerItem drawerItem) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                switch (position) {
                                    case 1:
                                        initHomeFragment();
                                        break;
                                    case 2:
                                        if(bejVeprim)
                                        initGjejShoferFragment();
                                        else{
                                            new MaterialDialog.Builder(PolicActivity.this)
                                                    .title("Humbi sinkronizimi")
                                                    .content("Problem ne databaze, nuk mund te vazhdoni me tej !")
                                                    .positiveText("Ok")
                                                    .show();
                                        }
                                        break;
                                    case 3:
                                        shikoGjobatEVena();
                                        break;
                                    case 4:

                                        break;
                                    case 5:
                                        break;
                                    case 6:
                                        auth.signOut();
                                        Intent intent = new Intent(PolicActivity.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                        break;

                                }
                            }
                        }, 200);
                        Log.d("position", " " + position);
                        return false;
                    }
                }).withAccountHeader(headerResult)
                .build();
      /*  result.addStickyFooterItem(new PrimaryDrawerItem().withName("Log out").withIconColor(Color.parseColor("#ffffff")).withIcon(R.drawable.ic_exit_to_app_white_24dp).withIdentifier(6).withTextColor(Color.parseColor("#ffffff")));
        result.getStickyFooter().setBackgroundResource(R.color.nav_yellow);*/
    }

    public void initHomeFragment(){
        toolbar.setTitle("Patrol App");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomePolic homePolic = new HomePolic();
        fragmentTransaction.replace(R.id.fragmentLogin, homePolic, "home");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void initGjejShoferFragment(){
        toolbar.setTitle("Gjej Shofer");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ListaShoferFragment listaShoferFragment = ListaShoferFragment.newInstance(perdoruesPolic.getIdProfil());
        fragmentTransaction.addToBackStack("Shofer");
        fragmentTransaction.replace(R.id.fragmentLogin, listaShoferFragment, "shofer");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void shikoGjobatEVena(){
        toolbar.setTitle("Gjobat");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        GjobatFragment gjobatFragment = new  GjobatFragment();
        fragmentTransaction.addToBackStack("Gjobat");
        fragmentTransaction.replace(R.id.fragmentLogin, gjobatFragment, "Gjobat");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(result.isDrawerOpen()){
            Log.d("Skerdi", "hapur");
            result.closeDrawer();
        }
        else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}
