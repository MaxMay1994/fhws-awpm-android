package prog4_projekt.awpm_android;

import prog4_projekt.awpm_android.RestApi.UserData.Login;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by florianduenow on 27.04.16.
 */
public interface LoginInterface {
    @POST("/api/login")
    Call<Login> authenticate(@Header("Authorization") String authorisation);
    @POST("/api/logout")
    Call<Login> logout(@Header("Authorization") String authorisation);
}
