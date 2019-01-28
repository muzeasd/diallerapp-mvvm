package com.saeed.projects.mycontacts.data.remote;

import com.saeed.projects.mycontacts.data.models.api.LoginRequest;
import com.saeed.projects.mycontacts.data.models.api.LoginResponse;
import com.saeed.projects.mycontacts.data.models.api.LogoutResponse;

import io.reactivex.Single;

public interface ApiHelper {

    Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Single<LogoutResponse> doLogoutApiCall();

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

//    ApiHeader getApiHeader();
//
//    Single<BlogResponse> getBlogApiCall();
//
//    Single<OpenSourceResponse> getOpenSourceApiCall();
}
