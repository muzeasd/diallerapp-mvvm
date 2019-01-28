package com.saeed.projects.mycontacts.data;

import com.saeed.projects.mycontacts.data.local.dao.DbHelper;
import com.saeed.projects.mycontacts.data.local.prefs.PreferencesHelper;
import com.saeed.projects.mycontacts.data.pojo.Contact;
import com.saeed.projects.mycontacts.data.remote.ApiHelper;

import java.util.List;

import io.reactivex.Observable;

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper
{

    Observable<List<Contact>> getAllContacts();


    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
