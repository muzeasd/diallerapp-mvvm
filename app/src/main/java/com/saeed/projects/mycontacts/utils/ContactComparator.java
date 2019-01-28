package com.saeed.projects.mycontacts.utils;

import com.saeed.projects.mycontacts.data.pojo.Contact;

import java.util.Comparator;

public class ContactComparator implements Comparator<Contact>
{
    @Override
    public int compare(Contact left, Contact right)
    {
        return left.getName().compareTo(right.getName());
    }
}
