package kookmin.cs.flower.homeflow.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Handler;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class SocketService extends Service {

  private Socket socket;
  private BufferedReader socket_in;
  private PrintWriter socket_out;
  private Boolean runService = true;
  private Handler mhandler;

  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i("mytag", "chatting is running");

    final Thread worker = new Thread() {
      public void run() {
        try {
          Log.i("mytag", "socket connet before");
          socket = new Socket("52.68.82.234", 19918);
          //socket = new Socket("211.212.133.221", 19917);
          Log.i("mytag", "socket connet after " + socket);
          socket_out = new PrintWriter(socket.getOutputStream(), true);
          socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
          e.printStackTrace();
        }
        try {
          while (runService) {
            String line = "";

            File
                file =
                new File(
                    Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/workflow");

            String[] filelist = file.list();

            for (int i = 0; i < filelist.length; i++) {
              BufferedReader
                  buf =
                  new BufferedReader(new FileReader(file.getPath() + "/" + filelist[i]));

              line = "";
              while ((line = buf.readLine()) != null) {
                socket_out.write(line);
                Log.i("mytag", "send : " + line);
              }
              socket_out.flush();
            }

            this.sleep(5000);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    worker.start();
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i("mytag", "chatting is closed");

    if (!socket.isClosed()) {
      try {
        socket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    runService = false;
  }
}