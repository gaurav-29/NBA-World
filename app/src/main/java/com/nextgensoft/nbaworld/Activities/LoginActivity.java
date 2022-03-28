package com.nextgensoft.nbaworld.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nextgensoft.nbaworld.Models.LoginModel;
import com.nextgensoft.nbaworld.R;
import com.nextgensoft.nbaworld.Retrofit.APIClient;
import com.nextgensoft.nbaworld.Retrofit.APIInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    LoginActivity context = LoginActivity.this;
    Button btnLogin;
    EditText etEmail, etPassword;
    APIInterface apiInterface;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().hide();

        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        dialog.show();

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        params.put("device_token", "");

        if (isInternetAvailable()) {
            Call<LoginModel> call = apiInterface.loginUser(params);
            call.enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().statusCode == 200) {
                                Log.d("RESPONSE", response.body().toString());
                                dialog.dismiss();
                                LoginModel model = response.body();
                                Toast.makeText(context, model.message, Toast.LENGTH_LONG).show();
                                Intent login = new Intent(context, HomeScreenActivity.class);
                                startActivity(login);
//                                Intent webview = new Intent(context, WebViewActivity.class);
//                                startActivity(webview);
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
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    dialog.dismiss();
                    Log.d("ERROR", t.getMessage());
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });

        } else {
            dialog.dismiss();
            Toast.makeText(context, "No internet connection", Toast.LENGTH_LONG).show();
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