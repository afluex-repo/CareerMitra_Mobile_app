package afluex.parent.careermitra.model.response.skills;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSkills{

    @SerializedName("Skills")
    private List<SkillsItem> skills;

    public List<SkillsItem> getSkills(){
        return skills;
    }
}