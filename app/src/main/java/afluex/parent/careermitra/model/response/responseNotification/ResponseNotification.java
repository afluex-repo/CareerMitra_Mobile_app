package afluex.parent.careermitra.model.response.responseNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseNotification {

    @SerializedName("lstNotification")
    @Expose
    private List<LstNotification> lstNotification = null;

    public List<LstNotification> getLstNotification() {
        return lstNotification;
    }

    public void setLstNotification(List<LstNotification> lstNotification) {
        this.lstNotification = lstNotification;
    }
}
