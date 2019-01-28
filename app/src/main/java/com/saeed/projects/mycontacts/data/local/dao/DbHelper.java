package com.saeed.projects.mycontacts.data.local.dao;

import com.saeed.projects.mycontacts.data.pojo.Contact;

import java.util.List;

import io.reactivex.Observable;

public interface DbHelper {
    Observable<Boolean> insertContact(final Contact user);
    Observable<Boolean> updateContact(final Contact user);
    Observable<Boolean> deleteContact(final Contact user);
}
