package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


import prog4_projekt.awpm_android.GCMRegistrationService;
import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;

import prog4_projekt.awpm_android.adapter.ViewPaperAdapterMainActivity;
import prog4_projekt.awpm_android.fragmente.FragmentBallot;
import prog4_projekt.awpm_android.fragmente.FragmentCourses;
import prog4_projekt.awpm_android.fragmente.FragmentLogin;
import prog4_projekt.awpm_android.fragmente.FragmentLoginDialog;
import prog4_projekt.awpm_android.fragmente.FragmentProfil;


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
        if(checkIfTokenIsExpired()){
            MySharedPreference.saveStringToken(sharedPref,null);
            MySharedPreference.saveBooleanIsLoged(sharedPref, false);
            Log.i("1111111", "checkToken dgf");
        }
        Log.i("1111111", "nach checkToken ");

        //Intent intent = new Intent(this, GCMRegistrationService.class);
        //startService(intent);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        String[] titleArray = {getString(R.string.katalogTab),getString(R.string.wahlzettelTab),getString(R.string.profiltab)};
        ViewPaperAdapterMainActivity viewPaperAdapter = null;
        if(MySharedPreference.getBooleanIsLoged(sharedPref)){
            viewPaperAdapter = new ViewPaperAdapterMainActivity(getSupportFragmentManager(),new FragmentCourses(),new FragmentBallot(),new FragmentProfil());
        }else{
            viewPaperAdapter = new ViewPaperAdapterMainActivity(getSupportFragmentManager(),new FragmentCourses(),new FragmentLogin(),new FragmentLogin());

        }
        viewPaperAdapter.setTitleArray(titleArray);
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
                FragmentLoginDialog.userLogout(sharedPref, this);
                invalidateOptionsMenu();

            }
            if( MySharedPreference.getBooleanIsLoged(sharedPref) == false){
                final FragmentLoginDialog dialog = new FragmentLoginDialog();
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.transperantDialog);
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
            menu.add(0,R.id.action_login,0,getString(R.string.logout));
            //menu.add(0,R.id.action_timeframe,0,getString(R.string.wahlzeitraeume));
            menu.add(0,R.id.action_links, 0 , getString(R.string.links));
        }
        if(String.valueOf(MySharedPreference.getBooleanIsLoged(sharedPref)).equals("false")){
            menu.add(0,R.id.action_login,0,getString(R.string.login));
            //menu.add(0,R.id.action_timeframe,0,getString(R.string.wahlzeitraeume));
            menu.add(0,R.id.action_links, 0 , getString(R.string.links));
        }
        return super.onPrepareOptionsMenu(menu);
    }
    public boolean checkIfTokenIsExpired(){
            Date today = new Date();
            Long todayLong = today.getTime();

        if( todayLong < MySharedPreference.getDateExpiresAtAsLong(sharedPref)){
                return false;
            }
            else{
                return true;
            }
    }


}
