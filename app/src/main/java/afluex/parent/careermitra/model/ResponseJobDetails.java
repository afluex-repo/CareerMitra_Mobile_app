package afluex.parent.careermitra.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseJobDetails {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("ShortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("NoOfVacancies")
    @Expose
    private String noOfVacancies;
    @SerializedName("SalaryMin")
    @Expose
    private String salaryMin;
    @SerializedName("SalaryMax")
    @Expose
    private String salaryMax;
    @SerializedName("IsMonthly")
    @Expose
    private Boolean isMonthly;
    @SerializedName("ExperienceMin")
    @Expose
    private String experienceMin;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("ExperienceMax")
    @Expose
    private String experienceMax;
    @SerializedName("Skill")
    @Expose
    private List<String> skill = null;
    @SerializedName("Course")
    @Expose
    private String course;
    @SerializedName("PostedDate")
    @Expose
    private String postedDate;
    @SerializedName("LastDate")
    @Expose
    private String lastDate;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("CategoryId")
    @Expose
    private String categoryId;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("JobRoleId")
    @Expose
    private String jobRoleId;
    @SerializedName("JobRole")
    @Expose
    private String jobRole;
    @SerializedName("JobTypeId")
    @Expose
    private String jobTypeId;
    @SerializedName("JobType")
    @Expose
    private String jobType;
    @SerializedName("Qualification")
    @Expose
    private String qualification;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("About")
    @Expose
    private String about;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Contact")
    @Expose
    private String contact;
    @SerializedName("Location")
    @Expose
    private String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getNoOfVacancies() {
        return noOfVacancies;
    }

    public void setNoOfVacancies(String noOfVacancies) {
        this.noOfVacancies = noOfVacancies;
    }

    public String getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(String salaryMin) {
        this.salaryMin = salaryMin;
    }

    public String getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(String salaryMax) {
        this.salaryMax = salaryMax;
    }

    public Boolean getIsMonthly() {
        return isMonthly;
    }

    public void setIsMonthly(Boolean isMonthly) {
        this.isMonthly = isMonthly;
    }

    public String getExperienceMin() {
        return experienceMin;
    }

    public void setExperienceMin(String experienceMin) {
        this.experienceMin = experienceMin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExperienceMax() {
        return experienceMax;
    }

    public void setExperienceMax(String experienceMax) {
        this.experienceMax = experienceMax;
    }

    public List<String> getSkill() {
        return skill;
    }

    public void setSkill(List<String> skill) {
        this.skill = skill;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJobRoleId() {
        return jobRoleId;
    }

    public void setJobRoleId(String jobRoleId) {
        this.jobRoleId = jobRoleId;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(String jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
