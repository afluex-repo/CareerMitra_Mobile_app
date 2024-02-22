package afluex.parent.careermitra.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEducation {

    @SerializedName("CourseId")
    @Expose
    private String courseId;
    @SerializedName("College")
    @Expose
    private String college;
    @SerializedName("YearOfPassing")
    @Expose
    private String yearOfPassing;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getYearOfPassing() {
        return yearOfPassing;
    }

    public void setYearOfPassing(String yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }
}
