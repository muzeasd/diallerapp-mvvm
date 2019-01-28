package com.saeed.projects.mycontacts.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.saeed.projects.mycontacts.data.pojo.Contact;
import com.saeed.projects.mycontacts.utils.BitmapUtility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.params.CoreConnectionPNames;

public class DatabaseHandler
{

    private static DatabaseHandler dbHandler;

    public static DatabaseHandler getInstance() {
        synchronized (DatabaseHandler.class) {
            if (dbHandler == null) {
                dbHandler = new DatabaseHandler();
            }
        }
        return dbHandler;
    }

    private DatabaseHandler(){

    }

    public List<Contact> getContacts(Context context){
        List<Contact> listContacts = new ArrayList<>();

        try
        {
            ContextWrapper contextWrapper = new ContextWrapper(context);

            ContentResolver cr = contextWrapper.getContentResolver();
            String[] projection = new String[]{ContactsContract.PhoneLookup._ID};
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

            if (cur.getCount() > 0)
            {
                int idColumnIndex = cur.getColumnIndex(ContactsContract.Contacts._ID);
                int nameColumnIndex = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                int isPhoneAvailableColumnIndex = cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);

                while (cur.moveToNext())
                {
                    Contact contact = new Contact();

                    String id = cur.getString(idColumnIndex);
                    String name = cur.getString(nameColumnIndex);
                    String phoneNo = "";
                    if (cur.getInt(isPhoneAvailableColumnIndex) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            int phoneNumberColumnIndex = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            phoneNo = pCur.getString(phoneNumberColumnIndex);
//                                phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE_HOME));
//                                phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            Toast.makeText(getContext(), "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                        }
                        pCur.close();
                    }
//
//                    int type = cur.getInt(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
//                    switch (type)
//                    {
//                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
//                            contact.setPhoneNoType(1);  // Landline
//                            break;
//                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
//                            contact.setPhoneNoType(0);  // Mobile
//                            break;
//                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
//                            contact.setPhoneNoType(1);  // Landline
//                            break;
//                    }

//                    String address = "";
//                    Uri contact_address_uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
//                    Cursor addr_cur = contextWrapper.getContentResolver().query(contact_address_uri,null, null, null, null);
//                    while(addr_cur.moveToNext())
//                    {
//                        address = addr_cur.getString(addr_cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
////                            String  Postcode = addr_cur.getString(addr_cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
////                            String  City = addr_cur.getString(addr_cur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
//                    }
//                    addr_cur.close();
//
//                    InputStream is = openThumbnailPhoto((long) Integer.parseInt(id), cr);//openDisplayPhoto((long) Integer.parseInt(id), cr);
//                    byte[] imageBytes = BitmapUtility.getByteArrayFromStream(is);//IOUtils.toByteArray(is);
////                    Bitmap bmp = (imageBytes == null ? BitmapFactory.decodeResource(resources, R.mipmap.ic_person_black_36dp) : BitmapUtility.getImage(imageBytes));
//                    Bitmap bmp = (imageBytes == null ? null : BitmapUtility.getImage(imageBytes));

                    contact.setId(Integer.parseInt(id));
                    contact.setName(name);
                    contact.setAddress("");
                    contact.setPhoneNo(phoneNo);
                    contact.setPhoto(null);

                    contact.setEmail("");
                    contact.setCity("");
                    contact.setCountry("");
                    contact.setSkypeId("");

                    listContacts.add(contact);
                }
            }
        }
        catch (Exception ex)
        {
            Log.d("=== LOAD_CONTACT ===", ex.getMessage());
        }

        return  listContacts;
    }


    private InputStream openThumbnailPhoto(long contactId, ContentResolver contentResolver) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
        Cursor cursor = contentResolver.query(photoUri,
                new String[] {ContactsContract.Contacts.Photo.PHOTO}, null, null, null);
        if (cursor == null) {
            return null;
        }
        try {
            if (cursor.moveToFirst()) {
                byte[] data = cursor.getBlob(0);
                if (data != null) {
                    return new ByteArrayInputStream(data);
                }
            }
        } finally {
            cursor.close();
        }
        return null;
    }

}
