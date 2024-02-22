package afluex.parent.careermitra.model.response.Course;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseCourse {

    @SerializedName("Courses")
    @Expose
    private List<Course> courses = null;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
