package kookmin.cs.flower.homeflow.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class SocketService extends Service {
  public static final String READ_STATE = "read state";
  public static final String STATE_NAME = "state name";
  public static final String STATE_DATA = "state data";

  private static String SERVER_IP = "203.246.112.77";
  private static int SERVER_PORT = 19916;

  private Socket socket;
  private BufferedReader socket_in;
  private PrintWriter socket_out;
  private Boolean runService = true;

  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public int onStartCommand(final Intent intent, int flags, int startId) {
    Log.i("mytag", "chatting is running");

    final Thread working = new Thread(){
      @Override
      public void run() {
        try {
          InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

          Log.i("mytag", "Connecting : " + intent.getStringExtra("selectItem"));
          socket = new Socket(serverAddr, SERVER_PORT);
          Log.i("mytag", "Connecting : " + socket);

          File file = null;
          String fileName = "";
          try {
            for (int i = 0; i < FileManager.getFlowList().size(); i++) {
              Log.i("mytag", FileManager.getFlowList().get(i).toString());

              if (intent.getStringExtra("selectItem") != null && intent.getStringExtra("selectItem")
                  .equalsIgnoreCase(FileManager.getFlowList().get(i).toString())) {
                file = new File(Environment.getExternalStorageDirectory().getPath() +
                                "/HomeFlow/workflow/" + "flow" + FileManager.getFlowList().get(i)
                    .getFlowId() + ".xml");
                fileName = FileManager.getFlowList().get(i).getFlowId() + ".xml";
              }
            }

            Log.i("mytag", file.getPath());
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            socket_out =
                new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            String line = "";
            String head = fileName + " ";
            String outFile = "";
            while ((line = bfr.readLine()) != null) {
              outFile += line;
            }
            int length = outFile.getBytes().length;
            head += length + " " + outFile;
            Log.i("mytag", head);

            //socket_out.println(head);
            socket_out.println(head);
            Log.i("mytag", "msg send");

            while (runService) {
              try {
                Log.i("mytag", "start read");
                socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = socket_in.readLine();

                /*
                String[] split = msg.split(" ");
                Intent intent = new Intent(READ_STATE);
                intent.putExtra(STATE_NAME, split[0]);
                intent.putExtra(STATE_DATA, split[1]);

                sendBroadcast(intent);
                */

                Log.i("mytag", "read line : " + msg);
              } catch (IOException e) {
                e.printStackTrace();
              }

              try {
                this.sleep(5000);
              } catch (Exception e) {
                e.printStackTrace();
              }
            }

          } catch (IOException e) {
            e.printStackTrace();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    };

    working.start();

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