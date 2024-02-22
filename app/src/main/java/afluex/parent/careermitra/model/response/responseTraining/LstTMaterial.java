package afluex.parent.careermitra.model.response.responseTraining;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstTMaterial {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Link")
    @Expose
    private String link;
    @SerializedName("Attachment")
    @Expose
    private String attachment;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("PublishDate")
    @Expose
    private String publishDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String   getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

}
