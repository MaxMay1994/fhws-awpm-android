package prog4_projekt.awpm_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.Module.Building;
import prog4_projekt.awpm_android.RestApi.Module.City;
import prog4_projekt.awpm_android.RestApi.Module.Location;
import prog4_projekt.awpm_android.RestApi.Module.SubjectArea;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    String wahlZeitraumID = null;
    String locationID = null;
    int locationid;
    String blockedforMe = null;
    String blockedForID = null;
    int blockedforid;
    String favoredModulesID = null;
    Call<List<SubjectArea>> callSubjects;
    List<SubjectArea> areaList;
    Call<List<City>> callLocations;
    List<City> locList;
    List<Building> buildings;
    String[] areasFinal, buildingsFinal;

    CheckBox wahlZeitraum, location, blockedFor, favoredModules;
    Button returnFromFilter, resetFilter;
    Spinner spinnerFilter1, spinnerFilter2;
    TextView toolText;
    ArrayAdapter<String> adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter2);

        toolText = (TextView) findViewById(R.id.filtertoolbar_title);
        wahlZeitraum = (CheckBox) findViewById(R.id.aktuellerWahlzeitraum);
        location = (CheckBox) findViewById(R.id.location);
        blockedFor = (CheckBox) findViewById(R.id.gesperrt);
        favoredModules = (CheckBox) findViewById(R.id.favoriteModulesFilter);


        callLocations = ServiceAdapter.getService().getAllCity();
        callLocations.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                locList = response.body();
                buildings = locList.get(0).getBuildings();
                final Building[] building = buildings.toArray(new Building[buildings.size()]);
                buildingsFinal = new String[(building.length + 1)];
                buildingsFinal[0] = getString(R.string.auswaehlen);
                for (int i = 0; i < building.length; i++) {
                    buildingsFinal[i + 1] = building[i].getName();
                }
                spinnerFilter1 = (Spinner) findViewById(R.id.spinner1);
                adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, buildingsFinal);
                spinnerFilter1.setAdapter(adapter1);
                spinnerFilter1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            for (int i = 1; i < buildingsFinal.length; i++) {
                                if (i == position) {
                                    location.setChecked(true);
                                    locationid = i;
                                    Log.i("Location im Filter", String.valueOf(locationid));
                                }
                            }
                        } else {
                            location.setChecked(false);
                            locationid = 0;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        location.setChecked(false);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

            }
        });

        wahlZeitraum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wahlZeitraumID = "wahlzeitraum";
                    Log.i("WahlzeitraumID", wahlZeitraumID);
                }
            }
        });
        favoredModules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    favoredModulesID = "favorites";
                    Log.i("FavoritenID", favoredModulesID);
                }
            }
        });


        callSubjects = ServiceAdapter.getService().getAllSubjectAreas(50);
        callSubjects.enqueue(new Callback<List<SubjectArea>>() {
            @Override
            public void onResponse(Call<List<SubjectArea>> call, Response<List<SubjectArea>> response) {
                areaList = response.body();
                SubjectArea[] areas = areaList.toArray(new SubjectArea[areaList.size()]);
                areasFinal = new String[(areas.length) + 2];
                areasFinal[0] = getString(R.string.auswaehlen);
                areasFinal[1] = getString(R.string.mich);
                for (int i = 0; i < areas.length; i++) {
                    areasFinal[i + 2] = areas[i].getName();
                }

                spinnerFilter2 = (Spinner) findViewById(R.id.spinner2);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, areasFinal);
                spinnerFilter2.setAdapter(adapter2);
                spinnerFilter2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            for (int i = 1; i < areasFinal.length; i++) {
                                if (i == position) {
                                    blockedFor.setChecked(true);
                                    if (areasFinal[i].equalsIgnoreCase("mich")) {
                                        blockedforMe = "mich";
                                        Log.i("BlockedForMe", blockedforMe);
                                    } else {
                                        blockedforid = areaList.get(position - 2).getId();
                                        //blockedforid = i-1;
                                        Log.i("Block im Filter", String.valueOf(blockedforid));
                                    }
                                }
                            }
                        } else {
                            blockedFor.setChecked(false);
                            blockedforid = 0;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        blockedFor.setChecked(false);
                    }
                });


            }

            @Override
            public void onFailure(Call<List<SubjectArea>> call, Throwable t) {

            }
        });

        returnFromFilter = (Button) findViewById(R.id.return_filter);
        resetFilter = (Button) findViewById(R.id.reset_filter);
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("wahlZeitraumID", (String) null);
                returnIntent.putExtra("locationID", (Integer) null);
                returnIntent.putExtra("blockedForMe", (String) null);
                returnIntent.putExtra("blockedForAll", (Integer) null);
                returnIntent.putExtra("favoredModulesID", (String) null);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        returnFromFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = getIntent();
                returnIntent.putExtra("wahlZeitraumID", wahlZeitraumID);
                returnIntent.putExtra("locationID", locationid);
                returnIntent.putExtra("blockedForMe", blockedforMe);
                returnIntent.putExtra("blockedForAll", blockedforid);
                returnIntent.putExtra("favoredModulesID", favoredModulesID);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
