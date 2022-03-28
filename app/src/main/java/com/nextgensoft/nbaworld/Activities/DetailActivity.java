package com.nextgensoft.nbaworld.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nextgensoft.nbaworld.Adapters.CompletedAdapter;
import com.nextgensoft.nbaworld.Models.CompletedModel;
import com.nextgensoft.nbaworld.Models.DetailModel;
import com.nextgensoft.nbaworld.R;
import com.nextgensoft.nbaworld.Retrofit.APIClient;
import com.nextgensoft.nbaworld.Retrofit.APIInterface;
import com.nextgensoft.nbaworld.databinding.ActivityDetailBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    DetailActivity context = DetailActivity.this;
    ActivityDetailBinding binding;
    APIInterface apiInterface;
    ProgressDialog dialog;
    String appointmentId, InNote;
    DetailModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(context, R.layout.activity_detail);

        appointmentId = getIntent().getStringExtra("ID3");
        binding.toolbar.tvTitle.setText("Appointment Detail");

        apiInterface = APIClient.getClient().create(APIInterface.class);

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        getAppointmentDetail();

        binding.btnPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                punchIn();
            }
        });
    }

    private void punchIn() {
        InNote = binding.etInNote.getText().toString();
        dialog.show();
        int exec_id = 1;

        if (isInternetAvailable()) {
            Call<DetailModel> call = apiInterface.getPunchInDetail(exec_id, appointmentId, InNote);
            call.enqueue(new Callback<DetailModel>() {
                @Override
                public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().statusCode == 200) {
                                dialog.dismiss();
                                Log.d("RESPONSE", response.body().toString());
                                dialog.dismiss();
                                DetailModel model2 = response.body();
                                Toast.makeText(context, model2.message, Toast.LENGTH_LONG).show();

                                Intent detail2 = new Intent(context, DetailActivity2.class);
                                detail2.putExtra("DetailObject",model);
                                detail2.putExtra("INNOTE",InNote);
                                startActivity(detail2);
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
        }else{
            Toast.makeText(context,"No internet connection",Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }

    private void getAppointmentDetail() {

        dialog.show();
        int exec_id = 1;

        if (isInternetAvailable()) {
            Call<DetailModel> call = apiInterface.getDetail(exec_id, appointmentId);
            call.enqueue(new Callback<DetailModel>() {
                @Override
                public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().statusCode == 200) {
                                dialog.dismiss();
                                Log.d("RESPONSE", response.body().toString());
                                dialog.dismiss();
                                model = response.body();

                                binding.tvBookcode.setText(": " + model.bookcode);
                                binding.tvName.setText(": " + model.first_name);
                                binding.tvMobile.setText(": " + model.mobile_no);
                                binding.tvAddress.setText(": " + model.address);
                                binding.tvAdminNote.setText(": " + model.admin_note);
                                binding.tvDate.setText(": " + model.date);
                                binding.tvTime.setText(": " + model.time);

                                Toast.makeText(context, model.message, Toast.LENGTH_LONG).show();

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
        }else{
            Toast.makeText(context,"No internet connection",Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }
}