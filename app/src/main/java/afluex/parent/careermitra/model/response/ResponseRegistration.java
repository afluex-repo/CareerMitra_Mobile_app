package afluex.parent.careermitra.model.response;

import com.google.gson.annotations.SerializedName;

public class ResponseRegistration{

    @SerializedName("MobileNo")
    private String mobileNo;

    @SerializedName("Email")
    private String email;

    @SerializedName("FirstName")
    private String firstName;

    @SerializedName("Photo")
    private String photo;

    @SerializedName("Id")
    private String id;

    @SerializedName("LastName")
    private String lastName;

    public String getMobileNo(){
        return mobileNo;
    }

    public String getEmail(){
        return email;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getPhoto(){
        return photo;
    }

    public String getId() {
        return id;
    }

    public String getLastName(){
        return lastName;
    }
}