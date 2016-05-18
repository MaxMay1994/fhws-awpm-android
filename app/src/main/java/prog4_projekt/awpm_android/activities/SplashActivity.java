package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import prog4_projekt.awpm_android.MySharedPreference;

/**
 * Created by florianduenow on 28.04.16.
 */
public class SplashActivity extends AppCompatActivity {
    public SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceSate) {
        super.onCreate(savedInstanceSate);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);


        MySharedPreference.saveBooleanIs401(sharedPref,false);
        MySharedPreference.saveBooleanIs500(sharedPref,false);
        MySharedPreference.saveBooleanIsFailed(sharedPref,false);

        if (MySharedPreference.getStringToken(sharedPref)==null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (MySharedPreference.getStringToken(sharedPref)!=null) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
        }
    }
}