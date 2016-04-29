package prog4_projekt.awpm_android.RestApi;

import java.util.List;

import prog4_projekt.awpm_android.RestApi.Modules.Modules;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ModulesApi {

    @GET("api/modules")Call<List<Modules>> getAllModules(); // für pagination @Path("page") int page


}
