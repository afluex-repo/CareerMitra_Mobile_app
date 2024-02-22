package afluex.parent.careermitra.model.response.chatMessages;

import com.google.gson.annotations.SerializedName;

public class MessagesItem{

    @SerializedName("UserName")
    private String userName;

    @SerializedName("Message")
    private String message;

    @SerializedName("IsUnread")
    private Boolean isUnread;

    @SerializedName("SenderId")
    private String senderId;

    @SerializedName("EmployerName")
    private String employerName;

    @SerializedName("Date")
    private String date;

    public String getUserName(){
        return userName;
    }

    public String getMessage(){
        return message;
    }

    public boolean isIsUnread(){
        return isUnread;
    }

    public String getSenderId(){
        return senderId;
    }

    public String getEmployerName(){
        return employerName;
    }

    public String getDate(){
        return date;
    }
}