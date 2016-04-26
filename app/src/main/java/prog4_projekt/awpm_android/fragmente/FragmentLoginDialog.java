package prog4_projekt.awpm_android.fragmente;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import prog4_projekt.awpm_android.R;

/**
 * Created by florianduenow on 19.04.16.
 */
public class FragmentLoginDialog extends DialogFragment {
    View view;

   /* @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_login, null));


        return builder.create();

    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_login, null);
        final Button loginConfirmation = (Button) view.findViewById(R.id.login_confirmation);
        loginConfirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginConfirmation.setText("BumBum");
            }
        });
        Button loginCancel = (Button) view.findViewById(R.id.login_cancel);
        loginCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }
public void onViewCreated(View view, Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.dialog_login, null));
    builder.create();

}

}
