package afluex.parent.careermitra.model.response.resopnseEnrollmentProgram;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseEnrollment {
    @SerializedName("lstProgram")
    @Expose
    private List<LstProgram> lstProgram = null;

    public List<LstProgram> getLstProgram() {
        return lstProgram;
    }

    public void setLstProgram(List<LstProgram> lstProgram) {
        this.lstProgram = lstProgram;
    }

}
