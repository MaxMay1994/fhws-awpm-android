package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;


import java.util.List;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;

import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.fragmente.FragmentLoginDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseDetailsActivity extends AppCompatActivity {
    TextView information, info_content, toolTitle, dozent_header, dozent_content, location_header,
            location_content, startDate_header, startDate_content, endDate_header, endDate_content,
            examType_header, examType_content, examNumber_header, examNumber_content, participants_header, participants_content,
            blocked_header, blocked_content, appearance_header, appearance_content;
    MaterialFavoriteButton mfb;
    Switch wahlSwitch;
    boolean moduleVoted, moduleFavorite, blocked, appearance;
    String favMarked, notFavMarked, voteMarked, notVoteMarked, name, content, examType, teacher, start, end, city, location, room, examNumber,
    authorization;
    SharedPreferences sharedPreferences;
    Call<Module> callVoted;
    Call<Module> callFavorite;
    int id, participants, votes;
    FragmentLoginDialog dialog;
    Module passedModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        blocked_header = (TextView) findViewById(R.id.blocked_header);
        blocked_content = (TextView) findViewById(R.id.blocked_content);
        appearance_header = (TextView) findViewById(R.id.appearance_header);
        appearance_content = (TextView) findViewById(R.id.appearance_content);
        mfb = (MaterialFavoriteButton) findViewById(R.id.view);
        wahlSwitch = (Switch) findViewById(R.id.wahlswitch);
        Intent passedObject = getIntent();
        Bundle extras = passedObject.getExtras();
        name = extras.getString("name");
        content = extras.getString("content");
        examType = extras.getString("examtype");
        teacher = extras.getString("Teacher");
        start = extras.getString("start");
        end = extras.getString("end");
        city = extras.getString("city");
        location = extras.getString("location");
        room = extras.getString("room");
        participants = extras.getInt("participants");
        examNumber = extras.getString("examnumber");
        id = extras.getInt("id");
        blocked = extras.getBoolean("blocked");
        appearance = extras.getBoolean("mandatory");
        moduleVoted = extras.getBoolean("voted");
        votes = extras.getInt("votes");
        if(moduleVoted){
            wahlSwitch.setChecked(true);
        }
        moduleFavorite = extras.getBoolean("favorite");
        if (moduleFavorite) mfb.setFavorite(true);
        if(extras != null){
            toolTitle.setText(name);
            information.setText(getString(R.string.description));
            info_content.setText(content);
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
            participants_content.setText(votes + " / " + String.valueOf(participants));
            blocked_header.setText(getString(R.string.blocked));
            if(blocked)blocked_content.setText("✔");
            else if(!blocked)blocked_content.setText("✘");
            else blocked_content.setText("unbekannt");
            appearance_header.setText(getString(R.string.appearance));
            if(appearance)appearance_content.setText("✔");
            else if(!appearance)appearance_content.setText("✘");
            else appearance_content.setText("unbekannt");
        }

        Resources res = getResources();
        favMarked = String.format(res.getString(R.string.favorite), name);
        notFavMarked = String.format(res.getString(R.string.nonFavorite), name);
        voteMarked = String.format(res.getString(R.string.voted), name);
        notVoteMarked = String.format(res.getString(R.string.notvoted), name);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPreferences)+":").getBytes(), Base64.NO_WRAP);
        mfb.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                if (MySharedPreference.getBooleanIsLoged(sharedPreferences)) {

                    if (mfb.isFavorite()) {

                        callFavorite = ServiceAdapter.getService().patchFavored(id, true, authorization);
                        callFavorite.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Log.i("Test", response.message());
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.i("FailTest", t.toString());
                            }
                        });
                        Toast.makeText(getApplicationContext(), favMarked, Toast.LENGTH_LONG).show();
                    } else {

                        callFavorite = ServiceAdapter.getService().patchFavored(id, false, authorization);
                        callFavorite.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Log.i("Test", response.message());
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.i("FailTest", t.toString());
                            }
                        });
                        Toast.makeText(getApplicationContext(), notFavMarked, Toast.LENGTH_LONG).show();
                    }
                } else {
                    mfb.setFavorite(false);
                    dialog = new FragmentLoginDialog();
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.transperantDialog);
                    dialog.show(getSupportFragmentManager(), "log");
                }
            }
        } );
        wahlSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(MySharedPreference.getBooleanIsLoged(sharedPreferences)) {

                    if (wahlSwitch.isChecked()) {
                        callVoted = ServiceAdapter.getService().patchVoted(id, true, authorization);
                        callVoted.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Log.i("Test", response.message());
                                Toast.makeText(getApplicationContext(), voteMarked, Toast.LENGTH_LONG).show();
                                Intent voteBack = new Intent(getApplicationContext(), MainActivity.class);
                                int id = 1;
                                voteBack.putExtra("id", id);
                                startActivity(voteBack);
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.i("FailTest", t.toString());
                            }
                        });

                    } else {
                        moduleVoted = false;
                        callVoted = ServiceAdapter.getService().patchVoted( id, false, authorization);
                        callVoted.enqueue(new Callback<Module>() {
                            @Override
                            public void onResponse(Call<Module> call, Response<Module> response) {
                                Log.i("Test", response.message());
                                Toast.makeText(getApplicationContext(), notVoteMarked, Toast.LENGTH_LONG).show();
                                Intent voteBack = new Intent(getApplicationContext(), MainActivity.class);
                                int id = 1;
                                voteBack.putExtra("id", id);
                                startActivity(voteBack);
                            }

                            @Override
                            public void onFailure(Call<Module> call, Throwable t) {
                                Log.i("FailTest", t.toString());
                            }
                        });

                    }
                }else{
                    wahlSwitch.setChecked(false);
                    dialog = new FragmentLoginDialog();
                    dialog.show(getSupportFragmentManager(), "log");
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
