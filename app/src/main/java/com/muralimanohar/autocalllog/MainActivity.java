package com.muralimanohar.autocalllog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = null;
    TextView call;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        call = findViewById(R.id.call);
        checkAndRequestPermissions();
        getCallDetails();
    }

    // Check Necessary Permission
    private boolean checkAndRequestPermissions() {
        int readcontactPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);

        int readphonestatePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);

        int readphonelogPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG);

        int writecalllogPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CALL_LOG);

        int readexternalstoragePermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (readcontactPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (readphonestatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (readphonelogPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG);
        }
        if (writecalllogPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CALL_LOG);
        }
        if (readexternalstoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }

        return true;
    }


       ArrayList<contactdetails> contactmiss = new ArrayList<contactdetails>();


    private void getCallDetails() {

//        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
//        sb.append( "Call Details :");
        while (managedCursor.moveToNext()) {
            String callType = managedCursor.getString(type);

            contactdetails contactdd = new contactdetails();
            contactdd.setNumber(Integer.toString(number));


            contactdd.setNumber(Integer.toString(number));
            contactdd.setDate(Integer.toString(date));
            contactdd.setDuration(Integer.toString(duration));


//            contactmiss.add(contactdd.setNumber(Integer.toString(number)));
//            String phNumber = managedCursor.getString(number);
//            String callType = managedCursor.getString(type);
//            String callDate = managedCursor.getString(date);
//            Date callDayTime = new Date(Long.valueOf(callDate));
//            String callDuration = managedCursor.getString(duration);

            Log.i(TAG, "getCallDetails: "+contactmiss);

            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    contactdd.setType("OUTGOING");
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    contactdd.setType("INCOMING");
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    contactdd.setType("MISSED");
                    break;
            }

            contactmiss.add(contactdd);



            if (dircode == 3) {


            }

        }

        for (contactdetails temp : contactmiss) {
            System.out.println(temp.getDate());

        }

        managedCursor.close();


     call.setText(contactmiss);


    }


}



