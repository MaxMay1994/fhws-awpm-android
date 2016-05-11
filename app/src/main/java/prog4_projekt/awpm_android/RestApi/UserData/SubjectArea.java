package prog4_projekt.awpm_android.RestApi.UserData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by florianduenow on 27.04.16.
 */
public class SubjectArea {

    @SerializedName("name")
    @Expose
    private String name;



    @SerializedName("id")
    @Expose
    private String id;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }
    /**
     *
     * @param id
     * The name
     */
    public void setId(String id) {
        this.id = id;
    }

}