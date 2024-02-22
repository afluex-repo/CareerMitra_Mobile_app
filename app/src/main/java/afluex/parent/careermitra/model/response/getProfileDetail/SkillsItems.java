package afluex.parent.careermitra.model.response.getProfileDetail;

import com.google.gson.annotations.SerializedName;

public class SkillsItems {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}