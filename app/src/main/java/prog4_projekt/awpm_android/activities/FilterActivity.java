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

public class FilterActivity extends AppCompatActivity {

    String wahlZeitraumID = "";
    String locationID = "";
    String blockedForID = "";
    String favoredModulesID = "";
    CheckBox wahlZeitraum, location, blockedFor, favoredModules;
    Button returnFromFilter, resetFilter;
    Spinner spinnerFilter1, spinnerFilter2;
    TextView toolText;

    List<Building> locationList;
    Call<List<Building>> callLocations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter2);

        toolText = (TextView) findViewById(R.id.filtertoolbar_title);
        wahlZeitraum = (CheckBox) findViewById(R.id.aktuellerWahlzeitraum);
        location = (CheckBox) findViewById(R.id.location);
        blockedFor = (CheckBox) findViewById(R.id.gesperrt);
        favoredModules = (CheckBox) findViewById(R.id.favoriteModulesFilter);


        wahlZeitraum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wahlZeitraumID = "wahlZeitraum";
                    Log.i("ID-name", wahlZeitraumID);
                }
                else{
                    wahlZeitraumID = "null";
                    Log.i("Id-name", wahlZeitraumID);
                }
            }
        });
        favoredModules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    favoredModulesID = "favoredModules";
                    Log.i("ID-name", favoredModulesID);
                }
                else{
                    favoredModulesID = "null";
                    Log.i("ID-name", favoredModulesID);
                }
            }
        });


        spinnerFilter1 = (Spinner) findViewById(R.id.spinner1);
        final String[] itemsFilter1 = new String[]{getString(R.string.auswaehlen), getString(R.string.ortSw), getString(R.string.ortWue), getString(R.string.ortMuenz), getString(R.string.ortShl)};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsFilter1);
        spinnerFilter1.setAdapter(adapter1);
        spinnerFilter1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    for (int i = 1; i < itemsFilter1.length; i++) {
                        if (i == position){
                            location.setChecked(true);
                            locationID = itemsFilter1[i];
                            Log.i("ID-Name", locationID);
                        }
                    }
                } else {
                    location.setChecked(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                location.setChecked(false);
                locationID = "null";
                Log.i("ID-Name", locationID);
            }
        });

        spinnerFilter2 = (Spinner) findViewById(R.id.spinner2);
        final String[] itemsFilter2 = new String[]{getString(R.string.auswaehlen),getString(R.string.mich), getString(R.string.stringStudiengangBIN), getString(R.string.stringStudiengangBWI)};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsFilter2);
        spinnerFilter2.setAdapter(adapter2);
        spinnerFilter2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    for (int i = 1; i < itemsFilter2.length; i++) {
                        if (i == position){
                            blockedFor.setChecked(true);
                            blockedForID = itemsFilter2[i];
                            Log.i("ID-Name", blockedForID);
                        }
                    }
                } else {
                    blockedFor.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                blockedFor.setChecked(false);
                blockedForID = "null";
                Log.i("ID-Name", blockedForID);
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
                returnIntent.putExtra("blockedForID", blockedForID);
                returnIntent.putExtra("favoredModulesID", favoredModulesID);
                    //String[] returnFilter = new String[]{wahlZeitraumID, locationID, blockedForID, favoredModulesID};
                    //returnIntent.putExtra("returnData", returnFilter);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
            }
        });

    }
}
