package prog4_projekt.awpm_android.RestApi.UserData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by florianduenow on 27.04.16.
 */
public class Login {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("expires_at")
    @Expose
    private String expires_at;
    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     *
     * @return
     * The expires_at
     */
    public String getExpires_at() {
        return expires_at;
    }
    /**
     *
     * @param expires_at
     * The expires_at
     */
    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }

    @Override
    public String toString(){


        return "ausgabe LoginObjekt "+this.getExpires_at()+"  "+this.getToken();
    }

}
