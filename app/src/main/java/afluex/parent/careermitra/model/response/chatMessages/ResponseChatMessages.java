package afluex.parent.careermitra.model.response.chatMessages;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseChatMessages{

    @SerializedName("Messages")
    private List<MessagesItem> messages;

    public List<MessagesItem> getMessages(){
        return messages;
    }
}