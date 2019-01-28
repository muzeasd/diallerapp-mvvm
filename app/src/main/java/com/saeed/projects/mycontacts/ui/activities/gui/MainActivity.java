package com.saeed.projects.mycontacts.ui.activities.gui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.saeed.projects.mycontacts.R;
import com.saeed.projects.mycontacts.adapters.ContactViewAdapter;
import com.saeed.projects.mycontacts.data.AppDataManager;
import com.saeed.projects.mycontacts.data.models.db.ContactModel;
import com.saeed.projects.mycontacts.data.pojo.Contact;
import com.saeed.projects.mycontacts.databinding.ActivityMainBinding;
import com.saeed.projects.mycontacts.navigators.MainNavigator;
import com.saeed.projects.mycontacts.ui.activities.viewmodel.MainViewModel;
import com.saeed.projects.mycontacts.ui.base.BaseActivity;
import com.saeed.projects.mycontacts.utils.AppConstants;
import com.saeed.projects.mycontacts.utils.ContactComparator;
import com.saeed.projects.mycontacts.utils.rx.AppSchedulerProvider;
import com.saeed.projects.mycontacts.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator
{
    ViewModelProvider.Factory mViewModelFactory;
    ActivityMainBinding activityMainBinding;
    MainViewModel mMainViewModel;

    AppDataManager mDataManager;
    AppSchedulerProvider mScheduler;

    ContactViewAdapter contactsViewAdapter;
    RecyclerView contactsRecyclerView;
    List<Contact> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();//DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);

        if(activityMainBinding.loadingLayout != null) {
            activityMainBinding.loadingLayout.setVisibility(View.VISIBLE);
        }

//        mActivityMainBinding = getViewDataBinding();
//        mMainViewModel.setNavigator(this);

        if(!arePermissionsEnabled()){
            requestPermissions();
            return;
        }

        proceed();
    }

    private void proceed() {
//        mDataManager = new AppDataManager(this, null, null, null, null);
//        mScheduler = new AppSchedulerProvider();
//        MainViewModel mainViewModel = new MainViewModel(this, mDataManager, mScheduler);

        if(mMainViewModel != null){
            mMainViewModel.setNavigator(this);
            mMainViewModel.loadContacts();
            subscribeToLiveData();
        }
    }


    public boolean arePermissionsEnabled(){
        for(String permission : AppConstants.PERMISSIONS){
            if(ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    public void requestPermissions(){

        // used for activity
        //ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);

        // used for v4 fragment
//            requestPermissions(PERMISSIONS, PERMISSION_ALL);

        ActivityCompat.requestPermissions(this, AppConstants.PERMISSIONS, AppConstants.PERMISSION_ALL);
    }


    @Override
    public void onBackPressed() {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
//        if (fragment == null) {
//            super.onBackPressed();
//        } else {
//            onFragmentDetached(AboutFragment.TAG);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == AppConstants.PERMISSION_ALL){
            if(!arePermissionsEnabled())
            {
                requestPermissions();
                return;
            }

            proceed();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void handleError(Throwable throwable)
    {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
//        if (fragment != null) {
//            fragmentManager
//                    .beginTransaction()
//                    .disallowAddToBackStack()
//                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                    .remove(fragment)
//                    .commitNow();
//            unlockDrawer();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
//        if (drawable instanceof Animatable) {
//            ((Animatable) drawable).start();
//        }
//        switch (item.getItemId()) {
//            case R.id.action_cut:
//                return true;
//            case R.id.action_copy:
//                return true;
//            case R.id.action_share:
//                return true;
//            case R.id.action_delete:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
        return true;
    }

    @Override
    public void contactsLoaded(List<Contact> contactList)
    {
        if(activityMainBinding.loadingLayout != null) {
            activityMainBinding.loadingLayout.setVisibility(View.GONE);
        }

        if(contactList != null) {
            int a = contactList.size();
            this.contactsList = contactList;

            Collections.sort(this.contactsList, new ContactComparator());

            contactsViewAdapter = new ContactViewAdapter(MainActivity.this, this.contactsList);
            contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
//            contactsRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            contactsRecyclerView.setAdapter(contactsViewAdapter);

        } else {
            this.contactsList = getDummyContacts();
            contactsViewAdapter = new ContactViewAdapter(MainActivity.this, this.contactsList);
            contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
//            contactsRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            contactsRecyclerView.setAdapter(contactsViewAdapter);

//            Toast.makeText(this, "Contacts not loaded !!", Toast.LENGTH_LONG).show();
        }
    }

    private List<Contact> getDummyContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();

        Contact contact1 = new Contact();
        Contact contact2 = new Contact();
        Contact contact3 = new Contact();
        Contact contact4 = new Contact();
        Contact contact5 = new Contact();

        contact1.setName("Ghazanfar Saeed");
        contact2.setName("Amir Saeed");
        contact3.setName("Mudassir Saeed");
        contact4.setName("Muzammil Saeed");
        contact5.setName("Mustansar Saeed");

        contact1.setPhoneNo("03019999999");
        contact2.setPhoneNo("03218888888");
        contact3.setPhoneNo("03477777777");
        contact4.setPhoneNo("03134483195");
        contact5.setPhoneNo("03316666666");

        contact1.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.no_photo));
        contact2.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.no_photo));
        contact3.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.no_photo));
        contact4.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.no_photo));
        contact5.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.no_photo));

        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);
        contacts.add(contact5);

        return (List<Contact>) contacts;
    }

    @Override
    public int getBindingVariable()
    {
        return 0;
//        return BR.viewModel;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
//        mMainViewModel= ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        mMainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);

        mDataManager = new AppDataManager(this, null, null, null, null);
        mScheduler = new AppSchedulerProvider();

        mMainViewModel.setPreReqs(this, mDataManager, mScheduler);

        return mMainViewModel;
    }


    private void subscribeToLiveData() {
        mMainViewModel.getContactsData().observe(this, contactData -> {

            if(activityMainBinding.loadingLayout != null) {
                activityMainBinding.loadingLayout.setVisibility(View.GONE);
            }

            if(contactData != null) {
                int a = contactData.size();
                this.contactsList = contactData;

                Collections.sort(this.contactsList, new ContactComparator());

                contactsViewAdapter = new ContactViewAdapter(MainActivity.this, this.contactsList);
                contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
//            contactsRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                contactsRecyclerView.setAdapter(contactsViewAdapter);
                contactsViewAdapter.notifyDataSetChanged();

            } else {
                this.contactsList = getDummyContacts();
                contactsViewAdapter = new ContactViewAdapter(MainActivity.this, this.contactsList);
                contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
//            contactsRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
                contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                contactsRecyclerView.setAdapter(contactsViewAdapter);
                contactsViewAdapter.notifyDataSetChanged();
//            Toast.makeText(this, "Contacts not loaded !!", Toast.LENGTH_LONG).show();
            }

        });
    }
}
