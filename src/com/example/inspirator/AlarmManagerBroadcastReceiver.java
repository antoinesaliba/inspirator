package com.example.inspirator;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import com.antoinesaliba.inspirator.R;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	
	         //You can do the processing here update the widget/remote views.
	         NotificationManager noti = (NotificationManager) context.getApplicationContext().getSystemService(context.getApplicationContext().NOTIFICATION_SERVICE);
	 		 if(MainActivity.onOff.isChecked()){
	 			String temp = MainActivity.pickDisplayMessage();
	 			int ID = MainActivity.uniqueID;
	 			Intent itt = new Intent(context.getApplicationContext(), MainActivity.class);
	 			PendingIntent pIntent = PendingIntent.getActivity(context.getApplicationContext(), 0 , itt, 0);
	 			Notification notification = new Notification.Builder(context.getApplicationContext())
	 			.setTicker("Here to Inspire You!")
	 			.setContentTitle("Inspirator")
	 			.setContentText(temp)
	 			.setSmallIcon(R.drawable.ic_launcher)
	 			.setContentIntent(pIntent).getNotification();
	 			notification.flags=Notification.FLAG_AUTO_CANCEL;
	 			noti.notify(ID, notification);
	 			MainActivity.textBox.setText(temp);
	 		}else{
	 			noti.cancelAll();
	 		}
	}
	public void SetAlarm(Context context, int time)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        long interval = DateUtils.SECOND_IN_MILLIS * 60 * 60 * time;
        long firstWake = System.currentTimeMillis() + interval;
        am.setRepeating(AlarmManager.RTC_WAKEUP, firstWake, interval, pi);

    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
    public void setOnetimeTimer(Context context){
    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
    }
}