package prog4_projekt.awpm_android.RestApi.UserData;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by florianduenow on 26.04.16.
 */
public class User {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("first_name")
    @Expose
    private String first_name;
    @SerializedName("last_name")
    @Expose
    private String last_name;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("subject_area")
    @Expose
    private SubjectArea subjectArea;

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }
    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     *
     * @return
     * The first_name
     */
    public String getFirst_name() {
        return first_name;
    }
    /**
     *
     * @param first_name
     * The first_name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    /**
     *
     * @return
     * The last_name
     */
    public String getLast_name() {
        return last_name;
    }
    /**
     *
     * @param last_name
     * The last_name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
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
    /**
     *
     * @return
     * The subjectArea
     */
    public SubjectArea getSubjectArea() {
        return subjectArea;
    }
    /**
     *
     * @param subjectArea
     * The subjectArea
     */
    public void setSubjectArea(SubjectArea subjectArea) {
        this.subjectArea = subjectArea;
    }
    @Override
    public String toString(){
        return this.getFirst_name() + " "+this.getLast_name();
    }

}









