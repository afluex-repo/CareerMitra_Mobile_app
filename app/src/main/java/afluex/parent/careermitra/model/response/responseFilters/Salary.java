package afluex.parent.careermitra.model.response.responseFilters;

import com.google.gson.annotations.SerializedName;

public class Salary{

    @SerializedName("Min")
    private Integer min;

    @SerializedName("Max")
    private Integer max;

    public Integer getMin(){
        return min;
    }

    public Integer getMax(){
        return max;
    }
}