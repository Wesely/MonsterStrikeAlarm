package tw.wesely.mstrikealerm;

import tw.wesely.mstrikealarm.R;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TurtleAlarmReceiver extends BroadcastReceiver {

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		String title = "•¥ØQ¿t≈o!";
		String msg = "QQ";
		
		Log.d("notify", "alarm receive");
		NotificationManager nm = (NotificationManager) arg0.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification.Builder(arg0)
				.setContentTitle(title)
				.setContentText(msg)
				.setSmallIcon(R.drawable.ic_launcher).build();
		nm.notify(1, notification);
	}
	
}
