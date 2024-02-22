package afluex.parent.careermitra.model.response.responseNotification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstNotification {


    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("NotificationId")
    @Expose
    private String notificationId;
    @SerializedName("Link")
    @Expose
    private String link;
    @SerializedName("Image")
    @Expose
    private String Image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
