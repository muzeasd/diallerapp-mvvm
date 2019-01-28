package com.saeed.projects.mycontacts.data.models.db;

import android.graphics.Bitmap;

import com.saeed.projects.mycontacts.data.pojo.Contact;

public class ContactModel
{
    public long id;
    public String name;
    public String address;
    public String phoneNo;
    public String email;
    public String city;
    public String country;
    public String skypeId;
    public Bitmap photo;

    public Contact contact;

    public ContactModel(Contact contact)
    {
        this.setContact(contact);
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
        this.id = contact.getId();
        this.name = contact.getName();
        this.phoneNo = contact.getPhoneNo();
        this.email = contact.getEmail();
        this.city = contact.getCity();
        this.country = contact.getCountry();
        this.skypeId = contact.getSkypeId();
        this.photo = contact.getPhoto();
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

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

    public String getPhoneNo()
    {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getSkypeId()
    {
        return skypeId;
    }

    public void setSkypeId(String skypeId)
    {
        this.skypeId = skypeId;
    }

    public Bitmap getPhoto()
    {
        return photo;
    }

    public void setPhoto(Bitmap photo)
    {
        this.photo = photo;
    }
}
