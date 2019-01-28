package com.saeed.projects.mycontacts.data.pojo;


/**
 * Created by Muzammil on 22/03/2017.
 */

public class ContactTag
{
    private int contactId;
    private int recycerViewListPosition;

    public int getRecycerViewListPosition()
    {
        return recycerViewListPosition;
    }

    public void setRecycerViewListPosition(int recycerViewListPosition)
    {
        this.recycerViewListPosition = recycerViewListPosition;
    }

    public int getContactId()
    {
        return contactId;
    }

    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }
}
