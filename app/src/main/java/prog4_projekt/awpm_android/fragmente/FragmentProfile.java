package prog4_projekt.awpm_android.fragmente;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.SimpleItemTouchHelperCallback;
import prog4_projekt.awpm_android.adapter.RecyclerViewAdapterBallot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {

    View view;
    RecyclerView recyclerViewC;
    RecyclerViewAdapterBallot adapter;
    List<Module> votedList;
    Call<List<Module>> call;
    TextView nullVoted;
    TextView sad;
    SharedPreferences sharedPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, null);
        nullVoted = (TextView) view.findViewById(R.id.textView_null_voted);
        sad = (TextView) view.findViewById(R.id.textView_sad);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);

        recyclerViewC = (RecyclerView) view.findViewById(R.id.recyclerViewC);
        recyclerViewC.setLayoutManager(new LinearLayoutManager(getContext()));

        call = ServiceAdapter.getService().getVotedModules(true, authorization);
        call.enqueue(new Callback<List<Module>>() {

            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {

                if(response.code() == 200) {

                    votedList = response.body();
                    adapter = new RecyclerViewAdapterBallot(getActivity(), votedList);
                    recyclerViewC.setAdapter(adapter);

                    if (response.body().size() == 0) {

                        nullVoted.setVisibility(View.VISIBLE);
                        sad.setVisibility(View.VISIBLE);

                    }

                    ItemTouchHelper.Callback callback =
                            new SimpleItemTouchHelperCallback(adapter);
                    ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerViewC);

                }
                if(response.code() == 401){

                    nullVoted.setText("Bitte Logen Sie sich ein");
                    nullVoted.setVisibility(View.VISIBLE);
                    sad.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {



            }
        });

    }

}
