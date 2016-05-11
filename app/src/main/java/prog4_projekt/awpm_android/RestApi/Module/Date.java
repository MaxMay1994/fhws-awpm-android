
package prog4_projekt.awpm_android.RestApi.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Date {

    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("module_id")
    @Expose
    private int moduleId;
    @SerializedName("qr_token")
    @Expose
    private Object qrToken;
    @SerializedName("start")
    @Expose
    private String start;

    /**
     * 
     * @return
     *     The end
     */
    public String getEnd() {
        return end;
    }

    /**
     * 
     * @param end
     *     The end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The moduleId
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * 
     * @param moduleId
     *     The module_id
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * 
     * @return
     *     The qrToken
     */
    public Object getQrToken() {
        return qrToken;
    }

    /**
     * 
     * @param qrToken
     *     The qr_token
     */
    public void setQrToken(Object qrToken) {
        this.qrToken = qrToken;
    }

    /**
     * 
     * @return
     *     The start
     */
    public String getStart() {
        return start;
    }

    /**
     * 
     * @param start
     *     The start
     */
    public void setStart(String start) {
        this.start = start;
    }

}
