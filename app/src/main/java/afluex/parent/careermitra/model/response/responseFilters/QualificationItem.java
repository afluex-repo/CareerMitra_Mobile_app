package afluex.parent.careermitra.model.response.responseFilters;

import com.google.gson.annotations.SerializedName;

public class QualificationItem{

    @SerializedName("NameH")
    private String nameH;

    @SerializedName("Id")
    private Integer id;

    @SerializedName("Name")
    private String name;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getNameH(){
        return nameH;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}