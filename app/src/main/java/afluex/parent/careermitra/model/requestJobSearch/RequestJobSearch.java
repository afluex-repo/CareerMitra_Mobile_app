package afluex.parent.careermitra.model.requestJobSearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestJobSearch{

    @SerializedName("deviceType")
    private String deviceType;

    @SerializedName("address")
    private String address;

    @SerializedName("os")
    private String os;

    @SerializedName("city")
    private String city;

    @SerializedName("ip")
    private String ip;

    @SerializedName("language")
    private String language;

    @SerializedName("userAgent")
    private String userAgent;

    @SerializedName("cityId")
    private List<String> cityId;

    @SerializedName("deviceId")
    private String deviceId;

    @SerializedName("long")
    private String jsonMemberLong;

    @SerializedName("salaryMax")
    private String salaryMax;

    @SerializedName("skillId")
    private List<String> skillId;

    @SerializedName("employerId")
    private String employerId;

    @SerializedName("searchTerm")
    private String searchTerm;

    @SerializedName("domain")
    private String domain;

    @SerializedName("sortBy")
    private String sortBy;

    @SerializedName("page")
    private String page;

    @SerializedName("deviceOtherInfo")
    private String deviceOtherInfo;

    @SerializedName("courseId")
    private List<String> courseId;

    @SerializedName("salaryMin")
    private String salaryMin;

    @SerializedName("categoryId")
    private List<String> categoryId;

    @SerializedName("lat")
    private String lat;

    public void setDeviceType(String deviceType){
        this.deviceType = deviceType;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setOs(String os){
        this.os = os;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setIp(String ip){
        this.ip = ip;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public void setUserAgent(String userAgent){
        this.userAgent = userAgent;
    }

    public void setCityId(List<String> cityId){
        this.cityId = cityId;
    }

    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }

    public void setJsonMemberLong(String jsonMemberLong){
        this.jsonMemberLong = jsonMemberLong;
    }

    public void setSalaryMax(String salaryMax){
        this.salaryMax = salaryMax;
    }

    public void setSkillId(List<String> skillId){
        this.skillId = skillId;
    }

    public void setEmployerId(String employerId){
        this.employerId = employerId;
    }

    public void setSearchTerm(String searchTerm){
        this.searchTerm = searchTerm;
    }

    public void setDomain(String domain){
        this.domain = domain;
    }

    public void setSortBy(String sortBy){
        this.sortBy = sortBy;
    }

    public void setPage(String page){
        this.page = page;
    }

    public void setDeviceOtherInfo(String deviceOtherInfo){
        this.deviceOtherInfo = deviceOtherInfo;
    }

    public void setCourseId(List<String> courseId){
        this.courseId = courseId;
    }

    public void setSalaryMin(String salaryMin){
        this.salaryMin = salaryMin;
    }

    public void setCategoryId(List<String> categoryId){
        this.categoryId = categoryId;
    }

    public void setLat(String lat){
        this.lat = lat;
    }
}