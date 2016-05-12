package prog4_projekt.awpm_android.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.adapter.ViewPaperAdapterMainActivity;
import prog4_projekt.awpm_android.fragmente.FragmentLoginDialog;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    public SharedPreferences sharedPref ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPaperAdapterMainActivity viewPaperAdapter = new ViewPaperAdapterMainActivity(getSupportFragmentManager());
        viewPager.setAdapter(viewPaperAdapter);
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
                invalidateOptionsMenu();
            }
            if( MySharedPreference.getBooleanIsLoged(sharedPref) == false && MySharedPreference.getStringToken(sharedPref) ==null){
                final FragmentLoginDialog dialog = new FragmentLoginDialog();
                dialog.show(getSupportFragmentManager(), "log");
                invalidateOptionsMenu();
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        menu.clear();
        if(MySharedPreference.getBooleanIsLoged(sharedPref) == true){
            menu.add(0,R.id.action_login,0,"Logout");
        }
        if(MySharedPreference.getBooleanIsLoged(sharedPref) == false){
            menu.add(0,R.id.action_login,0,"Login");
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
