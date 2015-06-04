package kookmin.cs.flower.homeflow.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.data.DataSheet;

/**
 * Created by sloth on 2015-04-09.
 */
public class ReadstateReceiver extends WakefulBroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    Bundle extras = intent.getExtras();
    Log.i("mytag2", extras.getString("default"));

    String msg = extras.getString("default");

    Log.i("mytag", msg);
    String[] msgParse = msg.split(" ");

    for(int i=0; i<msgParse.length; i++) {
      Log.i("mytag", msgParse[i]);
    }

    switch (msgParse[3]) {
      case "FLOW_START":
        if (!DataSheet.getFlowState().containsKey(Integer.parseInt(msgParse[1]))) {
          DataSheet.getFlowState().put(Integer.parseInt(msgParse[1]), "ON");
        }

        break;
      case "FLOW_DONE":
        DataSheet.getFlowState().remove(Integer.parseInt(msgParse[1]));
        Log.i("mytag", "com flow down");

        break;
      case "NODE_START":
        if (DataSheet.getNodeState().containsKey(Integer.parseInt(msgParse[1]))) {
          if (DataSheet.getNodeState().get(Integer.parseInt(msgParse[1])) < Integer
              .parseInt(msgParse[2])) {
            DataSheet.getNodeState().remove(Integer.parseInt(msgParse[1]));
            DataSheet.getNodeState()
                .put(Integer.parseInt(msgParse[1]), Integer.parseInt(msgParse[2]));
          }
        }
        Log.i("mytag", "com node start");

        break;
      case "NODE_COMPLETED":
        if (DataSheet.getNodeState().containsKey(Integer.parseInt(msgParse[1]))) {
          if (DataSheet.getNodeState().get(Integer.parseInt(msgParse[1])) == Integer
              .parseInt(msgParse[2])) {
            DataSheet.getNodeState().remove(Integer.parseInt(msgParse[1]));
          }
          Log.i("mytag", "com node complete");

          break;
        }

        sendRegistrationIdToBackend();
    }
  }

  /**
   * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
   * messages to your app. Not needed for this demo since the device sends upstream messages to a
   * server that echoes back the message using the 'from' address in the message.
   */
  private void sendRegistrationIdToBackend() {
    Log.i("mytag", "in the send");
    // Your implementation here.
    final Thread working = new Thread() {
      @Override
      public void run() {
        try {
          URL
              url =
              new URL(
                  "http://" + FileManager.TARGET_SERVER_IP + ":" + FileManager.TARGET_SERVER_PORT
                  + "/notification");
          HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

          urlConnection.setDoInput(true);
          urlConnection.setRequestMethod("GET");

          InputStream response = new BufferedInputStream(urlConnection.getInputStream());

          int data;
          String msg = "";
          while ((data = response.read()) != -1) {
            msg += (char) data;
          }

          response.close();

          // 로그캣에 메세지 출력
          Log.i("mytag", "read line : " + msg);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };

    working.start();
  }
}