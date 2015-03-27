/**
 * @namespace kookmin.cs.homeflow.filestream
 * @brief java package of HomeFlow app
 * @details XML 파일을 읽는데 필요한 모듈
 */
package kookmin.cs.homeflow.filestream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

import kookmin.cs.homeflow.data.Appliance;
import kookmin.cs.homeflow.data.Workflow;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.1
 * @brief an class read XML file and parsing it
 * @details XML 파일을 읽어서 필요한 부분을 파싱하는 클래스이다. Workflow xml 파일을 파싱하는 함수와 Appliance xml 파일을 파싱하는 함수가
 * 오버로딩 되어있다.
 * @todo develop ...
 */
public class XMLInput {

  private String text;

  public XMLInput() {
  }

  /**
   * @brief Workflow xml file parsing
   * @details Workflow xml 파일을 파싱한다. XMLPullParser 를 이용하여 태그의 시작 혹은 끝 일 때 원하는 동작을 한다. Appliance xml
   * 파일을 파싱하는 함수와 오버로딩 되어있다.
   */
  public Workflow parse(InputStream is, Workflow flow) {
    XmlPullParserFactory factory;
    XmlPullParser parser;
    try {
      factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      parser = factory.newPullParser();

      parser.setInput(is, null);

      int eventType = parser.getEventType();
      String workName = "defalt";
      int workId = 0;

      while (eventType != XmlPullParser.END_DOCUMENT) {
        String tagName = parser.getName();
        switch (eventType) {
          // 태그의 시작
          case XmlPullParser.START_TAG:
            if (tagName.equalsIgnoreCase("workflow")) {
              // create a new instance of employee
              flow = new Workflow();
            }
            break;

          // 텍스트 일 때
          case XmlPullParser.TEXT:
            text = parser.getText();
            break;

          // 태그의 끝
          case XmlPullParser.END_TAG:
            if (tagName.equalsIgnoreCase("flowname")) {
              flow.setName(text);
            } else if (tagName.equalsIgnoreCase("work")) {
              flow.addWork(workName, workId);
            } else if (tagName.equalsIgnoreCase("name")) {
              workName = text;
            } else if (tagName.equalsIgnoreCase("id")) {
              workId = Integer.parseInt(text);
            }

            break;

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

    return flow;
  }

  /**
   * @brief Appliance xml file parsing
   * @details Appliance xml 파일을 파싱한다. XMLPullParser 를 이용하여 태그의 시작 혹은 끝 일 때 원하는 동작을 한다. Workflow xml
   * 파일을 파싱하는 함수와 오버로딩 되어있다.
   */
  public Appliance parse(InputStream is, Appliance dev) {
    XmlPullParserFactory factory;
    XmlPullParser parser;
    try {
      factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      parser = factory.newPullParser();

      parser.setInput(is, null);

      int eventType = parser.getEventType();
      String funcName = "defalt";
      int funcId = 0;

      while (eventType != XmlPullParser.END_DOCUMENT) {
        String tagName = parser.getName();

        switch (eventType) {
          case XmlPullParser.START_TAG:
            if (tagName.equalsIgnoreCase("device")) {
              // create a new instance of employee
              dev = new Appliance();
            }
            break;

          case XmlPullParser.TEXT:
            text = parser.getText();
            break;

          case XmlPullParser.END_TAG:
            if (tagName.equalsIgnoreCase("devicename")) {
              dev.setName(text);
            } else if (tagName.equalsIgnoreCase("function")) {
              dev.addFunction(funcName, funcId);
            } else if (tagName.equalsIgnoreCase("name")) {
              funcName = text;
            } else if (tagName.equalsIgnoreCase("id")) {
              funcId = Integer.parseInt(text);
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

    return dev;
  }
}
