package afluex.parent.careermitra.model.response.ResponseAppliedJob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAppliedJob {

    @SerializedName("ListJob")
    @Expose
    private List<AppliedListJob> listJob = null;

    public List<AppliedListJob> getAppliedJobList() {
        return listJob;
    }

    public void setListJob(List<AppliedListJob> listJob) {
        this.listJob = listJob;
    }
}
