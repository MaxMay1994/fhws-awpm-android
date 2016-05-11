package prog4_projekt.awpm_android;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by florianduenow on 01.05.16.
 */
public class MySharedPreference {

    public static final String tokenValue = "Token";
    public static String isLoged;



    public static String is401 ;
    public static String is500 ;
    public static String isFailed;





    public static void saveStringToken(SharedPreferences pref, String text) {

        SharedPreferences.Editor editor = pref.edit();

        editor.putString(tokenValue, text);
        editor.commit();

        Log.i("002", "savedStringToken= "+ text);


    }
    public static void saveBooleanIsLoged(SharedPreferences pref, boolean status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(isLoged, status);

        editor.commit();

    }
    public static void saveBooleanIs401(SharedPreferences pref, boolean status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(String.valueOf(is401), status);

        editor.commit();

    }
    public static void saveBooleanIs500(SharedPreferences pref, boolean status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(String.valueOf(is500), status);

        editor.commit();

    }
    public static void saveBooleanIsFailed(SharedPreferences pref, boolean status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(String.valueOf(isFailed), status);

        editor.commit();

    }
    public static String getStringToken(SharedPreferences pref) {

       return pref.getString(tokenValue, null);
    }
    public static boolean getBooleanIsLoged(SharedPreferences pref){
        boolean status;

        status = pref.getBoolean(isLoged, false);
        return status;
    }
    public static boolean getBooleanIs401(SharedPreferences pref){
        boolean status;

        status = pref.getBoolean(is401, false);
        return status;
    }
    public static boolean getBooleanIs500(SharedPreferences pref){
        boolean status;

        status = pref.getBoolean(is500, false);
        return status;
    }
    public static boolean getBooleanIsFailed(SharedPreferences pref){
        boolean status;

        status = pref.getBoolean(isFailed, false);
        return status;
    }
}
