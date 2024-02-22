package afluex.parent.careermitra.model.SearchJob;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSearchJob {
    @SerializedName("TotalRecords")
    @Expose
    private String totalRecords;
    @SerializedName("ListJob")
    @Expose
    private List<ListJob> listJob = null;

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<ListJob> getListJob() {
        return listJob;
    }

    public void setListJob(List<ListJob> listJob) {
        this.listJob = listJob;
    }

}
