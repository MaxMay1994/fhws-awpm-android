package prog4_projekt.awpm_android;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by florianduenow on 01.05.16.
 */
public class MySharedPreference {

    public static final String tokenValue = "Token";
    public static boolean isLoged = false;



    public static String is401 = "false";
    public static String is500 = "false";
    public static String isFailed= "false";





    public static void saveStringToken(SharedPreferences pref, String text) {

        SharedPreferences.Editor editor = pref.edit();

        editor.putString(tokenValue, text);
        editor.commit();

        Log.i("002", "savedStringToken= "+ text);


    }
    public static void saveBooleanIsLoged(SharedPreferences pref, boolean status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isLoged", status);

        editor.commit();

    }
    public static void saveBooleanIs401(SharedPreferences pref, String status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(is401, status);

        editor.commit();

    }
    public static void saveBooleanIs500(SharedPreferences pref, String status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(is500, status);

        editor.commit();

    }
    public static void saveBooleanIsFailed(SharedPreferences pref, String status){

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(isFailed, status);

        editor.commit();

    }
    public static String getStringToken(SharedPreferences pref) {

       return pref.getString(tokenValue, null);
    }
    public static boolean getBooleanIsLoged(SharedPreferences pref){

        return pref.getBoolean("isLoged", false);

    }
    public static String getBooleanIs401(SharedPreferences pref){

        return pref.getString(is401, "false");
    }
    public static boolean getBooleanIs500(SharedPreferences pref){
        boolean status;

        status = Boolean.parseBoolean(pref.getString(is500, "false"));
        return status;
    }
    public static boolean getBooleanIsFailed(SharedPreferences pref){
        boolean status;

        status = Boolean.parseBoolean(pref.getString(isFailed, "false"));
        return status;
    }
}
