package prog4_projekt.awpm_android.RestApi.Module;

import java.util.List;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("buildings")
    @Expose
    private List<Building> buildings = new ArrayList<Building>();
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     *
     * @return
     * The buildings
     */
    public List<Building> getBuildings() {
        return buildings;
    }

    /**
     *
     * @param buildings
     * The buildings
     */
    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

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

}
