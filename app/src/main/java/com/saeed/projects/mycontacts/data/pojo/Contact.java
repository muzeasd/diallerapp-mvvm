package com.saeed.projects.mycontacts.data.pojo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import java.io.Serializable;

/**
 * Created by Muzammil on 01/03/2017.
 */

public class Contact extends BaseObservable implements Serializable
{
    private long id;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private String city;
    private String country;
    private String skypeId;
    private Bitmap photo;


    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getSkypeId()
    {
        return skypeId;
    }

    public void setSkypeId(String skypeId)
    {
        this.skypeId = skypeId;
    }

    @Bindable
    public Bitmap getPhoto()
    {
        return photo;
    }

    public void setPhoto(Bitmap photo)
    {
        this.photo = photo;
    }

    @Bindable
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Bindable
    public String getPhoneNo()
    {
        return phoneNo;
    }

    public void setPhoneNo(String phone)
    {
        this.phoneNo= phone;
    }

    @Bindable
    public String getEmail()
    {
        if(email == null)
            return "mail@provider.com";
        if(email.trim().length() <= 0)
            return "mail@provider.com";

        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Contact() {}
}
