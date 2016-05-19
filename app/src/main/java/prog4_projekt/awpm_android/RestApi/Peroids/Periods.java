package prog4_projekt.awpm_android.RestApi.Peroids;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by florianduenow on 18.05.16.
 */
public class Periods implements Serializable {
    @SerializedName("active")
    @Expose
    private boolean active;
    @SerializedName("paused")
    @Expose
    private boolean paused;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("locations")
    @Expose
    private List<Locations> locations  = new ArrayList<Locations>();
    @SerializedName("end")
    @Expose
    private Date end;
    @SerializedName("start")
    @Expose
    private Date start;
    /**
     *
     * @return
     *     active
     */
    public boolean isActive() {
        return active;
    }

    /**
     *
     * @param active
     *
     */
    public void setActive(boolean active) {
        this.active = active;
    }
    /**
     *
     * @return
     *     paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     *
     * @param paused
     *
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    /**
     *
     * @return
     *     id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     *
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     *
     * @return
     *     locations
     */
    public List<Locations> getLocations() {
        return locations;
    }

    /**
     *
     * @param locations
     *
     */
    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }
    /**
     *
     * @return
     *     end
     */
    public Date getEnd() {
        return end;
    }

    /**
     *
     * @param end
     *
     */
    public void setEnd(Date end) {
        this.end = end;
    }
    /**
     *
     * @return
     *     start
     */
    public Date getStart() {
        return start;
    }

    /**
     *
     * @param start
     *
     */
    public void setStart(Date start) {
        this.start = start;
    }

}