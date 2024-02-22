package afluex.parent.careermitra.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import afluex.parent.careermitra.R;
import afluex.parent.careermitra.adapter.EducationAdapter;
import afluex.parent.careermitra.adapter.ExperiencesAdapter;
import afluex.parent.careermitra.adapter.GetSkilsAdapter;
import afluex.parent.careermitra.app.PreferencesManager;
import afluex.parent.careermitra.common.FileUtils;
import afluex.parent.careermitra.common.LoggerUtil;
import afluex.parent.careermitra.common.NetworkUtils;
import afluex.parent.careermitra.common.Utils;
import afluex.parent.careermitra.constants.BaseActivity;
import afluex.parent.careermitra.constants.Cons;
import afluex.parent.careermitra.databinding.ActivityProfileBinding;
import afluex.parent.careermitra.model.response.ResponseCommon;
import afluex.parent.careermitra.model.response.getProfileDetail.ResponseViewProfile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.NormalFilePickActivity;
import com.vincent.filepicker.filter.entity.NormalFile;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.ArrayList;




import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends BaseActivity implements IPickCancel, IPickResult {

    ActivityProfileBinding binding;
    PickImageDialog dialog;
    File homeWorkFile;


    private int PICK_PDF_REQUEST = 1;
    private Uri filePath;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private String pdfPath;
    private boolean isFirst = true;

    GridLayoutManager layoutManager;

    LinearLayoutManager layoutManager2;

    LinearLayoutManager layoutManager1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tvDescribeSkillsEndorsements.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 3);
        binding.tvDescribeSkillsEndorsements.setLayoutManager(layoutManager);
        layoutManager2 = new LinearLayoutManager(Profile.this);
        binding.recEducation.setLayoutManager(layoutManager2);

        layoutManager1 = new LinearLayoutManager(Profile.this);
        binding.recvExperience.setLayoutManager(layoutManager1);

        binding.imgUsernameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, Edit_Designation.class, null);
            }
        });
        binding.imgEditExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, Edit_Experience.class, null);
            }
        });
        binding.imgEditEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, Edit_Education.class, null);
            }
        });
        binding.imgSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, Edit_Skills.class, null);
            }
        });
        binding.imgEditInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, Edit_Personal_information.class, null);
            }
        });
        binding.imgEditAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(context, Edit_About.class, null);
            }
        });
        binding.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        binding.btnUploadResum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Profile.this, NormalFilePickActivity.class);
                intent4.putExtra(Constant.MAX_NUMBER, 1);
                intent4.putExtra(NormalFilePickActivity.SUFFIX, new String[]{"doc", "docx", "pdf"});
                startActivityForResult(intent4, PICK_PDF_REQUEST);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtils.getConnectivityStatus(context) != 0)
            ViewProfile();
        else showMessage(getResources().getString(R.string.alert_internet));
        isFirst = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    private void ViewProfile() {
        if (isFirst)
            showLoading();
        JsonObject object = new JsonObject();
        object.addProperty("Id", PreferencesManager.getInstance(context).getUserid());
        object.addProperty("address", "");
        object.addProperty("deviceId", Utils.getAndroidDeviceId(context));
        object.addProperty("deviceOtherInfo", "");
        object.addProperty("lat", PreferencesManager.getInstance(context).getLatitude());
        object.addProperty("long", PreferencesManager.getInstance(context).getLongitude());
        object.addProperty("ip", "");
        object.addProperty("domain", "");
        object.addProperty("userAgent", "");
        object.addProperty("os", "");


        Call<ResponseCommon> call = apiServices.GetViewProfile(bodyParam(object));
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                ResponseViewProfile responseViewProfile;
                try {
                    if (response.body().getStatusCode().equals(200)) {
                        String paramResponse = Cons.decryptMsg(response.body().getBody(), cross_intent);

                        Gson gson = new GsonBuilder().create();
                        responseViewProfile = gson.fromJson(paramResponse, ResponseViewProfile.class);

                        binding.tvUsername.setText(responseViewProfile.getFirstName() + " " + responseViewProfile.getLastName());
                        binding.tvUserCompanyLocationName.setText(responseViewProfile.getCompanyName());

                        binding.tvEmail.setText(responseViewProfile.getEmail());
                        binding.tvAbout.setText(responseViewProfile.getAbout());

                        binding.tvPhoneNo.setText(responseViewProfile.getMobileNo());
                        Glide.with(context).load(responseViewProfile.getPhoto())
                                .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher)
                                        .error(R.mipmap.ic_launcher))
                                .into(binding.imgProfile);
                        PreferencesManager.getInstance(context).setImage(responseViewProfile.getPhoto());
                        if (!TextUtils.isEmpty(responseViewProfile.getResume()) && responseViewProfile.getResume().length() > 0) {
                            binding.tvResumStatus.setVisibility(View.VISIBLE);
                            binding.tvResumStatus.setText(responseViewProfile.getResume());
                        } else {
                            binding.tvResumStatus.setVisibility(View.GONE);
                        }
                        //seekbar.setVisibility(View.VISIBLE);

                        binding.seekbar.setProgress(responseViewProfile.getProfileFillPercent());
                        binding.tvFatherName.setText(responseViewProfile.getFatherName());
                        binding.tvMotherName.setText(responseViewProfile.getMotherName());
                        binding.tvAddress.setText(responseViewProfile.getAddress());
                        binding.tvCity.setText(responseViewProfile.getCity());
                        binding.tvState.setText(responseViewProfile.getState());
                        binding.tvPincode.setText(responseViewProfile.getPinCode());
                        binding.tvGender.setText(responseViewProfile.getGender());
                        binding.tvDateOfBroth.setText(responseViewProfile.getDOB());
                        binding.tvUserDesignation.setText(responseViewProfile.getDesignation());

                        if (responseViewProfile.getSkills() != null &&
                                responseViewProfile.getSkills().size() > 0) {
                            GetSkilsAdapter adapter = new GetSkilsAdapter(responseViewProfile.getSkills(), Profile.this);
                            binding.tvDescribeSkillsEndorsements.setAdapter(adapter);
                        }

                        if (responseViewProfile.getEducations() != null &&
                                responseViewProfile.getEducations().size() > 0) {
                            EducationAdapter adapter1 = new EducationAdapter(responseViewProfile.getEducations(), Profile.this);
                            binding.recEducation.setAdapter(adapter1);
                        }

                        if (responseViewProfile.getExperiences() != null &&
                                responseViewProfile.getExperiences().size() > 0) {
                            ExperiencesAdapter adapter2 = new ExperiencesAdapter(responseViewProfile.getExperiences(), Profile.this);
                            binding.recvExperience.setAdapter(adapter2);
                        }
                        //tvHighestEducation.setText(context.responseViewProfile,getEducat);

                    } else {
                        showMessage(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                hideLoading();
            }
        });
    }


    void showDialog() {
        PickSetup pickSetup = new PickSetup();
        pickSetup.setTitle(getString(R.string.select_image));
        pickSetup.setPickTypes(EPickType.GALLERY, EPickType.CAMERA);
        pickSetup.setGalleryIcon(com.vansuita.pickimage.R.mipmap.gallery_colored);
        pickSetup.setCameraIcon(com.vansuita.pickimage.R.mipmap.camera_colored);
        pickSetup.setCancelTextColor(R.color.colorAccent);
        // If show system dialog..
//        pickSetup.setSystemDialog(true);

        dialog = PickImageDialog.build(pickSetup);
        dialog.setOnPickCancel(this);
        dialog.show(getSupportFragmentManager());
    }

    @Override
    public void onPickResult(PickResult r) {

        Log.e("RESULT", "= " + r.getPath());
        if (r.getError() == null) {
            CropImage.activity(r.getUri()).setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(context);
        } else {
            Log.e("RESULT", "ERROR = " + r.getError());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                binding.imgProfile.setImageBitmap(result.getBitmap());
                homeWorkFile = FileUtils.getFile(context, result.getUri());
                uploadProfilePic();
                Log.e("ADDRESS File ", homeWorkFile.toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            }
        }
        if (requestCode == PICK_PDF_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
                File file = new File(list.get(0).getPath());
                uploadePdf(file);
            }
        }
    }

    private void uploadePdf(File homeWorkFile) {
        LoggerUtil.logItem(homeWorkFile.length());
        showLoading();
        RequestBody requestBody;
        MultipartBody.Part body = null;
        requestBody = RequestBody.create(MediaType.parse("application/*"), homeWorkFile);
        body = MultipartBody.Part.createFormData("File", homeWorkFile.getName(), requestBody);
        RequestBody userId = null;
        try {
            userId = RequestBody.create(MediaType.parse("text/plain"), Cons.encryptMsg(PreferencesManager.getInstance(context).getUserid(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        RequestBody deviceId = RequestBody.create(MediaType.parse("text/plain"), Utils.getAndroidDeviceId(context));
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getLatitude());
        RequestBody longi = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getLongitude());
        RequestBody Address = RequestBody.create(MediaType.parse("text/plain"), "");

        Call<ResponseCommon> call = apiServices.uploadPdf(userId, deviceId, lat, longi, Address, body);
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        showMessage(response.body().getMessage());
                        new Handler().postDelayed(() -> ViewProfile(), 200);
                    }
                    showMessage(response.body().getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                LoggerUtil.logItem(t.getMessage());
                hideLoading();
            }
        });
    }

    private void uploadProfilePic() {
        LoggerUtil.logItem(homeWorkFile.length());
        showLoading();
        RequestBody requestBody;
        MultipartBody.Part body = null;
        requestBody = RequestBody.create(MediaType.parse("image/*"), homeWorkFile);
        body = MultipartBody.Part.createFormData("File", homeWorkFile.getName(), requestBody);
        RequestBody userId = null;
        try {
            userId = RequestBody.create(MediaType.parse("text/plain"), Cons.encryptMsg(PreferencesManager.getInstance(context).getUserid(), cross_intent));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        RequestBody deviceId = RequestBody.create(MediaType.parse("text/plain"), Utils.getAndroidDeviceId(context));
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getLatitude());
        RequestBody longi = RequestBody.create(MediaType.parse("text/plain"), PreferencesManager.getInstance(context).getLongitude());
        RequestBody Address = RequestBody.create(MediaType.parse("text/plain"), "");

        Call<ResponseCommon> call = apiServices.uploadProfilePic(userId, deviceId, lat, longi, Address, body);
        call.enqueue(new Callback<ResponseCommon>() {
            @Override
            public void onResponse(Call<ResponseCommon> call, Response<ResponseCommon> response) {
                hideLoading();

                try {
                    if (response.body().getStatusCode().equals(200)) {
                        new Handler().postDelayed(() -> ViewProfile(), 200);
                    }
                    showMessage(response.body().getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseCommon> call, Throwable t) {
                LoggerUtil.logItem(t.getMessage());
                hideLoading();
            }
        });
    }

    public void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, R.string.storage_permission, Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, R.string.denie_permission, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCancelClick() {

    }
}