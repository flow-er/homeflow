package kookmin.cs.flower.homeflow.Management;

import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.data.DataSheet;
import kookmin.cs.flower.homeflow.data.Flow;
import kookmin.cs.flower.homeflow.data.Node;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class WorkflowManager {

  // xml tags
  public final static String FLOW = "flow";
  public final static String TRIGGER = "trigger";
  public final static String LOOP = "loop";
  public final static String CONDITION = "condition";
  public final static String ACTION = "action";

  // flow attributes
  public final static String FLOWID = "id";
  public final static String FLOWNAME = "name";
  public final static String DESCRIPTION = "description";
  public final static String ISAUTO = "isAuto";

  // node attributes
  public final static String APPID = "appid";
  public final static String COMMAND = "command";
  public final static String OPTION = "option";
  public final static String NUMBER = "num";

  public static StringWriter createFlow(Flow workflow) {
    XmlSerializer serializer = Xml.newSerializer();
    StringWriter writer = new StringWriter();

    try {
      serializer.setOutput(writer);
      serializer.startDocument("UTF-8", true);

      // start flow tag
      serializer.startTag("", FLOW);
      serializer.attribute("", FLOWID, "" + workflow.getId());
      serializer.attribute("", FLOWNAME, workflow.toString());
      serializer.attribute("", DESCRIPTION, workflow.getDescription());
      serializer.attribute("", ISAUTO, (workflow.getAuto()) ? "true" : "false");

      ArrayList<Node> nodes = workflow.getNodeList();
      for (int i = 0; i < nodes.size(); i++) {
        if (nodes.get(i).getType().equalsIgnoreCase("trigger")) {
          continue;
        }
        serializer.startTag("", nodes.get(i).getType());
        serializer.attribute("", APPID, "" + workflow.getNodeList().get(i).getAppId());
        serializer.attribute("", NUMBER, "" + i);
        serializer.attribute("", COMMAND, "" + workflow.getNodeList().get(i).getCommand());
        serializer.attribute("", OPTION, "" + workflow.getNodeList().get(i).getOption());
        serializer.endTag("", nodes.get(i).getType());
      }

      serializer.endTag("", FLOW);
      serializer.endDocument();
      serializer.flush();


    } catch (IOException e) {
      e.printStackTrace();
    }

    return writer;
  }

  public static void parseFlow(InputStream is) {

    XmlPullParserFactory factory;
    XmlPullParser parser;
    Flow flow = new Flow();

    try {
      factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      parser = factory.newPullParser();

      parser.setInput(is, null);

      int eventType = parser.getEventType();

      while (eventType != XmlPullParser.END_DOCUMENT) {
        String tagName = parser.getName();
        Node node = new Node();

        switch (eventType) {
          // 태그의 시작
          case XmlPullParser.START_TAG:
            if (tagName.equalsIgnoreCase(FLOW)) {
              String id = parser.getAttributeValue("", FLOWID);

              flow.setId(Integer.parseInt(id));
              flow.setName(parser.getAttributeValue("", FLOWNAME));
              flow.setDescription(parser.getAttributeValue("", DESCRIPTION));
              flow.setAuto((parser.getAttributeValue("", ISAUTO).equalsIgnoreCase("true")));

              flow.setFilename("flow" + id + ".xml");
              DataSheet.getHashFlow().put(Integer.parseInt(id), DataSheet.getHashFlow().size());

            } else if (tagName.equalsIgnoreCase(ACTION)) {
              node.setType(ACTION);
              node.setappid(Integer.parseInt(parser.getAttributeValue("", APPID)));
              node.setCommand(Integer.parseInt(parser.getAttributeValue("", COMMAND)));
              node.setOption(Integer.parseInt(parser.getAttributeValue("", OPTION)));
              node.setNumber(Integer.parseInt(parser.getAttributeValue("", NUMBER)));

              flow.addnode(node);

            } else if (tagName.equalsIgnoreCase(LOOP)) {
              node.setType(LOOP);
              node.setappid(Integer.parseInt(parser.getAttributeValue("", APPID)));
              node.setCommand(Integer.parseInt(parser.getAttributeValue("", COMMAND)));
              node.setOption(Integer.parseInt(parser.getAttributeValue("", OPTION)));

              flow.addnode(node);

            } else if (tagName.equalsIgnoreCase(CONDITION)) {
              node.setType(CONDITION);
              node.setappid(Integer.parseInt(parser.getAttributeValue("", APPID)));
              node.setCommand(Integer.parseInt(parser.getAttributeValue("", COMMAND)));
              node.setOption(Integer.parseInt(parser.getAttributeValue("", OPTION)));

              flow.addnode(node);

            }
          default:
            break;
        }
        eventType = parser.next(); // 다음 토큰으로
      }

    } catch (XmlPullParserException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    DataSheet.addFlow(flow);
  }

  public static void deleteFlow(ArrayList<String> filelist) {
    if (filelist.size() > 0) {
      for (int i = 0; i < filelist.size(); i++) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() +
                             "/HomeFlow/workflow/" + filelist.get(i));
        file.delete();

        sendDeleteMessage(file.getName().substring(4, file.getName().length()-4));
        Log.i("mytag", file.getPath());
      }
    }

    FileManager.updateFlow();
  }

  private static void sendDeleteMessage(final String id) {
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
                  + "/delete");
          HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

          urlConnection.setDoInput(true);
          urlConnection.setDoOutput(true);
          urlConnection.setUseCaches(false);
          urlConnection.setReadTimeout(2000);

          urlConnection.setRequestMethod("POST");
          urlConnection.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");

          String sendmsg = "DELETE|" + id;
          sendmsg = new String(sendmsg.getBytes(), "ISO-8859-1"); // 캐릭터셋 변경.
          urlConnection.setRequestProperty(HTTP.CONTENT_LEN, "" + sendmsg.length());

          DataOutputStream request = new DataOutputStream(urlConnection.getOutputStream());
          request.writeBytes(sendmsg);
          request.flush();
          request.close();

          InputStream response = new BufferedInputStream(urlConnection.getInputStream());

          int data;
          String msg="";
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