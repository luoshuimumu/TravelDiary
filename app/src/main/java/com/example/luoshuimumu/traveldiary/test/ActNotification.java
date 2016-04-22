package com.example.luoshuimumu.traveldiary.test;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.R;

public class ActNotification extends ActionBarActivity {
    NotificationManager nm;
    static final int NOTIFICATION_ID = 0x123;
    String items[] = {"item-1", "item-2", "item-3"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_notification);
        if (null == nm) {
            nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        }

    }

    public void showAlert(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher).setTitle("simple dialog")
//                .setMessage("test message")
        ;
        builder.setPositiveButton("sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ActNotification.this, "positive clicked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ActNotification.this, "negative clicked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("eee...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ActNotification.this, "neutral clicked", Toast.LENGTH_SHORT).show();

            }
        });
//        builder
//                .setItems(items, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(ActNotification.this, "item " + (i + 1) + " clicked", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//        builder.setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(ActNotification.this, "item " + (i + 1) + " clicked", Toast.LENGTH_SHORT).show();
//
//            }
//        });
        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                Toast.makeText(ActNotification.this, "item " + (i + 1) + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();


    }

    public void sendNotification(View view) {
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(ActNotification.this, 0, intent, 0);
        Notification notification = new Notification.Builder(this).
                setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher).setWhen(System.currentTimeMillis())
                .setTicker("you have a new massage").setContentTitle("测试新通知").
                        setContentText("should be charSequence")
                .setDefaults(Notification.DEFAULT_ALL).
                        setContentIntent(pendingIntent).build();
        nm.notify(NOTIFICATION_ID, notification);
    }

    public void del(View view) {
        nm.cancel(NOTIFICATION_ID);
    }

}
