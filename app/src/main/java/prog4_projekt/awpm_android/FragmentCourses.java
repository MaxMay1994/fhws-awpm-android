package prog4_projekt.awpm_android;


import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FragmentCourses extends Fragment implements ExpandableListView.OnChildClickListener{

    private View view;
    private ExpandableListAdapter expandableListAdapter;
    private ExpandableListView exListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_courses, null);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        prepareListData();

        expandableListAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        exListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(FragmentCourses.this.getActivity(), Course_Details.class);
                startActivity(intent);
                return false;
            }

        });
        exListView.setAdapter(expandableListAdapter);




    }
    /*
    * Preparing the list data
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Sprachen");
        listDataHeader.add("Kulturwissenschaften");
        listDataHeader.add("Naturwissenschaften und Technik");
        listDataHeader.add("Politik, Recht, Wirtschaft");
        listDataHeader.add("Pädagogik, Psychologie, Sozialwissenschaften, Soft Skills");
        listDataHeader.add("Musische Fächer");
        listDataHeader.add("Fächer mit besonderem ZULAVERF");
        listDataHeader.add("Interdisziplinäre Lehrveranstaltungen");

        // Adding child data
        List<String> languages = new ArrayList<String>();
        languages.add("Arabisch A1a");
        languages.add("Chinesisch A1a");
        languages.add("Englisch - Alle");
        languages.add("Französisch - Alle");
        languages.add("Schottisch/Gälisch II");
        languages.add("Italienisch - Alle");
        languages.add("Japanisch - Alle");
        languages.add("Schwedisch A1/A2 Turbokurs");
        languages.add("Spanisch - Alle");

        List<String> culturalSciences = new ArrayList<String>();
        culturalSciences.add("Einführung in die mittelhochdeutsche Sprache");
        culturalSciences.add("Der Weg zur Moderne - Die Kunst seit 1900");
        culturalSciences.add("Philosophie des Abendlandes");
        culturalSciences.add("Brasilien heute - Politik, Wirtschaft, Kultur");

        List<String> naturalSciencesAndTechnics = new ArrayList<String>();
        naturalSciencesAndTechnics.add("Einführung in Maple");
        naturalSciencesAndTechnics.add("Einführung in Excel und Visual Basic");

        List<String> politicsLawEconomy = new ArrayList<String>();
        politicsLawEconomy.add("Staat und Verwaltung in Deutschland");
        politicsLawEconomy.add("Aus der Geschichte lernen? Erwerb von Entscheidungskompetenz");
        politicsLawEconomy.add("Unternehmensgeschichte und Unternehmenspraxis");
        politicsLawEconomy.add("Korruption in Deutschland");
        politicsLawEconomy.add("Business & Society - Grundlagen");
        politicsLawEconomy.add("Die Zukunft des Sozialstaates");
        politicsLawEconomy.add("Grundzüge des Schadenersatzrechts");
        politicsLawEconomy.add("Spannungsfeld zwischen Ökologie und Ökonomie");

        List<String> socialSciences = new ArrayList<String>();
        socialSciences.add("Interkulturelle Kompetenz");
        socialSciences.add("Angewandte Psychologie");
        socialSciences.add("Einführung in die Psychologie");
        socialSciences.add("Über die Funktion der Vernunft");
        socialSciences.add("Mediation I (Bedürfnisse klären, Konflikte lösen)");
        socialSciences.add("Mediation II (Vertiefung und Erweiterung)");




        listDataChild.put(listDataHeader.get(0), languages); // Header, Child data
        listDataChild.put(listDataHeader.get(1), culturalSciences);
        listDataChild.put(listDataHeader.get(2), naturalSciencesAndTechnics);
        listDataChild.put(listDataHeader.get(3), politicsLawEconomy);
        listDataChild.put(listDataHeader.get(4), socialSciences);
        listDataChild.put(listDataHeader.get(5), null);
        listDataChild.put(listDataHeader.get(6), null);
        listDataChild.put(listDataHeader.get(7), null);
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }
}
