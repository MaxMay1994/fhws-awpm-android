package prog4_projekt.awpm_android.RestApi.Peroids;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by florianduenow on 18.05.16.
 */
public class Locations implements Serializable {
    @SerializedName("id")
    @Expose
    private int locationID;
    @SerializedName("name")
    @Expose
    private String locationName;

    /**
     *
     * @return
     *     id
     */
    public int getLocationID() {
        return locationID;
    }

    /**
     *
     * @param locationID
     *
     */
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }
    /**
     *
     * @return
     *     locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     *
     * @param locationName
     *
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
