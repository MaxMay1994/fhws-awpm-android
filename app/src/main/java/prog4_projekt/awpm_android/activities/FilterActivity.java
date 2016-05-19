package prog4_projekt.awpm_android.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.fragmente.FragmentCourses;

public class FilterActivity extends AppCompatActivity {

    int wahlZeitraumID = 0;
    int locationID = 0;
    int blockedForID = 0;
    int votedModulesID = 0;
    CheckBox wahlZeitraum;
    CheckBox location;
    CheckBox blockedFor;
    CheckBox votedModules;
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
        votedModules = (CheckBox) findViewById(R.id.favoriteModulesFilter);
        returnFromFilter = (Button) findViewById(R.id.return_filter);
        returnFromFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                        if (i == position) location.setChecked(true);
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

        spinnerFilter2 = (Spinner) findViewById(R.id.spinner2);
        final String[] itemsFilter2 = new String[]{"Wählen", "mich", "BIN", "BWI"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsFilter2);
        spinnerFilter2.setAdapter(adapter2);
        spinnerFilter2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    for (int i = 1; i < itemsFilter2.length; i++) {
                        if (i == position) blockedFor.setChecked(true);
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
}
