package prog4_projekt.awpm_android.activities;

import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.adapter.ViewPaperAdapterTimeframes;


/**
 * Created by florianduenow on 17.04.16.
 */
public class TimeframesActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ViewPaperAdapterTimeframes viewPaperAdapter = new ViewPaperAdapterTimeframes(getSupportFragmentManager());
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
}
