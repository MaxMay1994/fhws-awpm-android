package prog4_projekt.awpm_android.fragmente;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.activities.LoginActivity;


/**
 * Created by florianduenow on 19.04.16.
 */
public class FragmentWarningDialog extends DialogFragment {
    View view;

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_warning, null));
        builder.create();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_warning, null);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String con = String.valueOf(getContext());
        Log.i("003", "context bei aufruf = "+con);

        if (String.valueOf(getContext()).startsWith("prog4_projekt.awpm_android.activities.LoginActivity")) {
            if(LoginActivity.stringKNummer.isEmpty() && LoginActivity.stringPwd.isEmpty()) {
                missingInputBothWarning();
                return view;
            }
            if(LoginActivity.stringKNummer.isEmpty() && !LoginActivity.stringPwd.isEmpty()) {
                missingInputKNummerWarning();
                return view;
            }
            if(!LoginActivity.stringKNummer.isEmpty() && LoginActivity.stringPwd.isEmpty()) {
                missingInputPwdWarning();
                return view;
            }
            if(MySharedPreference.getBooleanIs401(sharedPref).equals("true")){
                unauthorizedLogin();
                return view;
            }
            if(MySharedPreference.getBooleanIs500(sharedPref)){
                serverWarning();
                return view;

            }if(MySharedPreference.getBooleanIsFailed(sharedPref)){
                connectionFailed();
                return view;
            }

        }
        if (String.valueOf(getContext()).startsWith("prog4_projekt.awpm_android.activities.MainActivity")) {
            if(FragmentLoginDialog.stringKNummer.isEmpty() && FragmentLoginDialog.stringPwd.isEmpty()) {
                missingInputBothWarning();
                Log.i("003", "");

                return view;
            }
            if(FragmentLoginDialog.stringKNummer.isEmpty() && !FragmentLoginDialog.stringPwd.isEmpty()) {
                missingInputKNummerWarning();
                Log.i("003", "missingInput");

                return view;
            }
            if(!FragmentLoginDialog.stringKNummer.isEmpty() && FragmentLoginDialog.stringPwd.isEmpty()) {
                missingInputPwdWarning();
                Log.i("003", "missingInputPwd");

                return view;
            }
        }


            return view;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    public void serverWarning(){
        TextView v = (TextView) view.findViewById(R.id.wrong);
        v.setText(R.string.serverError);
    }
    public void connectionFailed(){
        TextView v = (TextView) view.findViewById(R.id.wrong);
        v.setText(R.string.failedConnection);
    }
    public void unauthorizedLogin(){
        TextView v = (TextView) view.findViewById(R.id.wrong);
        v.setText(R.string.unauthorizedLogin);
    }
    public void missingInputBothWarning(){
        TextView v = (TextView) view.findViewById(R.id.wrong);
        v.setText(R.string.missinBoth);
    }
    public void missingInputPwdWarning(){
        TextView v = (TextView) view.findViewById(R.id.wrong);
        v.setText(R.string.missingPwd);
    }public void missingInputKNummerWarning(){
        TextView v = (TextView) view.findViewById(R.id.wrong);
        v.setText(R.string.missingKNum);
    }
}
