package com.nextgensoft.nbaworld.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nextgensoft.nbaworld.Models.DetailModel;
import com.nextgensoft.nbaworld.R;
import com.nextgensoft.nbaworld.Retrofit.APIClient;
import com.nextgensoft.nbaworld.Retrofit.APIInterface;
import com.nextgensoft.nbaworld.Utils.Internet;
import com.nextgensoft.nbaworld.databinding.ActivityDetail2Binding;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity2 extends AppCompatActivity {

    DetailActivity2 context = DetailActivity2.this;
    ActivityDetail2Binding binding;
    String InNote2, ID;
    DetailModel model3, model4;
    APIInterface apiInterface;
    ProgressDialog dialog;
    File file;
    Uri imagePath;

    // Declaration for permission variables.
    private ActivityResultLauncher<String> mTakePhoto;
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isReadStorageGranted = false;
    private boolean isWriteStorageGranted = false;
    private boolean isManageExternalStorageGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(context, R.layout.activity_detail2);

        //---------------------- Permission code ------------------------------
        memoryAllocation();

        mPermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        if (result.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null) {
                            isReadStorageGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                        if (result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) != null) {
                            isReadStorageGranted = result.get(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                        if (result.get(Manifest.permission.MANAGE_EXTERNAL_STORAGE) != null) {
                            isReadStorageGranted = result.get(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
                        }
                    }
                });

        requestPermission();

        //---------------------- Permission code break ------------------------------

        binding.toolbar.tvTitle.setText("Appointment Detail");
        model3 = (DetailModel) getIntent().getSerializableExtra("DetailObject");
        InNote2 = getIntent().getStringExtra("INNOTE");

        ID = getIntent().getStringExtra("ID");

        apiInterface = APIClient.getClient().create(APIInterface.class);

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        if (model3 == null) {
            dialog.show();
            int exec_id = 1;

            if (Internet.isInternetAvailable(context)) {
                Call<DetailModel> call = apiInterface.getDetail(exec_id, ID);
                call.enqueue(new Callback<DetailModel>() {
                    @Override
                    public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                if (response.body().statusCode == 200) {
                                    dialog.dismiss();
                                    Log.d("RESPONSE", response.body().toString());
                                    model4 = response.body();

                                    binding.tvBookcode.setText(": " + model4.bookcode);
                                    binding.tvName.setText(": " + model4.first_name);
                                    binding.tvMobile.setText(": " + model4.mobile_no);
                                    binding.tvAddress.setText(": " + model4.address);
                                    binding.tvAdminNote.setText(": " + model4.admin_note);
                                    binding.tvDate.setText(": " + model4.date);
                                    binding.tvTime.setText(": " + model4.time);

                                    Toast.makeText(context, model4.message, Toast.LENGTH_LONG).show();

                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(context, response.body().message, Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            dialog.dismiss();
                            Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailModel> call, Throwable t) {
                        dialog.dismiss();
                        Log.d("ERROR", t.getMessage());
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        } else {
            binding.tvInNote2.setText(InNote2);
            binding.tvBookcode.setText(model3.bookcode);
            binding.tvName.setText(model3.first_name);
            binding.tvMobile.setText(model3.mobile_no);
            binding.tvAddress.setText(model3.address);
            binding.tvAdminNote.setText(model3.admin_note);
            binding.tvDate.setText(model3.date);
            binding.tvTime.setText(model3.time);
        }
        binding.btnPunchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                punchOut();
            }
        });
    }

    private void punchOut() {
        dialog.show();
        if (Internet.isInternetAvailable(context)) {
            file = new File(getPath(imagePath)); // see the custom made getPath(Uri uri) method below this api call method.
            // Which converts Uri into File.
            Log.d("FILEPATH", String.valueOf(file));

            RequestBody execId = RequestBody.create(MediaType.parse("text/plain"), "1");

            RequestBody outNote = RequestBody.create(MediaType.parse("text/plain"), binding.etOutNote.getText().toString());

            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            Call<DetailModel> call2 = apiInterface.uploadData(image, execId, outNote);

            call2.enqueue(new Callback<DetailModel>() {
                @Override
                public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                    Log.d("SHIV", response.body().toString());
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().statusCode == 200) {
                                dialog.dismiss();
                                Toast.makeText(context, response.body().message, Toast.LENGTH_SHORT).show();
                                DetailModel res = response.body();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(context, response.body().message, Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        dialog.dismiss();
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<DetailModel> call, Throwable t) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("UPLOADERROR", t.getMessage());
                    dialog.dismiss();
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    //-------------- Permission code continue.. --------------------------------------------------//
    private void memoryAllocation() {
        mTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        Log.d("IMAGEURL", String.valueOf(result));
                        imagePath = result;
                        Log.d("IMAGEPATH", String.valueOf(imagePath));
                        binding.ivImage.setVisibility(View.VISIBLE);
                        binding.ivImage.setImageURI(result);
                        //D/IMAGEPATH: content://com.android.providers.media.documents/document/image%3A40388
                        //D/IMAGEURL: content://com.android.providers.media.documents/document/image%3A40388
                    }
                });
        binding.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    mTakePhoto.launch("image/*");
                } else {
                    Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestPermission() {
        isReadStorageGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
        isWriteStorageGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            isManageExternalStorageGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }

        List<String> permissionRequest = new ArrayList<String>();

        if (!isReadStorageGranted) {
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!isWriteStorageGranted) {
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!isManageExternalStorageGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                permissionRequest.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
            }
        }
        if (!permissionRequest.isEmpty()) {
            mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
        }
    }
}

