package prog4_projekt.awpm_android;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class FragmentInformations extends Fragment {

    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_informations, null);
        //Buttons erstellen
        Button infoWue = (Button) view.findViewById(R.id.info_button_wue);
        Button infoSw = (Button) view.findViewById(R.id.infof_button_sw);
        Button zeitraum = (Button) view.findViewById(R.id.zeitraum);

        //ClickListener setzen
        infoWue.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0)
            {
                Uri uri = Uri.parse("http://fang.fhws.de/studium/allgemeinwissenschaftliche_wahlpflichtfaecher/angebote_in_wuerzburg/informationen.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        infoSw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0)
            {
                Uri uri = Uri.parse("http://fang.fhws.de/studium/allgemeinwissenschaftliche_wahlpflichtfaecher/angebote_in_schweinfurt/informationen.html");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        zeitraum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0)
            {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}