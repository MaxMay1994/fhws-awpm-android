package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.Peroids.Periods;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.adapter.ViewPaperAdapterMainActivity;
import prog4_projekt.awpm_android.fragmente.FragmentLoginDialog;
import prog4_projekt.awpm_android.fragmente.FragmentTimeframesWue;
import prog4_projekt.awpm_android.fragmente.FragmentWarningDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    public SharedPreferences sharedPref ;
    Intent getFromDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        MySharedPreference.saveBooleanIs401(sharedPref,false);
        MySharedPreference.saveBooleanIs500(sharedPref,false);
        MySharedPreference.saveBooleanIsFailed(sharedPref,false);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final ViewPaperAdapterMainActivity viewPaperAdapter = new ViewPaperAdapterMainActivity(getSupportFragmentManager());
        viewPager.setAdapter(viewPaperAdapter);
        getFromDetails = getIntent();
        int id = getFromDetails.getIntExtra("id", 0);
        if(id == 1) {
            viewPager.setCurrentItem(0);
        }
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_login) {
            if( MySharedPreference.getBooleanIsLoged(sharedPref) == true){
               LoginActivity.userLogout(sharedPref, getSupportFragmentManager());
                synchronized (this){
                    try {
                        this.wait(1950);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                invalidateOptionsMenu();
            }
            if( MySharedPreference.getBooleanIsLoged(sharedPref) == false){
                final FragmentLoginDialog dialog = new FragmentLoginDialog();
                dialog.show(getSupportFragmentManager(), "log");
                invalidateOptionsMenu();
            }
        }
        if(id == R.id.action_timeframe){
            Intent times = new Intent(this, TimeframesActivity.class);
            startActivity(times);
        }
        if(id == R.id.action_links){
            Intent links = new Intent(this, LinkActivity.class);
            startActivity(links);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.clear();
        if(String.valueOf(MySharedPreference.getBooleanIsLoged(sharedPref)).equals("true")){
            menu.add(0,R.id.action_login,0,"Logout");
            menu.add(0,R.id.action_timeframe,0,"Zeitraeume");
            menu.add(0,R.id.action_links, 0 , "Links");
        }
        if(String.valueOf(MySharedPreference.getBooleanIsLoged(sharedPref)).equals("false")){
            menu.add(0,R.id.action_login,0,"Login");
            menu.add(0,R.id.action_timeframe,0,"Zeitraeume");
            menu.add(0,R.id.action_links, 0 , "Links");

        }
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(MySharedPreference.getBooleanIs401(sharedPref) || MySharedPreference.getBooleanIs500(sharedPref) || MySharedPreference.getBooleanIsFailed(sharedPref)){
            FragmentWarningDialog dialog = new FragmentWarningDialog();
            dialog.show(getSupportFragmentManager(), "log");
        }
    }


}
