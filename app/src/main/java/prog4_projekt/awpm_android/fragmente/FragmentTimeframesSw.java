package prog4_projekt.awpm_android.fragmente;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import prog4_projekt.awpm_android.R;

/**
 * Created by florianduenow on 17.04.16.
 */
public class FragmentTimeframesSw extends android.support.v4.app.Fragment {
    View view;
    String dateString, dayString, monthString, yearString, hourString, minuteString, mainStartString, mainEndString,backupStartString, backupEndString;
    int dayInt, monthInt, yearInt, hourInt, minuteInt;
    GregorianCalendar mainStart, mainEnd, backupStart, backupEnd;
    TextView txtMainStart, txtMainEnd, txtBackupStart, txtBackupEnd;
    Button mainAktivButton, mainInaktivButton, backupAktivButton, backupInaktivButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_timeframes_sw, null);

        //aktuelles datum holen, strings erstellen und in int umformen
        Date today = new Date();
        formDateStrings(today);
        makeDateInt();

        //aktuelles datum in GregorianCalendar fuellen
        Calendar g = new GregorianCalendar();
        g.set(yearInt, monthInt, dayInt, hourInt, minuteInt);

        //zeitfenster haupt- und nachmeldezeitraum
        //GregorianCalendar befuellen
        //WICHTIG !!! hier month = monat-1 !!!!
        setMainStartTimframe(2016,2,15,0,0);
        setMainEndTimeframe(2016,2,18,14,0);
        setBackupStartTimeframe(2016,2,22,14,0);
        setBackupEndTimeframe(2016,3,22,12,0);


        //zeitfenster haupt- nachmeldezeitraum
        //textviews erstellen und befuellen
        generateTextViews();
        setTextViews();

        //Buttons erstellen
        generateButtons();

        if(g.compareTo(mainStart) >= 0 && g.compareTo(mainEnd) <=0)
        {
            mainAktivButton.setVisibility(View.VISIBLE);
            mainInaktivButton.setVisibility(View.INVISIBLE);
        }
        if(g.compareTo(backupStart) >= 0 && g.compareTo(backupEnd) <=0)
        {
            backupInaktivButton.setVisibility(View.INVISIBLE);
            backupAktivButton.setVisibility(View.VISIBLE);
        }
        else{
            mainAktivButton.setVisibility(View.INVISIBLE);
            backupAktivButton.setVisibility(View.INVISIBLE);
            mainInaktivButton.setVisibility(View.VISIBLE);
            backupInaktivButton.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    //erstellt Buttons
    public void generateButtons(){
        mainAktivButton = (Button) view.findViewById(R.id.hauptAktivButton);
        mainInaktivButton = (Button) view.findViewById(R.id.hauptInaktivButton);
        backupAktivButton = (Button) view.findViewById(R.id.nebenAktivButton);
        backupInaktivButton = (Button) view.findViewById(R.id.nebenInaktivButton);

    }
    //erstellt DateStrings aus Date()
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
    //erstellt Int werte aus den DateStrings
    public void makeDateInt(){
        dayInt = Integer.parseInt(dayString);
        monthInt = Integer.parseInt(monthString);
        yearInt = Integer.parseInt(yearString);
        hourInt = Integer.parseInt(hourString);
        minuteInt = Integer.parseInt(minuteString);
    }
    //erstellt Textviews
    public void generateTextViews(){
        txtMainStart = (TextView) view.findViewById(R.id.mainStartDate);
        txtMainEnd = (TextView) view.findViewById(R.id.mainEndDate);
        txtBackupStart = (TextView) view.findViewById(R.id.backupStartDate);
        txtBackupEnd = (TextView) view.findViewById(R.id.backupEndDate);
    }
    //setzt die DateStrings in die TextViews
    public void setTextViews(){
        txtMainStart.setText(mainStartString);
        txtMainEnd.setText(mainEndString);
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
}