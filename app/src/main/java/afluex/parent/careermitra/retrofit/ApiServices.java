package afluex.parent.careermitra.retrofit;


import com.google.gson.JsonObject;

import afluex.parent.careermitra.model.response.ResponseCommon;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiServices {

    @POST("api/careermitra/GenerateOTP")
    Call<ResponseCommon> GenerateOTP(@Body JsonObject object);

    @POST("api/careermitra/CheckMobileNo")
    Call<ResponseCommon> CheckMobileNo(@Body JsonObject object);

    @POST("api/careermitra/ValidateOTP")
    Call<ResponseCommon> ValidateOTP(@Body JsonObject object);

    @POST("api/careermitra/Register")
    Call<ResponseCommon> Register(@Body JsonObject object);

    @POST("api/careermitra/Login")
    Call<ResponseCommon> Login(@Body JsonObject object);

    @POST("api/careerMitra/Logout")
    Call<ResponseCommon> Logout(@Body JsonObject object);

    @POST("api/careermitra/CheckLogIn")
    Call<ResponseCommon> CheckLogIn(@Body JsonObject object);

    @POST("api/careermitra/ForgetPassword")
    Call<ResponseCommon> ForgotPassword(@Body JsonObject object);

    @POST("api/careerMitra/ResetPassword")
    Call<ResponseCommon> ResetPassword(@Body JsonObject object);

    @POST("api/careermitra/ChangePassword")
    Call<ResponseCommon> ChangePassword(@Body JsonObject object);

    @POST("api/careermitra/UpdateProfileAbout")
    Call<ResponseCommon> UpdateProfileAbout(@Body JsonObject object);

    @POST("api/careermitra/UpdateProfileCompanyDetail ")
    Call<ResponseCommon> updateCompanyDetails(@Body JsonObject object);

    @POST("api/careermitra/UpdateProfileSkills ")
    Call<ResponseCommon> updateSkills(@Body JsonObject object);

    @POST("api/careerMitra/GetPincodeData")
    Call<ResponseCommon> GetPincode(@Body JsonObject object);

    @POST("api/careermitra/UpdateProfilePersonalDetail")
    Call<ResponseCommon> updatePersonalDetail(@Body JsonObject object);

    @POST("api/careermitra/GetSkills")
    Call<ResponseCommon> GetSkills(@Body JsonObject object);

    @POST("api/careerMitra/GetLocations")
    Call<ResponseCommon> GetCity(@Body JsonObject object);

    @POST("api/careermitra/GetCourses")
    Call<ResponseCommon> GetCourses(@Body JsonObject object);

    @POST("api/careermitra/UpdateProfileeducation")
    Call<ResponseCommon> updateEducation(@Body JsonObject object);

    @POST("api/careermitra/UpdateProfileExperience")
    Call<ResponseCommon> UpdateProfileExperience(@Body JsonObject object);

    @POST("api/careermitra/GetProfileData")
    Call<ResponseCommon> GetViewProfile(@Body JsonObject object);

    @Multipart
    @POST("api/careermitra/UpdateProfilePic")
    Call<ResponseCommon> uploadProfilePic(@Part("Id") RequestBody UserId,
                                          @Part("DeviceId") RequestBody DeviceId,
                                          @Part("Lat") RequestBody Lat,
                                          @Part("Long") RequestBody Long,
                                          @Part("Address") RequestBody Address,
                                          @Part() MultipartBody.Part file);

    @Multipart
    @POST("api/careermitra/UpdateResume")
    Call<ResponseCommon> uploadPdf(@Part("Id") RequestBody UserId,
                                   @Part("DeviceId") RequestBody DeviceId,
                                   @Part("Lat") RequestBody Lat,
                                   @Part("Long") RequestBody Long,
                                   @Part("Address") RequestBody Address,
                                   @Part() MultipartBody.Part file);

    @POST("api/careermitra/JobDetail")
    Call<ResponseCommon> GetJobDetails(@Body JsonObject object);

    @POST("api/careermitra/SearchJob")
    Call<ResponseCommon> GetJobList(@Body JsonObject object);

    @POST("api/careermitra/ApplyJob")
    Call<ResponseCommon> ApplyJobs(@Body JsonObject object);

    @POST("api/careermitra/AppliedJobs")
    Call<ResponseCommon> GetAppliedJobs(@Body JsonObject object);

    @POST("api/careerMitra/GetRecentJobs")
    Call<ResponseCommon> GetRecentJob(@Body JsonObject object);


    @POST("api/careerMitra/GetTrainingMaterial")
    Call<ResponseCommon> GetTrainning(@Body JsonObject object);

    @POST("api/careerMitra/GetRecentJobs")
    Call<ResponseCommon> GetAboutUs(@Body JsonObject object);

    @POST("api/careerMitra/GetCMSData")
    Call<ResponseCommon> getAboutUs(@Body JsonObject object);

    @POST("api/careerMitra/SubmitFeedBack")
    Call<ResponseCommon> GetFeedback(@Body JsonObject object);

    @POST("api/careerMitra/GetMessageList")
    Call<ResponseCommon> GetMessageList(@Body JsonObject object);

    @POST("api/careerMitra/GetMessageConversation")
    Call<ResponseCommon> GetMessageConversation(@Body JsonObject object);

    @POST("api/careerMitra/SendMessage")
    Call<ResponseCommon> SendMessage(@Body JsonObject object);

    @POST("api/careerMitra/GetFilters")
    Call<ResponseCommon> GetFilters();

    @POST("api/CareerMitra/GetEnrollmentProgram")
    Call<ResponseCommon>GetEnrollment(@Body JsonObject object);

    @POST("api/CareerMitra/ApplyForEnrollment")
    Call<ResponseCommon>GetApplyEnrollment(@Body JsonObject object);

    @POST("api/CareerMitra/GetAllNotification")
    Call<ResponseCommon>GetAllNotification(@Body JsonObject object);
}