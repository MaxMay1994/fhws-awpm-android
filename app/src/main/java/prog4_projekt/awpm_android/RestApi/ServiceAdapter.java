package prog4_projekt.awpm_android.RestApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAdapter {

    Retrofit retrofit;
    static ModulesApi modulesApi;
    static ServiceAdapter serviceAdapter;

    private ServiceAdapter(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://awpm.kraus.xyz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        modulesApi = retrofit.create(ModulesApi.class);

    }

    public static ModulesApi getService(){
        if(serviceAdapter == null) {
            serviceAdapter =  new ServiceAdapter();
        }
        return modulesApi;
    }




}
