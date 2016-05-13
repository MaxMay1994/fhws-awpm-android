package prog4_projekt.awpm_android.RestApi;

import prog4_projekt.awpm_android.LoginInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAdapter {

    private static String baseUrl = "https://awpm.kraus.xyz/";
    private static Retrofit retrofit;
    private static AwpmApi awpmApi;
    private static ServiceAdapter serviceAdapter;

    private ServiceAdapter(){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        awpmApi = retrofit.create(AwpmApi.class);

    }

    public static AwpmApi getService(){
        if(serviceAdapter == null) {
            serviceAdapter =  new ServiceAdapter();
        }
        return awpmApi;
    }

    public static LoginInterface getLoginService(){
        if(serviceAdapter == null) {
            serviceAdapter = new ServiceAdapter();
        }
        LoginInterface basicLogin = ServiceAdapter.serviceAdapter.retrofit.create(LoginInterface.class);
        return basicLogin;
    }




}
