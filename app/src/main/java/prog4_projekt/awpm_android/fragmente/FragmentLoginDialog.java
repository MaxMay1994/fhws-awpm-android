package prog4_projekt.awpm_android.fragmente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

import prog4_projekt.awpm_android.LoginInterface;
import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.RestApi.UserData.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by florianduenow on 19.04.16.
 */
public class FragmentLoginDialog extends DialogFragment {
    View view;
    public static String stringPwd = null;
    public static String stringKNummer = null;
    public SharedPreferences sharedPref;
    public static Login loginObject;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_login, null);
        final Button loginConfirmation = (Button) view.findViewById(R.id.login_confirmation);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        loginConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText inputKNummer = (EditText) view.findViewById(R.id.k_nummer);
                Editable valueKNummer = inputKNummer.getText();
                setStringKNummer(valueKNummer.toString());
                EditText inputPwd = (EditText) view.findViewById(R.id.password);
                Editable valuePwd = inputPwd.getText();
                setStringPwd(valuePwd.toString());
                if (stringKNummer.isEmpty() && stringPwd.isEmpty()) {
                    makeToast(makeToastView(getString(R.string.missinBoth), getActivity()),getActivity());
                }
                if (stringKNummer.isEmpty() && !stringPwd.isEmpty()) {
                    makeToast(makeToastView(getString(R.string.missingKNum), getActivity()),getActivity());
                }
                if (!stringKNummer.isEmpty() && stringPwd.isEmpty()) {
                    makeToast(makeToastView(getString(R.string.missingPwd), getActivity()),getActivity());
                }
                if (!stringKNummer.isEmpty() && !stringPwd.isEmpty()) {
                    try {
                        login(stringKNummer, stringPwd, sharedPref, getActivity(), FragmentLoginDialog.this);
                        inputKNummer.setText(null);
                        inputPwd.setText(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        Button loginCancel = (Button) view.findViewById(R.id.login_cancel);
        loginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoginDialog.this.dismiss();
            }
        });
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_login, null));
        builder.create();
    }

    public static String getKEingabe() {
        return stringKNummer;
    }

    public void setStringKNummer(String string) {
        stringKNummer = string;
    }

    public static String getPwdEingabe() {
        return stringPwd;
    }

    public void setStringPwd(String string) {
        stringPwd = string;
    }


    public static void makeToast(View layout, Activity activity) {

        Toast toast = new Toast(activity);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    public static View makeToastView(String message,Activity activity){
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,null);

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        return layout;
    }
    public static void login(String kNummer, String pwd, final SharedPreferences sharedPref, final Activity activity, final FragmentLoginDialog dialog) throws IOException {


        getLoginCall(makeBase64Codierung(makeSendData(kNummer, pwd))).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.code() == 200){
                    Log.i("0000","in firstUse "+response.code());
                    loginObject=response.body();
                    MySharedPreference.saveBooleanIsLoged(sharedPref, true);
                    MySharedPreference.saveStringToken(sharedPref, loginObject.getToken());
                    MySharedPreference.saveDateExpiresAtAsLong(sharedPref, loginObject.getExpires_at());

                    dialog.dismiss();

                }
                if(response.code() == 401){
                    FragmentLoginDialog.makeToast(FragmentLoginDialog.makeToastView(activity.getString(R.string.unauthorizedLogin), activity), activity);
                    MySharedPreference.saveBooleanIsLoged(sharedPref, false);
                    Log.i("0000","in firstUse "+response.code());
                }
                if(response.code() == 500){
                    FragmentLoginDialog.makeToast(FragmentLoginDialog.makeToastView(activity.getString(R.string.serverError), activity), activity);
                    MySharedPreference.saveBooleanIsLoged(sharedPref, false);
                    Log.i("0000","in firstUse "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                FragmentLoginDialog.makeToast(FragmentLoginDialog.makeToastView(activity.getString(R.string.failedConnection), activity), activity);
                MySharedPreference.saveBooleanIsLoged(sharedPref, false);
            }
        });


    }
    public static void  userLogout(final SharedPreferences sharedPref, final Activity activity){
        getLogoutCall(makeBase64Codierung(makeTokenSendDataLogout(MySharedPreference.getStringToken(sharedPref)))).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                Date today = new Date();
                today.getTime();
                String str = today.toString();
                if(response.code() == 204) {
                    Log.i("0011", response.message()+" "+response.code());
                    MySharedPreference.saveBooleanIsLoged(sharedPref, false);
                    MySharedPreference.saveStringToken(sharedPref, null);
                    Log.i("123123", str +" "+ MySharedPreference.getDateExpiresAtAsLong(sharedPref));

                }
                if(response.code() == 401){
                    Log.i("0011", response.message()+" "+response.code());

                    FragmentLoginDialog.makeToast(FragmentLoginDialog.makeToastView(activity.getString(R.string.unauthorizedLogin), activity), activity);

                }
                if(response.code() == 500){
                    Log.i("0011", response.message()+" "+response.code());
                    FragmentLoginDialog.makeToast(FragmentLoginDialog.makeToastView(activity.getString(R.string.serverError), activity), activity);
                }
            }
            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                FragmentLoginDialog.makeToast(FragmentLoginDialog.makeToastView(activity.getString(R.string.failedConnection), activity), activity);
                Log.i("0011", "logout False"+ t.getMessage());
            }
        });
    }
    //base64 Codierung des Token
    public static String makeBase64Codierung(String stringToEncode){
        String encoded = Base64.encodeToString(stringToEncode.getBytes(), Base64.NO_WRAP);
        return encoded;
    }
    //erstellt den zu verschluesselnden String
    public static String makeSendData(String nr, String pass){
        String sendData;
        sendData = nr+":"+pass;
        return sendData;
    }

    public static String makeTokenSendDataLogout(String token){
        return token+":";
    }

    //erstellt den Call
    public static Call<Login> getLoginCall(String stringToSend){
        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.authenticate("Basic " + stringToSend);
        return service;
    }
    public static Call<Login> getLogoutCall(String stringToSend){
        LoginInterface log = ServiceAdapter.getLoginService();
        Call<Login> service = log.logout("Basic "+stringToSend);
        return service;
    }



}