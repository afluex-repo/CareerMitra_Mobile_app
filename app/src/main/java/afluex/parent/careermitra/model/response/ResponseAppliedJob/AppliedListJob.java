package afluex.parent.careermitra.model.response.ResponseAppliedJob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppliedListJob {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("Company")
    @Expose
    private String company;
    @SerializedName("Date")
    @Expose
    private String date;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
