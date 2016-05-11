package prog4_projekt.awpm_android.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import prog4_projekt.awpm_android.R;

/**
 * Created by florianduenow on 28.04.16.
 */
public class SplashActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceSate){
        super.onCreate(savedInstanceSate);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
    }
}
