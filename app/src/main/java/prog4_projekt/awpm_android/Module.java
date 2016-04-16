package prog4_projekt.awpm_android;


import java.sql.Time;
import java.util.Date;


/**
 * Created by Ich on 16.04.2016.
 */
public class Module {

    private String name;
    private String location;
    private boolean favorite;
    private int id;
    private Time time;
    private Date startDate;

    public Module(int id, String name, String location, boolean favorite, Time time, Date startDate){
        setId(id);
        setName(name);
        setLocation(location);
        setFavorite(favorite);
        setTime(time);
        setStartDate(startDate);

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Module changeFavoriteStatus(Module module){
        if(module.isFavorite()) module.setFavorite(false);
        else module.setFavorite(true);
        return module;
    }



}
