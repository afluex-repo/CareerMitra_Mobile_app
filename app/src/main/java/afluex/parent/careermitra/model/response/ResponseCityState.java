package afluex.parent.careermitra.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseCityState{

    @SerializedName("CityId")
    private Integer cityId;

    @SerializedName("State")
    private String state;

    @SerializedName("StateId")
    private Integer stateId;

    @SerializedName("City")
    private String city;

    public Integer getCityId(){
        return cityId;
    }

    public String getState(){
        return state;
    }

    public Integer getStateId(){
        return stateId;
    }

    public String getCity(){
        return city;
    }
}