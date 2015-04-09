package kookmin.cs.flower.homeflow.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by sloth on 2015-04-09.
 */
public class ReadstateReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    String msg = intent.getStringExtra(SocketService.STATE_DATA);

    if(SocketService.READ_STATE == intent.getAction()) {
      Toast.makeText(context, "flow name : " + intent.getIntExtra(SocketService.STATE_NAME, 0) +
                              "state : " + intent.getIntExtra(SocketService.STATE_DATA, 0), Toast.LENGTH_SHORT);
    }
  }
}
