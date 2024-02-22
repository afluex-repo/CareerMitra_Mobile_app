package afluex.parent.careermitra.model.response.responseFilters;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem {

    @SerializedName("CategoryId")
    private Integer categoryId;

    @SerializedName("NameH")
    private String nameH;

    @SerializedName("Name")
    private String name;

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getNameH() {
        return nameH;
    }

    public String getName() {
        return name;
    }
}