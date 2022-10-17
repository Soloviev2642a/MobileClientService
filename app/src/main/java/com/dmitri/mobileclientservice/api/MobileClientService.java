package com.dmitri.mobileclientservice.api;

import com.dmitri.mobileclientservice.api.response.UserLoginResponse;
import com.dmitri.mobileclientservice.api.response.UserResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MobileClientService {
    @GET("form/users")
    Observable<UserResponse> getUsers();

    @GET("authentication")
    Call<UserLoginResponse> userLogin(
            @Query("uid") String uid,
            @Query("pass") String password,
            @Query("copyFromDevice") boolean copyFromDevice,
            @Query("nfs") String nfs
    );
}
