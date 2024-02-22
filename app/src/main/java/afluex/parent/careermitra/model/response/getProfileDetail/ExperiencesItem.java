package afluex.parent.careermitra.model.response.getProfileDetail;

import com.google.gson.annotations.SerializedName;

public class ExperiencesItem{

    @SerializedName("IsCurrent")
    private Boolean isCurrent;

    @SerializedName("Designation")
    private String designation;

    @SerializedName("Company")
    private String company;

    @SerializedName("YearTo")
    private String yearTo;

    @SerializedName("YearFrom")
    private String yearFrom;

    public boolean isIsCurrent(){
        return isCurrent;
    }

    public String getDesignation(){
        return designation;
    }

    public String getCompany(){
        return company;
    }

    public String getYearTo(){
        return yearTo;
    }

    public String getYearFrom(){
        return yearFrom;
    }
}