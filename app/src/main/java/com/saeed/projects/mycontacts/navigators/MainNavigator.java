package com.saeed.projects.mycontacts.navigators;

import com.saeed.projects.mycontacts.data.pojo.Contact;

import java.util.List;

public interface MainNavigator {

    void handleError(Throwable throwable);
    void contactsLoaded(List<Contact> contactList);
}
