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
    boolean moduleVoted;
    boolean moduleFavorite;
    Module passedModule;


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
        String room = extras.getString("room");
        int participants = extras.getInt("participants");
        String examNumber = extras.getString("examnumber");
        moduleVoted = extras.getBoolean("voted");
        moduleFavorite = extras.getBoolean("favorite");
        //passedModule = passedObject.getParcelableExtra("Object");

        if(extras!= null){
            toolTitle.setText(name);
            information.setText(getString(R.string.description)+ content  + getString(R.string.teacher)
            + teacher + getString(R.string.examType) + examType + getString(R.string.start) + start
                    + getString(R.string.end) + end + getString(R.string.participants) + participants //+numberVoted
            + getString(R.string.location) + room + getString(R.string.examnumber) + examNumber);
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
                    moduleVoted = true;
                    Toast.makeText(getApplicationContext(), "AWPM als 'Favorit' markiert", Toast.LENGTH_LONG).show();
                }
                else{
                    moduleVoted = false;
                    Toast.makeText(getApplicationContext(), "AWPM nicht als 'Favorit' markiert", Toast.LENGTH_LONG).show();
                }
            }
        });

        wahlSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    moduleFavorite = true;
                    Toast.makeText(getApplicationContext(), "AWPM wurde gewählt!", Toast.LENGTH_LONG).show();
                }
                else{
                    moduleFavorite = false;
                    Toast.makeText(getApplicationContext(), "AWPM wurde als 'nicht gewählt' markiert!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}
