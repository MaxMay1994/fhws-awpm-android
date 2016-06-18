package prog4_projekt.awpm_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import prog4_projekt.awpm_android.RestApi.Module.Location;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterActivity extends AppCompatActivity {

    String wahlZeitraumID = null;
    String buildingID = null;
    String locationID = null;
    String blockedforMe = null;
    String blockedForID = null;
    String favoredModulesID = null;
    Call<List<Building>> callSubjects;
    List<Building> areaList;
    String[] areasFinal;

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

                spinnerFilter1 = (Spinner) findViewById(R.id.spinner1);
                final  String[] itemsFilter1 = new String[]{getString(R.string.auswaehlen), getString(R.string.ortSw), getString(R.string.ortWue), getString(R.string.ortMuenz), getString(R.string.ortShl)};
                adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsFilter1);
                spinnerFilter1.setAdapter(adapter1);
                spinnerFilter1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            for (int i = 1; i < itemsFilter1.length; i++) {
                                if (i == position){
                                    location.setChecked(true);
                                    if(itemsFilter1[i].equalsIgnoreCase("Schweinfurt")) {
                                        locationID = "schweinfurt";
                                        Log.i("LocationName", locationID);
                                    }
                                    else if(itemsFilter1[i].equalsIgnoreCase("Würzburg")){
                                        locationID = "würzburg";
                                        Log.i("LocationName", locationID);
                                    }
                                    else if(itemsFilter1[i].equalsIgnoreCase("Münzstr.")){
                                        buildingID = "münzstr.";
                                        Log.i("BuildingName", buildingID);
                                    }
                                    else if(itemsFilter1[i].equalsIgnoreCase("SHL")){
                                        buildingID = "shl";
                                        Log.i("BuildingName", buildingID);
                                    }
                                }
                            }
                        } else {
                            location.setChecked(false);
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        location.setChecked(false);
                    }
                });





        wahlZeitraum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wahlZeitraumID = "wahlzeitraum";
                    Log.i("WahlzeitraumID", wahlZeitraumID);
                }
            }
        });
        favoredModules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    favoredModulesID = "favorites";
                    Log.i("FavoritenID", favoredModulesID);
                }
            }
        });


        callSubjects = ServiceAdapter.getService().getAllSubjectAreas();
        callSubjects.enqueue(new Callback<List<Building>>() {
            @Override
            public void onResponse(Call<List<Building>> call, Response<List<Building>> response) {
                areaList=response.body();
                Building[] areas = areaList.toArray(new Building [areaList.size()]);
                areasFinal = new String[(areas.length)];
                //areasFinal[0] = getString(R.string.auswaehlen);
                //areasFinal[1] = getString(R.string.mich);
                for(int i = 0; i < areas.length; i++){
                    areasFinal[i] = areas[i].getName();
                    Log.i("array", areasFinal[i]);
                }

                spinnerFilter2 = (Spinner) findViewById(R.id.spinner2);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, areasFinal);
                spinnerFilter2.setAdapter(adapter2);
                spinnerFilter2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            for (int i = 1; i < areasFinal.length; i++) {
                                if (i == position){
                                    blockedFor.setChecked(true);
                                    if (areasFinal[i].equalsIgnoreCase("mich")) {
                                        blockedforMe = "mich";
                                        Log.i("BlockedForMe", blockedforMe);
                                    }
                                    else if(areasFinal[i].equalsIgnoreCase("BIN")){
                                        blockedForID = "bin";
                                        Log.i("BlockedAllgemein", blockedForID);
                                    }
                                    else if(areasFinal[i].equalsIgnoreCase("BWI")){
                                        blockedForID = "bwi";
                                        Log.i("BlockedAllgemein", blockedForID);
                                    }
                                }
                            }
                        } else {
                            blockedFor.setChecked(false);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        blockedFor.setChecked(false);
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Building>> call, Throwable t) {

            }
        });

        returnFromFilter = (Button) findViewById(R.id.return_filter);
        resetFilter = (Button) findViewById(R.id.reset_filter);
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        returnFromFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = getIntent();
                returnIntent.putExtra("wahlZeitraumID", wahlZeitraumID);
                returnIntent.putExtra("locationID", locationID);
                returnIntent.putExtra("buildingID", buildingID);
                returnIntent.putExtra("blockedForMe", blockedforMe);
                returnIntent.putExtra("blockedForID", blockedForID);
                returnIntent.putExtra("favoredModulesID", favoredModulesID);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
            }
        });

    }
}
