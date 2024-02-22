package afluex.parent.careermitra.model.response.getProfileDetail;

import com.google.gson.annotations.SerializedName;

public class EducationsItem{

    @SerializedName("CourseId")
    private Integer courseId;

    @SerializedName("YearOfPassing")
    private String yearOfPassing;

    @SerializedName("College")
    private String college;

    @SerializedName("Course")
    private String course;

    public Integer getCourseId(){
        return courseId;
    }

    public String getYearOfPassing(){
        return yearOfPassing;
    }

    public String getCollege(){
        return college;
    }

    public String getCourse(){
        return course;
    }
}