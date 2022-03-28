package com.nextgensoft.nbaworld.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nextgensoft.nbaworld.Adapters.TodayAdapter;
import com.nextgensoft.nbaworld.Adapters.UpcomingAdapter;
import com.nextgensoft.nbaworld.Models.TodayAppointmentsModel;
import com.nextgensoft.nbaworld.Models.UpcomingModel;
import com.nextgensoft.nbaworld.R;
import com.nextgensoft.nbaworld.Retrofit.APIClient;
import com.nextgensoft.nbaworld.Retrofit.APIInterface;
import com.nextgensoft.nbaworld.databinding.ActivityUpcomingBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingActivity extends AppCompatActivity {

    UpcomingActivity context = UpcomingActivity.this;
    ActivityUpcomingBinding binding;
    APIInterface apiInterface;
    ProgressDialog dialog;
    UpcomingAdapter adapter;
    ArrayList<UpcomingModel.Data> upcomingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(context, R.layout.activity_upcoming);
        //getSupportActionBar().hide();

        binding.toolbar.tvTitle.setText("Upcoming Appointments");

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        getUpcomingDetail();
    }

    private void getUpcomingDetail() {
        dialog.show();
        int exec_id = 1;

        if (isInternetAvailable()) {
            Call<UpcomingModel> call = apiInterface.getUpcoming(exec_id);
            call.enqueue(new Callback<UpcomingModel>() {
                @Override
                public void onResponse(Call<UpcomingModel> call, Response<UpcomingModel> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().statusCode == 200) {
                                dialog.dismiss();
                                Log.d("RESPONSE", response.body().toString());
                                dialog.dismiss();
                                UpcomingModel model = response.body();
                                Toast.makeText(context, model.message, Toast.LENGTH_LONG).show();
                                upcomingList = response.body().data;
                                if(upcomingList != null) {
                                    adapter = new UpcomingAdapter(upcomingList, context);
                                    binding.rvUpcoming.setLayoutManager(new GridLayoutManager(context, 1));
                                    binding.rvUpcoming.setAdapter(adapter);
                                }else{
                                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
                                }

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
                public void onFailure(Call<UpcomingModel> call, Throwable t) {
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