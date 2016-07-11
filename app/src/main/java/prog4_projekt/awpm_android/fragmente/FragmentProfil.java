package prog4_projekt.awpm_android.fragmente;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import prog4_projekt.awpm_android.MD5Util;
import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.Module.Module;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.RestApi.UserData.Login;
import prog4_projekt.awpm_android.RestApi.UserData.User;
import prog4_projekt.awpm_android.activities.MainActivity;
import prog4_projekt.awpm_android.adapter.RecyclerViewAdapter;
import prog4_projekt.awpm_android.adapter.ViewPaperAdapterMainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Headers;


public class FragmentProfil extends Fragment {

    View view;
    View mainview;
    CardView card;
    CircleImageView image;

    TextView name;
    TextView subjectArea;
    TextView kNumber;
    TextView email;
    TextView acceptedTitle;
    TextView emptyTitle;
    TextView emptySubtitle;
    ImageButton logoutButton;
    RecyclerView recyclerViewA;
    List<Module> aList;
    RecyclerViewAdapter adapter;
    okhttp3.Headers headers;
    static int page = 1;
    static int perPage = 10;
    static String header = null;
    Call callUser;
    Call callAList;
    Call callPage;
    SharedPreferences sharedPref;
    User user;
    NestedScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profil2, null);
        mainview = inflater.inflate(R.layout.activity_main, null);
        card = (CardView) view.findViewById(R.id.card_view_profil);
        image = (CircleImageView) view.findViewById(R.id.card_view_profil_imageViewAvatar);
        name = (TextView) view.findViewById(R.id.card_view_profil_name);
        subjectArea = (TextView) view.findViewById(R.id.card_view_profil_subject);
        kNumber = (TextView) view.findViewById(R.id.card_view_profil_knumber);
        email = (TextView) view.findViewById(R.id.card_view_profil_email);
        emptyTitle = (TextView) view.findViewById(R.id.profil_empty);
        emptySubtitle = (TextView) view.findViewById(R.id.profil_empty_subtitle);
        logoutButton = (ImageButton) view.findViewById(R.id.card_view_profil_logout_button);
        recyclerViewA = (RecyclerView) view.findViewById(R.id.recyclerViewA);
        recyclerViewA.setLayoutManager(new LinearLayoutManager(getContext()));
        acceptedTitle = (TextView) view.findViewById(R.id.profil_a_title);
        scrollView = (NestedScrollView) view.findViewById(R.id.profil_scrollView);
        return view;

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewA.setNestedScrollingEnabled(false);

        Ion.with(image)
                .placeholder(R.mipmap.ic_launcher)
                .resize(64, 64);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());


        final String authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);

        callUser = ServiceAdapter.getService().getWhoAmI(authorization);
        callUser.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if (response.code() == 200) {
                    user = response.body().getUser();
                    String userName = user.getFirst_name() + " " + user.getLast_name();
                    String userSubject = subjectArea.getText() + " " + user.getSubjectArea().getName();
                    String userKNumber = kNumber.getText() + " " + user.getName();
                    String userEmail = email.getText() + " " + user.getEmail();

                    Ion.with(image)
                            .placeholder(R.mipmap.ic_launcher)
                            .resize(64, 64)
                            .load(user.getAvatar());

                    name.setText(userName);
                    subjectArea.setText(userSubject);
                    kNumber.setText(userKNumber);
                    email.setText(userEmail);

                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });


        callAList = ServiceAdapter.getService().getAccepted(true, authorization);
        callAList.enqueue(new Callback<List<Module>>() {
            @Override
            public void onResponse(Call<List<Module>> call, Response<List<Module>> response) {


                //headers = response.headers();
                //getNext(headers.get("Link"));
                //header = headers.get("Link");
                //Log.e("headers", headers.get("Link"));

                if (response.code() == 200) {
                    aList = response.body();
                    adapter = new RecyclerViewAdapter(getActivity(), aList);
                    recyclerViewA.setAdapter(adapter);

                    if (response.body().size() == 0) {
                        acceptedTitle.setVisibility(View.GONE);
                        emptyTitle.setVisibility(View.VISIBLE);
                        emptySubtitle.setVisibility(View.VISIBLE);


                    }

                }


            }

            @Override
            public void onFailure(Call<List<Module>> call, Throwable t) {

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLoginDialog.userLogout(sharedPref, FragmentProfil.this.getActivity());
            }
        });


    }

}