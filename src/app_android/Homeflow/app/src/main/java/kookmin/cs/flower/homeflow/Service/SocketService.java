package kookmin.cs.flower.homeflow.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import kookmin.cs.flower.homeflow.Account;
import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.data.DataSheet;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class SocketService extends Service {

  private String SERVER_IP = FileManager.TARGET_SERVER_IP;
  private String SERVER_PORT = FileManager.TARGET_SERVER_PORT;

  private URL url;
  private HttpURLConnection urlConnection;

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

    final Thread working = new Thread() {
      @Override
      public void run() {
        try {
          String api = "";
          if (intent.getIntExtra("mode", 0) == 2) {
            api = "/user";
          }
          else if(intent.getIntExtra("mode", 0) == 3) {
            api = "/start";
          }
          // 서버와 연결
          Log.i("mytag", "http://" + SERVER_IP + ":" + SERVER_PORT + api);
          url = new URL("http://" + SERVER_IP + ":" + SERVER_PORT + api);
          urlConnection = (HttpURLConnection) url.openConnection(); // HTTP 연결

          /* HTTP init */
          urlConnection.setDoInput(true);
          urlConnection.setDoOutput(true);
          urlConnection.setUseCaches(false);
          urlConnection.setReadTimeout(2000);

          /* HTTP header set */
          urlConnection.setRequestMethod("POST");
          urlConnection.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

          // 선택된 워크플로우 이름 로그에 출력 (없어도 됨)
          Log.i("mytag", "Connecting : " + intent.getStringExtra("selectItem"));

          String msg = editSendMessage(intent.getIntExtra("mode", 0), intent);

          msg = new String(msg.getBytes(), "ISO-8859-1"); // 캐릭터셋 변경.
          Log.i("mytag", msg);
            /* set content-length in request header */
          urlConnection.setRequestProperty(HTTP.CONTENT_LEN, "" + msg.length());
          Log.i("mytag", "Length : " + msg.getBytes().length + " and " + msg.length());

          // request 스트림 열기.
          DataOutputStream request = new DataOutputStream(urlConnection.getOutputStream());

          // HTTP로 메세지 보내기
          request.writeBytes(msg);
          request.flush();
          request.close();

          Log.i("mytag", "msg send");

          // response 되는 메세지 받기.
          while (runService) {
            try {
              Log.i("mytag", "start read");

              // reponse 메세지 스트림 열고 오는 메세지를 입력 받음.
              InputStream response = new BufferedInputStream(urlConnection.getInputStream());

              int data;
              msg = "";
              while ((data = response.read()) != -1) {
                msg += (char) data;
              }

              response.close();

              // 로그캣에 메세지 출력
              Log.i("mytag", "read line : " + msg);
              if (msg.equalsIgnoreCase("ok")) {
                //TODO 성공했을 때 처리 구현하기
                Log.i("mytag", "message success");
                stopSelf();
              }

            } catch (IOException e) {
              e.printStackTrace();
              // TODO 에러처리 하기
              Log.i("mytag", "error stop");
              stopSelf();
            }

            try {
              this.sleep(5000); // 5초간 슬립
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

        } catch (IOException e) {
          e.printStackTrace();
          stopSelf();
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

    runService = false;
  }

  private String editSendMessage(int mode, Intent intent) {
    String msg = "";

    switch (mode) {
      case 1: {
        File file = null;
        try {
          // 폴더 내에서 선택된 이름 찾아서 파일의 이름을 가져옴.
          file = new File(Environment.getExternalStorageDirectory().getPath() +
                          "/HomeFlow/workflow/" + intent.getStringExtra("filename"));

          // 파일 열기.
          Log.i("mytag", file.getPath());
          BufferedReader bfr = new BufferedReader(new FileReader(file));

          // HTTP로 보낼 POST 메세지 작성
          String line = "";
          msg = "FLOW|" + file.getName().substring(4, file.getName().length()) + "|";
          while ((line = bfr.readLine()) != null) {
            msg += line;
          }
          bfr.close();

        } catch (IOException e) {
          e.printStackTrace();
        }

        break;
      }

      case 2: {
        msg = "user={";
        msg += "\"id\":\"" + intent.getStringExtra(Account.USER_ID) + "\", ";
        msg += "\"passwd\":\"" + intent.getStringExtra(Account.USER_PASS) + "\", ";
        msg += "\"age\":\"" + intent.getStringExtra(Account.USER_AGE) + "\", ";
        msg += "\"gender\":\"" + intent.getStringExtra(Account.USER_GENDER) + "\", ";
        msg += "\"job\":\"" + intent.getStringExtra(Account.USER_JOB) + "\", ";
        msg += "\"act_time\":\"" + intent.getStringExtra(Account.USER_ATIV_TIME) + "\", ";
        msg += "\"reg_id\":\"" + intent.getStringExtra(PushActivity.PROPERTY_REG_ID) + "\"} ";

        break;
      }

      case 3: {
        int id=1;
        for(int i=0; i< DataSheet.getFlowList().size(); i++)
          if(intent.getStringExtra("selectItem").equalsIgnoreCase(DataSheet.getFlowList().get(i).getName())){
            id = DataSheet.getFlowList().get(i).getId();
            break;
          }

        msg = "START|" + id;
        break;
      }
    }

    return msg;
  }
}