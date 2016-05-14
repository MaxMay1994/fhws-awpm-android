package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.w3c.dom.Text;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;

import prog4_projekt.awpm_android.RestApi.AwpmApi;
import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.adapter.ModuleViewHolder;
import prog4_projekt.awpm_android.fragmente.FragmentCourses;
import prog4_projekt.awpm_android.fragmente.FragmentLoginDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseDetailsActivity extends AppCompatActivity {
    TextView information, info_content, toolTitle, dozent_header, dozent_content, location_header,
            location_content, startDate_header, startDate_content, endDate_header, endDate_content,
            examType_header, examType_content, examNumber_header, examNumber_content, participants_header, participants_content;
    MaterialFavoriteButton mfb;
    Switch wahlSwitch;
    boolean moduleVoted, moduleFavorite;
    String favMarked, notFavMarked, voteMarked, notVoteMarked;
    SharedPreferences sharedPreferences;
    Module passedModule;
    Call<Module> callVoted;
    Call<Module> callFavorite;
    int id;
    String authorization;


    FragmentLoginDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        information = (TextView) findViewById(R.id.Course_details);
        info_content = (TextView) findViewById(R.id.course_content);
        dozent_header = (TextView) findViewById(R.id.dozent_header);
        dozent_content = (TextView) findViewById(R.id.dozent_content);
        location_header = (TextView) findViewById(R.id.location_header);
        location_content = (TextView) findViewById(R.id.location_content);
        startDate_header = (TextView) findViewById(R.id.startDate_header);
        startDate_content = (TextView) findViewById(R.id.startDate_content);
        endDate_header = (TextView) findViewById(R.id.endDate_header);
        endDate_content = (TextView) findViewById(R.id.endDate_content);
        examType_header = (TextView) findViewById(R.id.examType_header);
        examType_content = (TextView) findViewById(R.id.examType_content);
        examNumber_header = (TextView) findViewById(R.id.examNumber_header);
        examNumber_content = (TextView) findViewById(R.id.examNumber_content);
        participants_header = (TextView) findViewById(R.id.participants_header);
        participants_content = (TextView) findViewById(R.id.participants_content);
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
        String city = extras.getString("city");
        String location = extras.getString("location");
        String room = extras.getString("room");
        int participants = extras.getInt("participants");
        String examNumber = extras.getString("examnumber");
        id = extras.getInt("id");
        moduleVoted = extras.getBoolean("voted");
        moduleFavorite = extras.getBoolean("favorite");
        //passedModule = passedObject.getParcelableExtra("Object");

        if(extras!= null){

            toolTitle.setText(name);
            information.setText(getString(R.string.description));
            info_content.setText(content); //+numberVoted);
            dozent_header.setText(getString(R.string.teacher));
            dozent_content.setText(teacher);
            location_header.setText(getString(R.string.location));
            location_content.setText(city+", "+location+", "+room);
            startDate_header.setText(getString(R.string.start));
            startDate_content.setText(start);
            endDate_header.setText(getString(R.string.end));
            endDate_content.setText(end);
            examType_header.setText(getString(R.string.examType));
            examType_content.setText(examType);
            examNumber_header.setText(getString(R.string.examnumber));
            examNumber_content.setText(examNumber);
            participants_header.setText(getString(R.string.participants));
            participants_content.setText("0 /" + String.valueOf(participants));
        }

        Resources res = getResources();
        favMarked = String.format(res.getString(R.string.favorite), name);
        notFavMarked = String.format(res.getString(R.string.nonFavorite), name);
        voteMarked = String.format(res.getString(R.string.voted), name);
        notVoteMarked = String.format(res.getString(R.string.notvoted), name);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(moduleVoted)
            wahlSwitch.setChecked(true);
        if(moduleFavorite)
            mfb.setFavorite(true);



        authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPreferences)+":").getBytes(), Base64.NO_WRAP);

        mfb.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (MySharedPreference.getBooleanIsLoged(sharedPreferences)) {

                    if (!moduleFavorite) {
                        if (favorite) {

                            callFavorite = ServiceAdapter.getService().patchFavored(id, true, authorization);
                            callFavorite.enqueue(new Callback<Module>() {
                                @Override
                                public void onResponse(Call<Module> call, Response<Module> response) {
                                    Log.i("Test", response.toString());
                                }

                                @Override
                                public void onFailure(Call<Module> call, Throwable t) {
                                    Log.i("Test", t.toString());
                                }
                            });
                            Toast.makeText(getApplicationContext(), favMarked, Toast.LENGTH_SHORT).show();
                            mfb.setFavorite(true);
                        }

                    } else {
                        moduleFavorite = false;
                        callFavorite = ServiceAdapter.getService().patchFavored(id, false, authorization);
                        callFavorite.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Log.i("Test", response.toString());
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.i("Test", t.toString());
                            }
                        });
                        Toast.makeText(getApplicationContext(), notFavMarked, Toast.LENGTH_SHORT).show();
                        mfb.setFavorite(false);
                    }
                } else {
                    dialog = new FragmentLoginDialog();
                    dialog.show(getSupportFragmentManager(), "log");
                    }
                }
            });




        wahlSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MySharedPreference.getBooleanIsLoged(sharedPreferences)) {
                    if (isChecked) {

                        callVoted = ServiceAdapter.getService().patchVoted(id, true, authorization);
                        callVoted.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Log.i("Test", response.toString());
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.i("Test", t.toString());
                            }
                        });
                        Toast.makeText(getApplicationContext(), voteMarked, Toast.LENGTH_SHORT).show();
                        wahlSwitch.setChecked(true);
                    } else {
                        moduleVoted = false;
                        callVoted = ServiceAdapter.getService().patchVoted( id, false, authorization);
                        callVoted.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Log.i("Test", response.toString());
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.i("Test", t.toString());
                            }
                        });
                        Toast.makeText(getApplicationContext(), notVoteMarked, Toast.LENGTH_SHORT).show();
                        wahlSwitch.setChecked(false);
                    }
                }else{
                    dialog = new FragmentLoginDialog();
                    dialog.show(getSupportFragmentManager(), "log");
                }
            }
        });
    }
}
