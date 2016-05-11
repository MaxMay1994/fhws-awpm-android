package prog4_projekt.awpm_android.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.io.IOException;

import prog4_projekt.awpm_android.LoginInterface;
import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.RestApi.UserData.Login;
import prog4_projekt.awpm_android.fragmente.FragmentWarningDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by florianduenow on 01.05.16.
 */
public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    static Login loginObject;
    public SharedPreferences sharedPref;
    public static String stringPwd;
    public static String stringKNummer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        setContentView(R.layout.actvity_login);

        toolbar = (Toolbar) findViewById(R.id.tollbar);
        setSupportActionBar(toolbar);
        final Button loginConfirmation = (Button) findViewById(R.id.login_confirmation);
        Log.i("001", "onCreate called in LoginActivity");

        assert loginConfirmation != null;
        loginConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MySharedPreference.getStringToken(sharedPref)== null) {

                    EditText inputKNummer = (EditText) findViewById(R.id.k_nummer);
                    Editable valueKNummer = inputKNummer.getText();
                    stringKNummer = valueKNummer.toString();
                    EditText inputPwd = (EditText) findViewById(R.id.password);
                    Editable valuePwd = inputPwd.getText();
                    stringPwd = valuePwd.toString();
                    if((stringKNummer.isEmpty() || stringPwd.isEmpty())|| stringKNummer.isEmpty() && stringPwd.isEmpty()){
                        FragmentWarningDialog dialog = new FragmentWarningDialog();
                        dialog.show(getSupportFragmentManager(), "log");
                    }
                    else {
                        try {
                            Log.i("001", "firstUse called inside LoginAktivity");

                            firstUse(stringKNummer, stringPwd, sharedPref, getSupportFragmentManager());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(MySharedPreference.getBooleanIs401(sharedPref) || MySharedPreference.getBooleanIs500(sharedPref) || MySharedPreference.getBooleanIsFailed(sharedPref)) {
                            FragmentWarningDialog dialog = new FragmentWarningDialog();
                            dialog.show(getSupportFragmentManager(), "log");
                        }
                     else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
                /*if(MySharedPreference.getStringToken(sharedPref)!= null ){
                    standardLogin(sharedPref, getSupportFragmentManager());
                }*/
            }
        });

    }

    public static void firstUse(String kNummer, String pwd, final SharedPreferences sharedPref, final FragmentManager manager) throws IOException {

        Log.i("001", "firstUse ");

            getLoginCall(makeBase64Codierung(makeSendData(kNummer, pwd))).enqueue(new Callback<Login>() {

                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {


                        if (response.code() == 200) {



                            loginObject = response.body();
                            MySharedPreference.saveStringToken(sharedPref,loginObject.getToken());
                            MySharedPreference.saveBooleanIsLoged(sharedPref, true);
                            MySharedPreference.saveBooleanIs401(sharedPref,false);
                            MySharedPreference.saveBooleanIs500(sharedPref,false);
                            MySharedPreference.saveBooleanIsFailed(sharedPref,false);

                            Log.i("001", response.message() + " " + response.code());

                        }


                    if (response.code() == 401) {
                        Log.i("001", response.message() + " " + response.code());

                        MySharedPreference.saveBooleanIs401(sharedPref,true);
                    }

                    if (response.code() == 500) {
                        MySharedPreference.saveBooleanIs500(sharedPref,true);

                        Log.i("001", response.message() + " " + response.code());

                    }



                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    Log.i("001", "login fail " + t.getMessage());

                    MySharedPreference.saveBooleanIsFailed(sharedPref,true);

                }
            });

    }

   /* public static void standardLogin(final SharedPreferences sharedPref, final FragmentManager manager){
        Log.i("001", "standardLogin ");
        Log.i("001", "savedBooleanIsLoged "+MySharedPreference.getBooleanIsLoged(sharedPref));
        Log.i("001", "savedToken "+MySharedPreference.getStringToken(sharedPref));

        //getLoginCall(MySharedPreference.getStringToken(sharedPref)).enqueue(new Callback<Login>() {

        getLoginCallWithToken(makeBase64Codierung(makeTokenSendDataLogout(MySharedPreference.getStringToken(sharedPref)))).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response ) {
                if (response.isSuccessful()) {
                    MySharedPreference.saveBooleanIs401(sharedPref,false);
                    MySharedPreference.saveBooleanIs500(sharedPref,false);
                    MySharedPreference.saveBooleanIsFailed(sharedPref,false);
                    //speichern des Boolean fuer isLogged
                    MySharedPreference.saveBooleanIsLoged(sharedPref, true);
                }
                if (response.code() == 401) {
                    MySharedPreference.saveBooleanIs401(sharedPref,true);

                }
                if(response.code() == 500){
                    MySharedPreference.saveBooleanIs500(sharedPref,true);

                }
                Log.i("001", response.message() + " " + response.code());

            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                MySharedPreference.saveBooleanIsFailed(sharedPref,true);

                Log.i("001", "login False");
            }
        });
    }
*/
    public static void  userLogout(final SharedPreferences sharedPref, final FragmentManager manager){
        getLogoutCall(makeBase64Codierung(makeTokenSendDataLogout(MySharedPreference.getStringToken(sharedPref)))).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.code() == 204) {
                    MySharedPreference.saveBooleanIs401(sharedPref,false);
                    MySharedPreference.saveBooleanIs500(sharedPref,false);
                    MySharedPreference.saveBooleanIsFailed(sharedPref,false);
                    MySharedPreference.saveBooleanIsLoged(sharedPref, false);
                    MySharedPreference.saveStringToken(sharedPref, null);


                }
                if(response.code() == 401){
                    Log.i("001", response.message()+" "+response.code());
                    MySharedPreference.saveBooleanIs401(sharedPref,true);

                }
                if(response.code() == 500){
                    Log.i("001", response.message()+" "+response.code());
                    MySharedPreference.saveBooleanIs500(sharedPref,true);

                }

            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                MySharedPreference.saveBooleanIsFailed(sharedPref,true);

                Log.i("001", "logout False"+ t.getMessage());
            }
        });
    }
    //base64 Codierung des Token
    public static String makeBase64Codierung(String stringToEncode){
        String encoded = Base64.encodeToString(stringToEncode.getBytes(), Base64.NO_WRAP);
        Log.i("001", "makeBase64Codierung von ="+encoded);

        return encoded;
    }
    //erstellt den zu verschluesselnden String
    public static String makeSendData(String nr, String pass){
        Log.i("001", "makeSendData");

        String sendData;

        sendData = nr+":"+pass;

        return sendData;
    }
    /*
    public static String makeTokenSendDataLogin(String token){
        return token;
    }
    */
    public static String makeTokenSendDataLogout(String token){
        return token+":";
    }

    //erstellt den Call
    public static Call<Login> getLoginCall(String stringToSend){
        Log.i("001", "getLoginCall");

        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.authenticate("Basic " + stringToSend);

        return service;
    }
   /* public static Call<Login> getLoginCallWithToken(String stringToSend){
        Log.i("001", "getLoginCallWithToken");

        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.authenticate("Basic "+stringToSend);

        return service;
    }
    */
    public static Call<Login> getLogoutCall(String stringToSend){
        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.logout("Basic "+stringToSend);
        return service;
    }

    public static String getStringKNummer(){
        return stringKNummer;
    }
    public static String getStringPwd(){
        return stringPwd;
    }


}