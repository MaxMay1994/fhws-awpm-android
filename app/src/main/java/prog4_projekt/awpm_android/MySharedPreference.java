package prog4_projekt.awpm_android;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Date;

/**
 * Created by florianduenow on 01.05.16.
 */
public class MySharedPreference {

    public static final String tokenValue = "Token";
    public static final String isLoged = "isLoged";
    public static final String dateLong = "dateLong";

    public static void saveStringToken(SharedPreferences pref, String text) {

        SharedPreferences.Editor editor = pref.edit();

        editor.putString(tokenValue, text);
        editor.commit();

        Log.i("002", "savedStringToken= " + text);


    }

    public static void saveBooleanIsLoged(SharedPreferences pref, boolean status) {

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(isLoged, status);

        editor.commit();

    }
    public static void saveDateExpiresAtAsLong(SharedPreferences pref, Date date){


        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(dateLong, date.getTime() );
        editor.commit();
        Log.i("123123", String.valueOf(MySharedPreference.getDateExpiresAtAsLong(pref)));

    }
    public static long getDateExpiresAtAsLong(SharedPreferences pref){
        return pref.getLong(dateLong, 0);
    }

    public static String getStringToken(SharedPreferences pref) {

        return pref.getString(tokenValue, null);
    }

    public static boolean getBooleanIsLoged(SharedPreferences pref) {

        return pref.getBoolean(isLoged, false);

    }
}