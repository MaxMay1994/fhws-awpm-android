package prog4_projekt.awpm_android.fragmente;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
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

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.activities.LoginActivity;

/**
 * Created by florianduenow on 19.04.16.
 */
public class FragmentLoginDialog extends DialogFragment {
    View view;
    public static String stringPwd = null;
    public static String stringKNummer = null;
    public SharedPreferences sharedPref;

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
                    makeToast(makeToastView(inflater, getString(R.string.missinBoth), getActivity()),getActivity());
                }
                if (stringKNummer.isEmpty() && !stringPwd.isEmpty()) {
                    makeToast(makeToastView(inflater, getString(R.string.missingKNum), getActivity()),getActivity());
                }
                if (!stringKNummer.isEmpty() && stringPwd.isEmpty()) {
                    makeToast(makeToastView(inflater, getString(R.string.missingPwd), getActivity()),getActivity());
                }
                if (!stringKNummer.isEmpty() && !stringPwd.isEmpty()) {


                    try {
                        LoginActivity.firstUse(stringKNummer, stringPwd, sharedPref, getFragmentManager());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    FragmentLoginDialog.this.dismiss();
                }
                synchronized (this) {

                    try {
                        this.wait(1950);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (MySharedPreference.getBooleanIs401(sharedPref)) {
                        makeToast(makeToastView(inflater, getString(R.string.unauthorizedLogin), getActivity()),getActivity());
                        MySharedPreference.saveBooleanIs401(sharedPref, false);
                    }
                    if (MySharedPreference.getBooleanIs500(sharedPref)) {
                        makeToast(makeToastView(inflater, getString(R.string.serverError), getActivity()),getActivity());
                        MySharedPreference.saveBooleanIs500(sharedPref,false);
                    }
                    if (MySharedPreference.getBooleanIsFailed(sharedPref)) {
                        makeToast(makeToastView(inflater, getString(R.string.failedConnection), getActivity()),getActivity());
                        MySharedPreference.saveBooleanIsFailed(sharedPref,false);
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
    public static View makeToastView(LayoutInflater inflater, String message,Activity activity){
        inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,null);

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);
        return layout;
    }

}