package com.saeed.projects.mycontacts.data;

import android.content.Context;

import com.google.gson.Gson;
import com.saeed.projects.mycontacts.data.local.dao.DbHelper;
import com.saeed.projects.mycontacts.data.local.prefs.PreferencesHelper;
import com.saeed.projects.mycontacts.data.models.api.LoginRequest;
import com.saeed.projects.mycontacts.data.models.api.LoginResponse;
import com.saeed.projects.mycontacts.data.models.api.LogoutResponse;
import com.saeed.projects.mycontacts.data.pojo.Contact;
import com.saeed.projects.mycontacts.data.remote.ApiHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;

public class AppDataManager implements DataManager
{

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public Observable<List<Contact>> getAllContacts()
    {
        List<Contact> contactList = DatabaseHandler.getInstance().getContacts(mContext);
        List<List<Contact>> list = new ArrayList<>();
        list.add(contactList);

        Observable<List<Contact>> listObservable = Observable.fromIterable(list);
        return listObservable;
    }

    @Override
    public Observable<Boolean> insertContact(Contact user)
    {
        return null;
    }

    @Override
    public Observable<Boolean> updateContact(Contact user)
    {
        return null;
    }

    @Override
    public Observable<Boolean> deleteContact(Contact user)
    {
        return null;
    }

    @Override
    public String getCurrentUserEmail()
    {
        return null;
    }

    @Override
    public void setCurrentUserEmail(String email)
    {

    }

    @Override
    public Long getCurrentUserId()
    {
        return null;
    }

    @Override
    public void setCurrentUserId(Long userId)
    {

    }

    @Override
    public int getCurrentUserLoggedInMode()
    {
        return 0;
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode)
    {

    }

    @Override
    public String getCurrentUserName()
    {
        return null;
    }

    @Override
    public void setCurrentUserName(String userName)
    {

    }

    @Override
    public String getCurrentUserProfilePicUrl()
    {
        return null;
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl)
    {

    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return mApiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
//        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }
}
