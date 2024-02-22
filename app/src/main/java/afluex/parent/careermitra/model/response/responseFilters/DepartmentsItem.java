package afluex.parent.careermitra.model.response.responseFilters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DepartmentsItem{

    @SerializedName("NameH")
    private String nameH;

    @SerializedName("Categories")
    private List<CategoriesItem> categories;

    @SerializedName("Id")
    private Integer id;

    @SerializedName("Name")
    private String name;

    public String getNameH(){
        return nameH;
    }

    public List<CategoriesItem> getCategories(){
        return categories;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}