package afluex.parent.careermitra.model.response.recruitersListMessage;

import com.google.gson.annotations.SerializedName;

public class MessagesItem{

    @SerializedName("EmployerId")
    private Integer employerId;

    @SerializedName("LastMessage")
    private String lastMessage;

    @SerializedName("UnReadCount")
    private String unReadCount;

    @SerializedName("IsUnread")
    private Boolean isUnread;

    @SerializedName("Id")
    private String id;

    @SerializedName("Job")
    private String job;

    @SerializedName("EmployerName")
    private String employerName;

    @SerializedName("Date")
    private String date;

    public Integer getEmployerId(){
        return employerId;
    }

    public String getLastMessage(){
        return lastMessage;
    }

    public String getUnReadCount(){
        return unReadCount;
    }

    public boolean isIsUnread(){
        return isUnread;
    }

    public String getId(){
        return id;
    }

    public String getJob(){
        return job;
    }

    public String getEmployerName(){
        return employerName;
    }

    public String getDate(){
        return date;
    }
}