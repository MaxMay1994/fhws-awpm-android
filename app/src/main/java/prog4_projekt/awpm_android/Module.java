package prog4_projekt.awpm_android;


import android.widget.LinearLayout;

import java.sql.Time;
import java.util.Date;


/**
 * Created by Ich on 16.04.2016.
 */
public class Module {

    private String subject;
    private String lecturer;
    private boolean favorite;

    public Module(String subject, String lecturer, boolean favorite){

        this.lecturer = lecturer;
        this.subject = subject;
        this.favorite = favorite;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


    private Module changeFavoriteStatus(Module module){
        if(module.isFavorite()) module.setFavorite(false);
        else module.setFavorite(true);
        return module;
    }



}
