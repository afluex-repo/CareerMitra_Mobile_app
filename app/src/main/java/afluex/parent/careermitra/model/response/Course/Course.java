package afluex.parent.careermitra.model.response.Course;

import com.google.gson.annotations.SerializedName;

public class Course {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
