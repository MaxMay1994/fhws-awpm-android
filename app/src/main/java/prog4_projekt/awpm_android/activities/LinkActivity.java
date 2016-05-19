package prog4_projekt.awpm_android.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import prog4_projekt.awpm_android.R;

public class LinkActivity extends AppCompatActivity {
    Button infoWue, infoSw;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link);
        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        infoWue = (Button) findViewById(R.id.info_button_wue);
        infoSw = (Button) findViewById(R.id.infof_button_sw);

        //ClickListener setzen
        infoWue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0)
            {
                //aufruf externer browser
                Uri uri = Uri.parse("http://fang.fhws.de/studium/allgemeinwissenschaftliche_wahlpflichtfaecher/angebote_in_wuerzburg/informationen.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        infoSw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                //aufruf externer browser
                Uri uri = Uri.parse("http://fang.fhws.de/studium/allgemeinwissenschaftliche_wahlpflichtfaecher/angebote_in_schweinfurt/informationen.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }


        });

    }
}
