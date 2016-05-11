package prog4_projekt.awpm_android.RestApi.UserData;

import android.util.Base64;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import prog4_projekt.awpm_android.LoginInterface;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.fragmente.FragmentInformations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by florianduenow on 26.04.16.
 */
public class User {


    @SerializedName("name")
    @Expose
    private String kNummer;
    @SerializedName("subject_area")
    @Expose
    private SubjectArea subjectArea;

    String pwd;
    String sendData;



    public String token;
    String baseCodierterToken;
    private static User appUser;

    private User(){


        this.token = "";


    }
    public static User getAppUser () throws IOException {
        if(appUser == null){
            appUser = new User();
            Log.i("100", "neuer nutzer");


        }
        return appUser;
    }

    public void firstUse(String kNummer, String pwd) throws IOException {
        this.kNummer=kNummer;
        this.pwd=pwd;
        this.sendData = ""+kNummer+":"+pwd+"";

        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.authenticate("Basic "+makeBaseCodierung(sendData));
        service.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login loginObject = response.body();

                appUser.setToken(loginObject.getToken());

                    Log.i("200", response.message());


            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.i("400", "login False");

            }
        });
    }
    public void standartLogin(){

        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.authenticate("Basic "+makeBaseCodierung(getToken()));
        service.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Login loginObject = response.body();


                FragmentInformations.setLoggedIn(FragmentInformations.isLoggedIn());
                Log.i("200", response.message());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.i("400", "login False");

            }
        });
    }

    public void  userLogout(){
        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.logout("Basic "+getBaseCodierterToken());
        service.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {


                FragmentInformations.setLoggedIn(FragmentInformations.isLoggedIn());
                Log.i("500", "logout ok");


            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.i("400", "logout False");

            }
        });
    }
    public String makeBaseCodierung(String token){
        return Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
    }
    public static String getkNummer(User appUser) {
        return appUser.kNummer;
    }

    public void setkNummer(String kNummer) {
        this.kNummer = kNummer;
    }
    public static String getPwd(User appUser) {
        return appUser.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getBaseCodierterToken() {
        return baseCodierterToken;
    }
    public String getToken(){return token;}

    public void setToken(String token) {
        this.token = token;
    }
}
