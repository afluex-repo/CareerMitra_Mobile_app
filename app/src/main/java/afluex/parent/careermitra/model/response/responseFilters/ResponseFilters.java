package afluex.parent.careermitra.model.response.responseFilters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseFilters{

    @SerializedName("Salary")
    private Salary salary;

    @SerializedName("Departments")
    private List<DepartmentsItem> departments;

    @SerializedName("Qualification")
    private List<QualificationItem> qualification;

    @SerializedName("Skills")
    private List<SkillsItem> skills;

    public Salary getSalary(){
        return salary;
    }

    public List<DepartmentsItem> getDepartments(){
        return departments;
    }

    public List<QualificationItem> getQualification(){
        return qualification;
    }

    public List<SkillsItem> getSkills(){
        return skills;
    }
}