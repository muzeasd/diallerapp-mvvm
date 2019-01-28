package com.saeed.projects.mycontacts.ui.activities.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.saeed.projects.mycontacts.data.DataManager;
import com.saeed.projects.mycontacts.data.DatabaseHandler;
import com.saeed.projects.mycontacts.data.pojo.Contact;
import com.saeed.projects.mycontacts.navigators.MainNavigator;
import com.saeed.projects.mycontacts.ui.base.BaseViewModel;
import com.saeed.projects.mycontacts.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.observers.DisposableObserver;

public class MainViewModel extends BaseViewModel<MainNavigator>
{
    private Activity activity;
    private MutableLiveData<List<Contact>> contactData = null;
    private final ObservableList<Contact> observableContactList = new ObservableArrayList<>();

    public MainViewModel(){
        super(null, null);
        contactData = new MutableLiveData<>();
    }

    public void setPreReqs(Activity activity, DataManager dataManager, SchedulerProvider schedulerProvider){
        this.activity = activity;
        super.setDataManager(dataManager);
        super.setSchedulerProvider(schedulerProvider);
    }

    public void loadContacts(){

        DisposableObserver<List<Contact>> contactDisposableObserver = getContactObservable();

        getCompositeDisposable().add(
             getDataManager().getAllContacts()
                    .subscribeOn(getSchedulerProvider().ui())
                     .subscribe(contact->{MainViewModel.this.contactData.postValue(contact);}, throwable -> {}));
//        getCompositeDisposable().add(
//             getDataManager().getAllContacts()
//                    .subscribeOn(getSchedulerProvider().io())
//                    .observeOn(getSchedulerProvider().io()/*getSchedulerProvider().ui()*/)
//                    .subscribeWith(contactDisposableObserver));
    }


    public MutableLiveData<List<Contact>> getContactsData() {
        return contactData;
    }


    public void setQuestionDataList(List<Contact> contactsData) {
        observableContactList.clear();
        observableContactList.addAll(contactsData);
    }

    private DisposableObserver<List<Contact>> getContactObservable(){
        return new DisposableObserver<List<Contact>>()
        {
            @Override
            public void onNext(List<Contact> contactList)
            {
                MainViewModel.this.contactData.postValue(contactList);
                for (Contact contact : contactList) {
                    String TAG = "==DisposaleObserver==";
                    Log.d(TAG, "=== Name: " + contact.getName() + "Contact: " + contact.getName() + " ===");
                }
            }

            @Override
            public void onError(Throwable e)
            {
                getNavigator().handleError(e);
                String TAG = "==DisposaleObserver==";
                Log.d(TAG, "Error: " + e.getMessage());
            }

            @Override
            public void onComplete()
            {
                String TAG = "==DisposaleObserver==";
                Log.d(TAG, "All Done !!");
//                setQuestionDataList(MainViewModel.this.contactData.getValue());
//                ((MainNavigator)activity).contactsLoaded(contactList);
            }
        };
    }


}
