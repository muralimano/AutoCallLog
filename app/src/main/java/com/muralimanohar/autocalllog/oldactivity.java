package com.muralimanohar.autocalllog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class oldactivity extends AppCompatActivity {

    TextView call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        call = findViewById(R.id.call);
        getCallDetails();
    }


//    ArrayList<String> contactmiss = new ArrayList<String>();
    ArrayList<HashMap<String, Integer>> contactmiss = new ArrayList<HashMap<String, Integer>>();

    ArrayList<contactdetails> contactdial = new ArrayList<contactdetails>();
    ArrayList<contactdetails> contactrecv = new ArrayList<contactdetails>();
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
//        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = managedQuery( CallLog.Calls.CONTENT_URI,null, null,null, null);
        int number = managedCursor.getColumnIndex( CallLog.Calls.NUMBER );
        int type = managedCursor.getColumnIndex( CallLog.Calls.TYPE );
        int date = managedCursor.getColumnIndex( CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex( CallLog.Calls.DURATION);
//        sb.append( "Call Details :");
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


                HashMap<String, Integer> data1 = new HashMap<String, Integer>();
                data1.put("0",new Integer(phNumber));
                data1.put("1",new Integer(dir));
//                data1.put("2",new Integer(callDayTime));
                data1.put("3",new Integer(callDuration));

                contactmiss.add(data1);
//                contactmiss.add(phNumber);
//                contactmiss.add(dir);
//                contactmiss.add(callDate);
//                contactmiss.add(callDuration);



//                sb.append( "\nPhone Number:--- "+phNumber +" \nCall Type:--- "+dir+" \nCall Date:--- "+callDayTime+" \nCall duration in sec :--- "+callDuration );
//                sb.append("\n----------------------------------");
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
        for (int i = 0; i < contactmiss.size(); i++)
        {
            HashMap<String, Integer> tmpData = (HashMap<String, Integer>) contactmiss.get(i);
            Set<String> key = tmpData.keySet();
            Iterator it = key.iterator();
            while (it.hasNext()) {
                String hmKey = (String)it.next();
                Integer hmData = (Integer) tmpData.get(hmKey);

                call.setText("Key: "+hmKey +" & Data: "+hmData);
                it.remove(); // avoids a ConcurrentModificationException
            }
//            TextView tv = new TextView(this);
//            tv.setLayoutParams(lparams);
//            tv.setText(mylist.get(i));
//            layout.addView(tv);
        }
//       call.setText(contactmiss);


    }


}



