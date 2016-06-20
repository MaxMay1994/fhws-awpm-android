package prog4_projekt.awpm_android.fragmente;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;

public class FragmentLogin extends Fragment {

    View view;
    EditText knumber;
    EditText password;
    Button loginButton;
    public static String stringPwd = null;
    public static String stringKNumber = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_login, null);
        knumber = (EditText) view.findViewById(R.id.fragment_login_knumber);
        password = (EditText) view.findViewById(R.id.fragment_login_password);
        knumber = (EditText) view.findViewById(R.id.fragment_login_knumber);
        loginButton = (Button) view.findViewById(R.id.fragment_login_confirmation);



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable valueKNummer = knumber.getText();
                stringKNumber = valueKNummer.toString();
                Editable valuePwd = password.getText();
                stringPwd = valuePwd.toString();


                if (stringKNumber.isEmpty() && stringPwd.isEmpty()) {
                    Toast.makeText(FragmentLogin.this.getContext(),R.string.missinBoth,Toast.LENGTH_LONG).show();
                }
                if (stringKNumber.isEmpty() && !stringPwd.isEmpty()) {
                    Toast.makeText(FragmentLogin.this.getContext(),R.string.missingKNum,Toast.LENGTH_LONG).show();
                }
                if (!stringKNumber.isEmpty() && stringPwd.isEmpty()) {
                    Toast.makeText(FragmentLogin.this.getContext(),R.string.missingPwd,Toast.LENGTH_LONG).show();
                }
                if (!stringKNumber.isEmpty() && !stringPwd.isEmpty()) {
                    try {
                        FragmentLoginDialog.login(stringKNumber, stringPwd, PreferenceManager.getDefaultSharedPreferences(getActivity()), getActivity(),FragmentLogin.this);

                        knumber.setText(null);
                        password.setText(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });


    }

}
