package com.nextgensoft.nbaworld.Retrofit;

import com.nextgensoft.nbaworld.Models.CompletedModel;
import com.nextgensoft.nbaworld.Models.DetailModel;
import com.nextgensoft.nbaworld.Models.LoginModel;
import com.nextgensoft.nbaworld.Models.TodayAppointmentsModel;
import com.nextgensoft.nbaworld.Models.UpcomingModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIInterface {

    @FormUrlEncoded
    @POST("check_login.php")
    Call<LoginModel> loginUser(@FieldMap Map<String, String> params);

    @GET("get_today_appoinment.php")
    Call<TodayAppointmentsModel> getToday(@Query("exec_id") int exec_id);

    @GET("get_upcoming_appoinment.php")
    Call<UpcomingModel> getUpcoming(@Query("exec_id") int exec_id);

    @GET("get_completed_appoinment.php")
    Call<CompletedModel> getCompleted(@Query("exec_id") int exec_id);

    @GET("get_appoinment_details.php")
    Call<DetailModel> getDetail(@Query("exec_id") int exec_id, @Query("appointment_id") String appointmentId);

    @GET("punch_in.php")
    Call<DetailModel> getPunchInDetail(@Query("exec_id") int exec_id, @Query("appointment_id") String appointmentId,
                                       @Query("in_note") String inNote);

    @Multipart
    @POST("punch_out.php")
    Call<DetailModel> uploadData(@Part MultipartBody.Part image, @Part("exec_id") RequestBody execID, @Part("out_note") RequestBody outNote);
}
