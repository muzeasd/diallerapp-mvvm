package com.saeed.projects.mycontacts.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.saeed.projects.mycontacts.R;
import com.saeed.projects.mycontacts.data.pojo.Contact;
import com.saeed.projects.mycontacts.databinding.ContactRowItemBinding;
import com.saeed.projects.mycontacts.utils.BitmapUtility;
import com.saeed.projects.mycontacts.utils.ViewScaleHandler;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ContactHolder>
{
    Activity activity;
    List<Contact> contactList;
    LayoutInflater layoutInflater;

    public ContactViewAdapter(Activity activity, List<Contact> contactList){
        this.activity = activity;
        this.contactList = contactList;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_item, parent, false);
        ContactRowItemBinding contactRowItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.contact_row_item, parent, false);
        new ViewScaleHandler(activity).scaleRootView(contactRowItemBinding.getRoot());
        return new ContactHolder(contactRowItemBinding);
    }

    @Override
    public void onBindViewHolder(final ContactHolder holder, int position)
    {
        final Contact contact = contactList.get(position);
        if(contact == null)
            return;

        holder.binding.setContact(contact);

        if (contact.getPhoto() != null) {
            Uri bitmapUri = BitmapUtility.bitmapToUriConverter(activity, contact.getPhoto());
            Picasso.with(activity).load(bitmapUri).into(holder.binding.contactImage);
        }
        holder.binding.contactName.setText(contact.getName());
        holder.binding.contactNumber.setText(contact.getPhoneNo());
        holder.binding.contactEmail.setText(contact.getEmail());

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    dialNumber(contact);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();



                }
            });
    }

    private void dialNumber(Contact contact){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "Permission Not Granted !!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contact.getPhoneNo()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            PackageManager packageManager = activity.getPackageManager();
            List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

            for(int j = 0 ; j < activities.size() ; j++)
            {

                if(activities.get(j).toString().toLowerCase().contains("com.android.phone"))
                {
                    intent.setPackage("com.android.phone");
                }
                else if(activities.get(j).toString().toLowerCase().contains("call"))
                {
                    String pack = (activities.get(j).toString().split("[ ]")[1].split("[/]")[0]);
                    intent.setPackage(pack);
                }
            }

            activity.startActivity(intent);
        }
    }

    @Override
    public int getItemCount()
    {
        if(contactList == null)
            return 0;

        return contactList.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {

//        public View itemView;
//        public ImageView contactImage;
//        public TextView contactName, contactNumber;

        private ContactRowItemBinding binding;

//        public ContactHolder(View itemView) {
//            super(itemView);
//            this.itemView = itemView;
//            contactName = (TextView) itemView.findViewById(R.id.contactName);
//            contactNumber = (TextView) itemView.findViewById(R.id.contactNumber);
//            contactImage = (ImageView) itemView.findViewById(R.id.contactImage);
//        }

        public ContactHolder(final ContactRowItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
