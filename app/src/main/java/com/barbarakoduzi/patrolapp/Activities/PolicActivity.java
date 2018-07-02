package com.barbarakoduzi.patrolapp.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.barbarakoduzi.patrolapp.R;
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
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        toolbar.setTitle("Home");
        setTitle("Home");
        setupNavigationDrawer();
    }

    private void setupNavigationDrawer() {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem itemHome = new PrimaryDrawerItem().withIdentifier(0).withName("Home").withIcon(R.drawable.ic_icon__home_normal);
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Gjej Shofer").withIcon(R.drawable.ic_icon_gjej_driver);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(3).withName("Gjoba ekzistuese").withIcon(R.drawable.ic_icon_gjoba);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("Historiku i gjobave").withIcon(R.drawable.ic_icon_gjoba_history);
        SecondaryDrawerItem settings = new SecondaryDrawerItem().withIdentifier(5).withName("Settings").withIcon(R.drawable.ic_icon_settings);
        //nis me Home Fragment ne OnCreate
        // Create the AccountHeader


        AccountHeader headerResult = new AccountHeaderBuilder()
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
                        itemHome, item1, item3, item2,
                        new DividerDrawerItem(),
                        settings
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, final int position, IDrawerItem drawerItem) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                switch (position) {
                                    case 1:

                                        break;
                                    case 2:

                                        break;
                                    case 3:

                                        break;
                                    case 4:

                                        break;
                                    case 5:
                                        break;
                                    case 7:
                                        break;
                                    case -1:
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
}
