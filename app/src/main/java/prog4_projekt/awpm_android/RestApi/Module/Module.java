package prog4_projekt.awpm_android.RestApi.Module;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import prog4_projekt.awpm_android.RestApi.Module.Date;

public class Module implements Serializable, Parcelable{

    @SerializedName("blocked")
    @Expose
    private boolean blocked;
    @SerializedName("blocked_for")
    @Expose
    private List<SubjectArea> blockedFor = new ArrayList<SubjectArea>();
    @SerializedName("comment")
    @Expose
    private Object comment;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("dates")
    @Expose
    private List<Date> dates = new ArrayList<Date>();
    @SerializedName("end")
    @Expose
    private String end;
    @SerializedName("exam_number")
    @Expose
    private String examNumber;
    @SerializedName("exam_type")
    @Expose
    private String examType;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("intensive")
    @Expose
    private boolean intensive;
    @SerializedName("level")
    @Expose
    private Object level;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("participants")
    @Expose
    private int participants;
    @SerializedName("period_id")
    @Expose
    private int periodId;
    @SerializedName("room")
    @Expose
    private Room room;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("tags")
    @Expose
    private List<Tag> tags = new ArrayList<Tag>();
    @SerializedName("teacher")
    @Expose
    private String teacher;
    @SerializedName("voted")
    @Expose
    private boolean voted;
    @SerializedName("votes")
    @Expose
    private int votes;
    @SerializedName("favored")
    @Expose
    private boolean favorite;
    @SerializedName("vote_position")
    @Expose
    private int votePosition;
    @SerializedName("mandatory")
    @Expose
    private boolean mandatory;
    /**
     * @return
     * mandatory
     */
    public boolean isMandatory() { return mandatory; }

    /**
     * @param mandatory
     * return mandatory
     */

    public void setMandatory(boolean mandatory) { this.mandatory = mandatory; }

    /**
     * 
     * @return
     *     The blocked
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * 
     * @param blocked
     *     The blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    /**
     * 
     * @return
     *     The blockedFor
     */
    public List<SubjectArea> getBlockedFor() {
        return blockedFor;
    }

    /**
     * 
     * @param blockedFor
     *     The blocked_for
     */
    public void setBlockedFor(List<SubjectArea> blockedFor) {
        this.blockedFor = blockedFor;
    }

    /**
     * 
     * @return
     *     The comment
     */
    public Object getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(Object comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The dates
     */
    public List<Date> getDates() {
        return dates;
    }

    /**
     * 
     * @param dates
     *     The dates
     */
    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

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
     *     The examNumber
     */
    public String getExamNumber() {
        return examNumber;
    }

    /**
     * 
     * @param examNumber
     *     The exam_number
     */
    public void setExamNumber(String examNumber) {
        this.examNumber = examNumber;
    }

    /**
     * 
     * @return
     *     The examType
     */
    public String getExamType() {
        return examType;
    }

    /**
     * 
     * @param examType
     *     The exam_type
     */
    public void setExamType(String examType) {
        this.examType = examType;
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
     *     The intensive
     */
    public boolean isIntensive() {
        return intensive;
    }

    /**
     * 
     * @param intensive
     *     The intensive
     */
    public void setIntensive(boolean intensive) {
        this.intensive = intensive;
    }

    /**
     * 
     * @return
     *     The level
     */
    public Object getLevel() {
        return level;
    }

    /**
     * 
     * @param level
     *     The level
     */
    public void setLevel(Object level) {
        this.level = level;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The participants
     */
    public int getParticipants() {
        return participants;
    }

    /**
     * 
     * @param participants
     *     The participants
     */
    public void setParticipants(int participants) {
        this.participants = participants;
    }

    /**
     * 
     * @return
     *     The periodId
     */
    public int getPeriodId() {
        return periodId;
    }

    /**
     * 
     * @param periodId
     *     The period_id
     */
    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    /**
     * 
     * @return
     *     The room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * 
     * @param room
     *     The room
     */
    public void setRoom(Room room) {
        this.room = room;
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

    /**
     * 
     * @return
     *     The tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * 
     * @param tags
     *     The tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * 
     * @return
     *     The teacher
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * 
     * @param teacher
     *     The teacher
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * 
     * @return
     *     The voted
     */
    public boolean isVoted() {
        return voted;
    }

    /**
     * 
     * @param voted
     *     The voted
     */
    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    /**
     *
     * @return
     *     The votes
     */
    public int getVoted() {
        return votes;
    }

    /**
     *
     * @param votes
     *     The votes
     */
    public void setVotes(int votes) {
        this.votes = votes;
    }


    /**
     *
     * @return
     *     The votePosition
     */
    public int getVotePosition() {
        return votePosition;
    }

    /**
     *
     * @param votePosition
     *     The vote_position
     */
    public void setVotePosition(int votePosition) {
        this.votePosition = votePosition;
    }





    /**
     *
     * @return
     * The favorite
     */
    public boolean isFavorite(){return favorite;}

    /**
     *
     * @param favorite
     *  the favorite
     */
    public void setFavorite(boolean favorite){ this.favorite = favorite; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(name);
        dest.writeString(examType);
        dest.writeInt(participants);
        dest.writeString(end);
        dest.writeString(start);
        dest.writeString(teacher);
    }
    protected Module(Parcel in){
        content = in.readString();
        name = in.readString();
        examType = in.readString();
        end = in.readString();
        start = in.readString();
        teacher = in.readString();
        participants = in.readInt();

    }
    public Module() {

    }

    public static final Creator<Module> CREATOR = new Creator<Module>() {
        @Override
        public Module createFromParcel(Parcel in) {
            return new Module(in);
        }

        @Override
        public Module[] newArray(int size) {
            return new Module[0];
        }


    };
}
