package prog4_projekt.awpm_android.fragmente;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.Peroids.Periods;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by florianduenow on 17.04.16.
 */
public class FragmentTimeframesWue extends android.support.v4.app.Fragment {
    View view;
    String dateString, dayString, monthString, yearString, hourString, minuteString, mainStartString, mainEndString,backupStartString, backupEndString;
    int dayInt, monthInt, yearInt, hourInt, minuteInt;
    GregorianCalendar mainStart, mainEnd, backupStart, backupEnd;
    TextView txtMainStart, txtMainEnd, txtBackupStart, txtBackupEnd;
    Button mainAktivButton, mainInaktivButton, backupAktivButton, backupInaktivButton;
    List<Periods> periodsList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timeframes_wue, null);


        generateTextViews();

        getPeriodsCall().enqueue(new Callback<List<Periods>>() {

            @Override
            public void onResponse(Call<List<Periods>> call, Response<List<Periods>> response) {
                Log.i("101", response.code() +" "+ response.message());
                if(response.code() == 200) {
                    periodsList = response.body();

                    formDateStrings(periodsList.get(0).getStart());
                    makeDateInt();
                    setMainStartTimframe(yearInt, monthInt, dayInt, hourInt, minuteInt);
                    txtMainStart.setText(dateString);
                    formDateStrings(periodsList.get(0).getEnd());
                    makeDateInt();
                    setMainEndTimeframe(yearInt, monthInt, dayInt, hourInt, minuteInt);
                    txtMainEnd.setText(dateString);
                    if (periodsList.get(0).isActive()) {
                        mainAktivButton.setVisibility(View.VISIBLE);
                        mainInaktivButton.setVisibility(View.INVISIBLE);
                    }
                    if (!periodsList.get(0).isActive()) {
                        mainAktivButton.setVisibility(View.INVISIBLE);
                        mainInaktivButton.setVisibility(View.VISIBLE);
                    }
                    Log.i("101", response.code() + " " + response.message());

                    Log.i("101", "elemente in der peroids list " + periodsList.size());
                    Log.i("101", String.valueOf(periodsList.get(0).getStart()) + " " + String.valueOf(periodsList.get(0).getEnd()));
                }

            }

            @Override
            public void onFailure(Call<List<Periods>> call, Throwable t) {
                Log.i("101", t.getMessage());

            }
        });


            //zeitfenster haupt- und nachmeldezeitraum
            //GregorianCalendar befuellen
            //WICHTIG !!! hier month = monat-1 !!!!

            setBackupStartTimeframe(2016, 2, 21, 18, 0);
            setBackupEndTimeframe(2016, 2, 22, 23, 0);

            //zeitfenster haupt- nachmeldezeitraum
            //textviews erstellen und befuellen
            setTextViews();

            //Buttons erstellen
            generateButtons();


            return view;
        }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    //erstellt buttons
    public void generateButtons(){
        mainAktivButton = (Button) view.findViewById(R.id.hauptAktivButton);
        mainInaktivButton = (Button) view.findViewById(R.id.hauptInaktivButton);
        backupAktivButton = (Button) view.findViewById(R.id.nebenAktivButton);
        backupInaktivButton = (Button) view.findViewById(R.id.nebenInaktivButton);

    }
    //erstellt aus Date() verschiedene Strings
    public void formDateStrings(Date date){
        SimpleDateFormat formDate = new SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault());
        SimpleDateFormat formDay = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat formMonth = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat formYear = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat formHour = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat formMinute = new SimpleDateFormat("mm", Locale.getDefault());

        dateString = formDate.format(date);
        dayString = formDay.format(date);
        monthString = formMonth.format(date);
        yearString = formYear.format(date);
        hourString = formHour.format(date);
        minuteString = formMinute.format(date);

    }
    //erstellt aus den DateStrings Int werte
    public void makeDateInt(){

        dayInt = Integer.parseInt(dayString);
        monthInt = Integer.parseInt(monthString);
        yearInt = Integer.parseInt(yearString);
        hourInt = Integer.parseInt(hourString);
        minuteInt = Integer.parseInt(minuteString);
    }
    //erstellt die TextViews
    public void generateTextViews(){
        txtMainStart = (TextView) view.findViewById(R.id.mainStartDate);
        txtMainEnd = (TextView) view.findViewById(R.id.mainEndDate);
        txtBackupStart = (TextView) view.findViewById(R.id.backupStartDate);
        txtBackupEnd = (TextView) view.findViewById(R.id.backupEndDate);
    }
    //setzt die Strings in die TextViews
    public void setTextViews(){
        txtBackupStart.setText(backupStartString);
        txtBackupEnd.setText(backupEndString);
    }

    //WICHTIG !!! hier month = monat-1 !!!!
    public void setMainEndTimeframe(int year,int month, int day, int hour,int minute){
        mainEnd = new GregorianCalendar();
        mainEnd.set(year,month,day,hour,minute);
        SimpleDateFormat setMainFormDate = new SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault());
        setMainFormDate.setCalendar(mainEnd);
        mainEndString = setMainFormDate.format(mainEnd.getTime());
    }
    //WICHTIG !!! hier month = monat-1 !!!!
    public void setMainStartTimframe(int year,int month, int day, int hour,int minute){
        mainStart = new GregorianCalendar();
        mainStart.set(year,month,day,hour,minute);
        SimpleDateFormat setMainFormDate = new SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault());
        setMainFormDate.setCalendar(mainStart);
        mainStartString = setMainFormDate.format(mainStart.getTime());
    }
    //WICHTIG !!! hier month = monat-1 !!!!
    public void setBackupEndTimeframe(int year,int month, int day, int hour,int minute){
        backupEnd = new GregorianCalendar();
        backupEnd.set(year,month,day,hour,minute);
        SimpleDateFormat setBackupFormDate = new SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault());
        setBackupFormDate.setCalendar(backupEnd);
        backupEndString = setBackupFormDate.format(backupEnd.getTime());
    }
    //WICHTIG !!! hier month = monat-1 !!!!
    public void setBackupStartTimeframe(int year,int month, int day, int hour,int minute){
        backupStart = new GregorianCalendar();
        backupStart.set(year,month,day,hour,minute);
        SimpleDateFormat setBackupFormDate = new SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.getDefault());
        setBackupFormDate.setCalendar(backupStart);
        backupStartString = setBackupFormDate.format(backupStart.getTime());
    }
    public static Call<List<Periods>> getPeriodsCall(){
        Call<List<Periods>> periods = ServiceAdapter.getService().getAllPeriods(false);
        return periods;
    }

}