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

import prog4_projekt.awpm_android.R;

public class FilterActivity extends AppCompatActivity {

    String wahlZeitraumID = "";
    String locationID = "";
    String blockedForID = "";
    String favoredModulesID = "";
    boolean wahl, loc, blocked, fav;
    CheckBox wahlZeitraum;
    CheckBox location;
    CheckBox blockedFor;
    CheckBox favoredModules;
    Button returnFromFilter;
    Spinner spinnerFilter1;
    Spinner spinnerFilter2;
    TextView toolText;

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
                }
                else wahlZeitraumID = null;
            }
        });
        favoredModules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    favoredModulesID = "favoredModules";
                }
                else favoredModulesID = null;
            }
        });


        spinnerFilter1 = (Spinner) findViewById(R.id.spinner1);
        final String[] itemsFilter1 = new String[]{"Bitte auswählen", "Schweinfurt", "Würzburg", "Münzstr.", "SHL"};
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
                locationID = null;
            }
        });

        spinnerFilter2 = (Spinner) findViewById(R.id.spinner2);
        final String[] itemsFilter2 = new String[]{"Wählen", "mich", "BIN", "BWI"};
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
                blockedForID = null;
            }
        });
        returnFromFilter = (Button) findViewById(R.id.return_filter);
        returnFromFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = getIntent();
                String[] returnFilter = new String[]{wahlZeitraumID, locationID, blockedForID, favoredModulesID};
                returnIntent.putExtra("returnData", returnFilter);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
