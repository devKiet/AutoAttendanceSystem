package com.java.autoattendancesystem.reotrfit;

import com.java.autoattendancesystem.model.Login;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginApi  {

    @GET("/login/get-all")
    Call<List<Login>> getAllLogin();

    @POST("/login/save")
    Call<Login> save(@Body Login login);
}
