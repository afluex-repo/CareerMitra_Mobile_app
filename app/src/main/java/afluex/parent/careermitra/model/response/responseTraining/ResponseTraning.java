package afluex.parent.careermitra.model.response.responseTraining;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTraning {
    @SerializedName("lstTMaterial")
    @Expose
    private List<LstTMaterial> lstTMaterial = null;

    public List<LstTMaterial> getLstTMaterial() {
        return lstTMaterial;
    }

    public void setLstTMaterial(List<LstTMaterial> lstTMaterial) {
        this.lstTMaterial = lstTMaterial;
    }
}
