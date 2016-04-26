package prog4_projekt.awpm_android.activities;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.adapter.ViewPaperAdapterMainActivity;
import prog4_projekt.awpm_android.fragmente.FragmentLoginDialog;
import prog4_projekt.awpm_android.fragmente.FragmentWarningDialog;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Boolean logdin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);

        final Button log = (Button) findViewById(R.id.login);

        log.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showLoginDialog(log);
            }
        });

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
    public void showLoginDialog(Button log){
        if(logdin == false){
        final FragmentLoginDialog dialog = new FragmentLoginDialog();
        dialog.show(getSupportFragmentManager(), "log");
            setLogin();
            switchTextOnButton(log);


        }
        else{
            FragmentWarningDialog dialog2 = new FragmentWarningDialog();
            dialog2.show(getSupportFragmentManager(), "Nolog");
        }

    }
    public void setLogin(){
        if(logdin == false){
            logdin = true;
        }
        else{
            logdin = false;
        }
    }
    public boolean getLogin(){
        return logdin;
    }
    public void switchTextOnButton(Button log){
        if(logdin){
            log.setText("Logout");
        }
        else{
            log.setText("Login");
        }

    }

}
