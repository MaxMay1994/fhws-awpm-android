package prog4_projekt.awpm_android.fragmente;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.rjung.util.Gravatar;
import org.rjung.util.GravatarException;
import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Protocol;
import org.rjung.util.gravatar.Rating;

import de.hdodenhof.circleimageview.CircleImageView;
import prog4_projekt.awpm_android.MD5Util;
import prog4_projekt.awpm_android.MySharedPreference;
import prog4_projekt.awpm_android.R;
import prog4_projekt.awpm_android.RestApi.ServiceAdapter;
import prog4_projekt.awpm_android.RestApi.UserData.Login;
import prog4_projekt.awpm_android.RestApi.UserData.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentProfil extends Fragment {

    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView name;
    Call call;
    SharedPreferences sharedPref;
    Login user;
    CircleImageView avatar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profil, null);
        name = (TextView) view.findViewById(R.id.textViewProfilName);
        avatar = (CircleImageView) view.findViewById(R.id.imageViewAvatar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);





        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String authorization = "Basic " + Base64.encodeToString((MySharedPreference.getStringToken(sharedPref) + ":").getBytes(), Base64.NO_WRAP);

        call = ServiceAdapter.getService().getWhoAmI(authorization);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                if (response.code() == 200) {
                    user = (Login) response.body();
                    String userName = user.getUser().getFirst_name() + " " + user.getUser().getLast_name();

                    name.setText(userName);

                    Log.e("hash",MD5Util.md5Hex(user.getUser().getEmail()));
                    String url = "https://secure.gravatar.com/avatar/" + MD5Util.md5Hex(user.getUser().getEmail()) + "?r=g&s=400";

                    Ion.with(avatar)
                            .placeholder(R.mipmap.ic_launcher)
                            .resize(400, 400)
                            .load(url);


                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });




        /*try {
            String url = Gravatar.forEmail()
                    .with(Protocol.HTTPS)     // prepend https://
                    .size(400)                // set the size to 123 pixel
                    .defaultImage(Default.MM) // if not available show mystery man image
                    .with(Rating.G)           // set rating to X
                    .toUrl();

            Ion.with(avatar)
                    .placeholder(R.mipmap.ic_launcher)
                    .resize(400, 400)
                    .load(url);
        }catch (GravatarException ex){

        }*/






    }

}