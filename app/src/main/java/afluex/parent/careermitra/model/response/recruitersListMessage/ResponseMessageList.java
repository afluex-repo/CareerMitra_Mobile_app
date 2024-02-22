package afluex.parent.careermitra.model.response.recruitersListMessage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseMessageList{

    @SerializedName("Messages")
    private List<MessagesItem> messages;

    public List<MessagesItem> getMessages(){
        return messages;
    }
}