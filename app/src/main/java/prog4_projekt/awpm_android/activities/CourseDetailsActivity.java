package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import prog4_projekt.awpm_android.R;


public class CourseDetailsActivity extends AppCompatActivity {
    TextView information;

    TextView toolTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        information = (TextView) findViewById(R.id.Course_details);
        Intent passedInf = getIntent();
        Bundle extras = passedInf.getExtras();
        if(extras!=null){
            String title = extras.getString("Titel");
            String text = extras.getString("Informationen");
            toolTitle.setText(title);
            information.setText(text);
            information.setMovementMethod(new ScrollingMovementMethod());
        }


    }

}
