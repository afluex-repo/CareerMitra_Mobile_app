package afluex.parent.careermitra.model.response.responseRecentJob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJobList {
    @SerializedName("TotalRecords")
    @Expose
    private String totalRecords;
    @SerializedName("ListJob")
    @Expose
    private List<RecentJobList> listJob = null;

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<RecentJobList> getRecentJob() {
        return listJob;
    }

    public void setListJob(List<RecentJobList> listJob) {
        this.listJob = listJob;
    }
}
