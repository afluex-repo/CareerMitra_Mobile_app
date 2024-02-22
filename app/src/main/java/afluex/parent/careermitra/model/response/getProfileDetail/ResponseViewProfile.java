package afluex.parent.careermitra.model.response.getProfileDetail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseViewProfile{

    @SerializedName("Email")
    private String email;

    @SerializedName("Address")
    private String address;

    @SerializedName("Photo")
    private String photo;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("PinCode")
    private String pinCode;

    @SerializedName("CompanyName")
    private String companyName;

    @SerializedName("CityId")
    private String cityId;

    @SerializedName("Skills")
    private List<SkillsItems> skills;

    @SerializedName("DOB")
    private String dOB;

    @SerializedName("MotherName")
    private String motherName;

    @SerializedName("Age")
    private String age;

    @SerializedName("MobileNo")
    private String mobileNo;

    @SerializedName("Designation")
    private String designation;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("Experiences")
    private List<ExperiencesItem> experiences;

    @SerializedName("City")
    private String city;

    @SerializedName("HusbandName")
    private String husbandName;

    @SerializedName("SpouseName")
    private String spouseName;

    @SerializedName("About")
    private String about;

    @SerializedName("Mobile2")
    private String mobile2;

    @SerializedName("State")
    private String state;

    @SerializedName("Educations")
    private List<EducationsItem> educations;

    @SerializedName("ProfileFillPercent")
    private int profileFillPercent;

    @SerializedName("Id")
    private String id;

    @SerializedName("LastName")
    private String lastName;

    @SerializedName("FatherName")
    private String fatherName;

    @SerializedName("Resume")
    private String resume;

    public String getEmail(){
        return email;
    }

    public String getAddress(){
        return address;
    }

    public String getPhoto(){
        return photo;
    }

    public String getGender(){
        return gender;
    }

    public String getPinCode(){
        return pinCode;
    }

    public String getCompanyName(){
        return companyName;
    }

    public String getCityId(){
        return cityId;
    }

    public List<SkillsItems> getSkills(){
        return skills;
    }

    public String getDOB(){
        return dOB;
    }

    public String getMotherName(){
        return motherName;
    }

    public String getAge(){
        return age;
    }

    public String getMobileNo(){
        return mobileNo;
    }

    public String getDesignation(){
        return designation;
    }

    public String getFirstName(){
        return firstName;
    }

    public List<ExperiencesItem> getExperiences(){
        return experiences;
    }

    public String getCity(){
        return city;
    }

    public String getHusbandName(){
        return husbandName;
    }

    public String getSpouseName(){
        return spouseName;
    }

    public String getAbout(){
        return about;
    }

    public String getMobile2(){
        return mobile2;
    }

    public String getState(){
        return state;
    }

    public List<EducationsItem> getEducations(){
        return educations;
    }

    public int getProfileFillPercent(){
        return profileFillPercent;
    }

    public String getId(){
        return id;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFatherName(){
        return fatherName;
    }

    public String getResume(){
        return resume;
    }
}