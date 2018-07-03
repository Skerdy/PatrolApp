package com.barbarakoduzi.patrolapp.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.barbarakoduzi.patrolapp.Fragments.GjobatFragment;
import com.barbarakoduzi.patrolapp.Fragments.GjobatShoferFragment;
import com.barbarakoduzi.patrolapp.Fragments.HomePolic;
import com.barbarakoduzi.patrolapp.Fragments.HomeShofer;
import com.barbarakoduzi.patrolapp.Models.PerdoruesShofer;
import com.barbarakoduzi.patrolapp.Models.Shofer;
import com.barbarakoduzi.patrolapp.R;
import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class ShoferActivity extends AppCompatActivity {

    private Drawer result;
    private AccountHeader headerResult;
    private Toolbar toolbar;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference loggedUserReference, loggedUserProfileReference;

    private PerdoruesShofer perdoruesShofer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shofer);
        setupViews();
        setupFirebaseAndGetCurrentUser();
    }

    private void setupFirebaseAndGetCurrentUser(){
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        loggedUserReference = database.getReference(CodesUtil.REFERENCE_PERDORUES).child(auth.getCurrentUser().getUid());
        loggedUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String emer = dataSnapshot.child("emer").getValue().toString();
                final String mbiemer = dataSnapshot.child("mbiemer").getValue().toString();
                final Integer rol = Integer.parseInt(dataSnapshot.child("rol").getValue().toString());
                final String profileId = dataSnapshot.child("idProfil").getValue().toString();
                final String email = dataSnapshot.child("email").getValue().toString();
                loggedUserProfileReference = database.getReference(CodesUtil.REFERENCE_SHOFER).child(profileId);

                loggedUserProfileReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String pikePatente = dataSnapshot.child("pikePatente").toString();
                        String targa = dataSnapshot.child("targa").toString();
                        Shofer shofer = new Shofer(pikePatente, targa);
                        perdoruesShofer = new PerdoruesShofer(emer,mbiemer,rol,profileId,email,shofer);
                        headerResult.removeProfile(0);
                        headerResult.addProfile(
                                new ProfileDrawerItem().withName(perdoruesShofer.getEmer()+" " + perdoruesShofer.getMbiemer()).withEmail(perdoruesShofer.getEmail()).withIcon(getResources().getDrawable(R.drawable.ic_icons_shofer)),0);
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
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Gjobat e papaguara").withIcon(R.drawable.ic_icon_gjoba);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(3).withName("Historiku i gjobave").withIcon(R.drawable.ic_icon_gjoba_history);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(5).withName("Settings").withIcon(R.drawable.ic_icon_settings);
        SecondaryDrawerItem logut = new SecondaryDrawerItem().withIdentifier(5).withName("Dil").withIcon(R.drawable.ic_icons_logout);

        //nis me Home Fragment ne OnCreate
        // Create the AccountHeader


        headerResult= new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.nav_header)
                .addProfiles(
                        new ProfileDrawerItem().withName("").withEmail("").withIcon(getResources().getDrawable(R.drawable.ic_icons_police))
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
                        itemHome, item1, item2,
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
                                        shikoGjobatMeParamPagesen(false);
                                        break;
                                    case 3:
                                        shikoGjobatMeParamPagesen(true);
                                        break;
                                    case 5:
                                        //settings
                                        break;
                                    case 6:
                                        //logout
                                        auth.signOut();
                                        Intent intent = new Intent(ShoferActivity.this, Login.class);
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
        HomeShofer homeShofer = new HomeShofer();
        fragmentTransaction.replace(R.id.fragmentLogin, homeShofer, "home");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void shikoGjobatMeParamPagesen(boolean paguar){
        toolbar.setTitle("Gjobat e Mia");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        GjobatShoferFragment gjobatShoferFragment = GjobatShoferFragment.newInstance(paguar);
        fragmentTransaction.addToBackStack("Gjobat");
        fragmentTransaction.replace(R.id.fragmentLogin, gjobatShoferFragment, "Gjobat");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(result.isDrawerOpen()){
            result.closeDrawer();
        }
        else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }
}