package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.w3c.dom.Text;

import prog4_projekt.awpm_android.R;

import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.adapter.ModuleViewHolder;
import prog4_projekt.awpm_android.fragmente.FragmentCourses;


public class CourseDetailsActivity extends AppCompatActivity {
    TextView information;
    TextView toolTitle;
    MaterialFavoriteButton mfb;
    Switch wahlSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        information = (TextView) findViewById(R.id.Course_details);
        mfb = (MaterialFavoriteButton) findViewById(R.id.view);
        wahlSwitch = (Switch) findViewById(R.id.wahlswitch);
        Intent passedObject = getIntent();
        Bundle extras = passedObject.getExtras();
        String name = extras.getString("name");
        String content = extras.getString("content");
        String examType = extras.getString("examtype");
        String teacher = extras.getString("Teacher");
        String start = extras.getString("start");
        String end = extras.getString("end");
        int participants = extras.getInt("participants");
        final Module passedModule = passedObject.getParcelableExtra("Object");

        if(extras!= null){
            toolTitle.setText(name);
            information.setText(getString(R.string.description)+ content  + getString(R.string.teacher)
            + teacher + getString(R.string.examType) + examType + getString(R.string.start) + start
                    + getString(R.string.end) + end + getString(R.string.participants) + participants);
            information.setMovementMethod(new ScrollingMovementMethod());

        }
       /* Bundle extras = passedInf.getExtras();
        if(extras!=null){
            String title = extras.getString("Titel");
            String text = extras.getString("Informationen");
            toolTitle.setText(title);
            information.setText(text);
            information.setMovementMethod(new ScrollingMovementMethod());
        }*/

        mfb.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if(favorite){
                    passedModule.setVoted(true);
                    Toast.makeText(getApplicationContext(), "Modul wurde als 'Favorit' markiert", Toast.LENGTH_LONG).show();
                }
                else{
                    passedModule.setVoted(false);
                    Toast.makeText(getApplicationContext(), "Modul nicht mehr als 'Favorit' markiert", Toast.LENGTH_LONG).show();
                }
            }
        });

        wahlSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passedModule.setFavorite(true);
                    Toast.makeText(getApplicationContext(), "Modul wurde als 'gewählt' markiert", Toast.LENGTH_LONG).show();
                }
                else{
                    passedModule.setFavorite(false);
                    Toast.makeText(getApplicationContext(), "Modul wurde als 'nicht gewählt' markiert", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
