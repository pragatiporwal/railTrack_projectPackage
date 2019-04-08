package com.example.dell.railtrack;

import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class SMS_activity extends Activity {
    String phn;

    public SMS_activity(String phn) {
        this.phn = phn;
        System.out.println(phn);

   /*ActivityCompat.requestPermissions(SMS_activity.this,new String[]{android.Manifest.permission.SEND_SMS},1);

        String msg="Rail Track order placed";
        SmsManager manager=SmsManager.getDefault();
        manager.sendTextMessage(phn,null,msg,null,null);
     //   Toast.makeText(SMS_activity.this, "Done", Toast.LENGTH_SHORT).show();*/

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}