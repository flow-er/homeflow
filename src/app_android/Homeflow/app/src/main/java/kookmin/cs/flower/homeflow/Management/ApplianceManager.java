package kookmin.cs.flower.homeflow.Management;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import kookmin.cs.flower.homeflow.data.Appliance;
import kookmin.cs.flower.homeflow.data.DataSheet;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class ApplianceManager {

  public static String APPLIANCE = "appliance";
  public static String APP_NAME = "name";
  public static String APPID = "id";
  public static String FUNCTION = "function";
  public static String FUNCTION_NAME = "name";
  public static String FUNCTION_ID = "command";

  public static void addAppliance(InputStream is) {
    XmlPullParserFactory factory;
    XmlPullParser parser;
    Appliance app = new Appliance();

    try {
      factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      parser = factory.newPullParser();

      parser.setInput(is, null);

      int eventType = parser.getEventType();

      while (eventType != XmlPullParser.END_DOCUMENT) {
        String tagName = parser.getName();

        switch (eventType) {
          case XmlPullParser.START_TAG:
            if (tagName.equalsIgnoreCase(APPLIANCE)) {
              app.setName(parser.getAttributeValue("", APP_NAME));
              app.setAppid(Integer.parseInt(parser.getAttributeValue("", APPID)));
            } else if (tagName.equalsIgnoreCase(FUNCTION)) {
              app.addFunction(parser.getAttributeValue("", FUNCTION_NAME),
                              Integer.parseInt(parser.getAttributeValue("", FUNCTION_ID)));
            }
            break;

          default:
            break;
        }
        eventType = parser.next();
      }
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    DataSheet.addAppliance(app);

    for(int i=0; i<app.getFunctions().size(); i++ ) {
      Log.i("mytag5", app.getFunctions().get(i).getName() + " id : " + app.getFunctions().get(i).getId());
    }
    Log.i("mytag3", "toString : " + app.toString());
  }
}
