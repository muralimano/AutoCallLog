package com.muralimanohar.autocalllog;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class oldactivity extends  AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;
    TextView call;
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
            listPermissionsNeeded.add( Manifest.permission.READ_CONTACTS);
        }
        if (readphonestatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (readphonelogPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG);
        }
        if (writecalllogPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add( Manifest.permission.WRITE_CALL_LOG);
        }
        if (readexternalstoragePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add( Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }

        return true;
    }

    private void getCallDetails() {

        final String[] NECESSARY_PERMISSIONS = new String[]{Manifest.permission.GET_ACCOUNTS};

        if (ContextCompat.checkSelfPermission(oldactivity.this,
                Manifest.permission.GET_ACCOUNTS) == PackageManager.PERMISSION_GRANTED) {
            Log.i("message", "permision granted");
            //Permission is granted

        } else {

            //ask for permission

            ActivityCompat.requestPermissions(
                    oldactivity.this,
                    NECESSARY_PERMISSIONS, 123);
        }
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
        sb.append( "Call Details :");
        while ( managedCursor.moveToNext() ) {
            String phNumber = managedCursor.getString( number );
            String callType = managedCursor.getString( type );
            String callDate = managedCursor.getString( date );
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString( duration );

            String dir = null;
            int dircode = Integer.parseInt( callType );
            switch( dircode ) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            if (dircode == 3) {

                sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
                sb.append("\n----------------------------------");
            }
//            else if (dircode == 2){
//                sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
//                sb.append("\n----------------------------------");
//            }
//            else if (dircode == 3){
//                sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
//                sb.append("\n----------------------------------");
//            }


//            sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
//            sb.append("\n----------------------------------");
        }
        managedCursor.close();

        call.setText(sb);


    }


}


