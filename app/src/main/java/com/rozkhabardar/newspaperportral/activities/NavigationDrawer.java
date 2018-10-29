package com.rozkhabardar.newspaperportral.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.rozkhabardar.newspaperportral.R;
import com.rozkhabardar.newspaperportral.Services.AndroidBmService;
import com.rozkhabardar.newspaperportral.fragments.EpaperList;
import com.rozkhabardar.newspaperportral.fragments.Favourites;
import com.rozkhabardar.newspaperportral.fragments.HomeFragment;
import com.rozkhabardar.newspaperportral.fragments.JobFeeds;
import com.rozkhabardar.newspaperportral.fragments.Magazines;
import com.rozkhabardar.newspaperportral.fragments.NewsPapers;
import com.rozkhabardar.newspaperportral.fragments.SearchNews;
import com.rozkhabardar.newspaperportral.models.Items;
import com.rozkhabardar.newspaperportral.sharedpref.SharedPreference;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ActionBar actionBar;
    FrameLayout frame_content;
    SharedPreference sharedPreference;
    public static ArrayList<Items> mainfeed=new ArrayList<Items>();
    public static ArrayList<Items> saveslist=new ArrayList<Items>();
    public static ArrayList<Items> ist=new ArrayList<Items>();

    public static Toolbar toolbar;

    private static final String TAG = "BroadcastTest";
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        intent = new Intent(this, AndroidBmService.class);
        setDefaultContainer();
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        sharedPreference=new SharedPreference();
      //  mainfeed=sharedPreference.getFavorites(getApplicationContext());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        frame_content= (FrameLayout) findViewById(R.id.frame_content);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                Float move=(navigationView.getWidth()*slideOffset);
                frame_content.setTranslationX(move);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {
        ist=( ArrayList<Items>)intent.getSerializableExtra("bundle");
        for (int i = 0; i < ist.size(); i++) {
            Log.d("Values after service", ""+ist.get(i).getTitle());
        }

        }

    @Override
    protected void onResume() {
        super.onResume();
        startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(AndroidBmService.BROADCAST_ACTION));
           }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
       unregisterReceiver(broadcastReceiver);
       // stopService(intent);
    }

    private void setDefaultContainer() {
        try {
            FragmentManager mFragmentManager=getSupportFragmentManager();
            mFragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            HomeFragment homefrag=new HomeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,homefrag).commit();
        }
        catch (Exception e)
        {
            Crashlytics.logException(e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(getSupportFragmentManager().getBackStackEntryCount()>0)
        {
        getSupportFragmentManager().popBackStack();        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit");
            builder.setMessage("Do you want to Exit?");
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
            //super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        try {
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.nav_home) {
                setDefaultContainer();
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        try {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                setDefaultContainer();

               /* Intent intent=new Intent(NavigationDrawer.this,WebActivity.class);
                intent.putExtra("url","http://epaper.greaterkashmir.com/");
                intent.putExtra("title","GK");
                startActivity(intent);*/
            } else if (id == R.id.nav_newspapers) {
                NewsPapers newsPapersfrag=new NewsPapers();
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,newsPapersfrag).addToBackStack(null).commit();

            }
            else if (id == R.id.nav_magazines) {
              Magazines newsPapersfrag=new Magazines();
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,newsPapersfrag).addToBackStack(null).commit();

            }
            else if (id == R.id.nav_epaper) {
                EpaperList newsPapersfrag=new EpaperList();
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,newsPapersfrag).addToBackStack(null).commit();

            }
            else if (id == R.id.nav_search) {
/*
                SearchNews newsPapersfrag=new SearchNews();
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,newsPapersfrag).addToBackStack(null).commit();
*/

            }
            else if (id == R.id.nav_job) {
               JobFeeds newsPapersfrag=new JobFeeds();
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,newsPapersfrag).addToBackStack(null).commit();

            }
            else if (id == R.id.nav_favourites) {
                Favourites storedfav=new Favourites();
                getSupportFragmentManager().beginTransaction().replace(R.id.subview_container,storedfav).addToBackStack(null).commit();

            }
            else if (id == R.id.nav_exit) {
                finish();
            }
        } catch (Exception e) {
            Crashlytics.logException(e);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setActionBarTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

}
