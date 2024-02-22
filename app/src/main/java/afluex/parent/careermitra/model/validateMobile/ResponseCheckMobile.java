package afluex.parent.careermitra.model.validateMobile;

import com.google.gson.annotations.SerializedName;

public class ResponseCheckMobile {

    @SerializedName("IsExist")
    private Boolean isExist;

    public boolean isIsExist(){
        return isExist;
    }
}