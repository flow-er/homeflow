/**
 * @namespace kookmin.cs.homeflow.filestream
 * @brief java package of HomeFlow app
 * @details XML 파일을 읽고 쓰는데 필요한 모듈
 */
package kookmin.cs.homeflow.filestream;

import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import kookmin.cs.homeflow.data.Appliance;
import kookmin.cs.homeflow.data.Workflow;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.1
 * @brief an class is write xml file
 * @details workflow class 나 Appliance 클래스와 저장할 경로를 받아서 xml 파일로 변환하여 저장하는 class 이다.
 * @todo develop ...
 */
public class XMLOutput {

  private final static String FLOW_NAME = "flowname";
  private final static String WORK = "work";
  private final static String APPLIANCE_NAME = "appname";
  private final static String FUNCTION = "function";
  private final static String NAME = "name";
  private final static String ID = "id";

  private XmlSerializer serializer;
  private StringWriter writer;

  public XMLOutput() {
  }

  public void writeXml(FileOutputStream fos, Workflow workflow) {
    serializer = Xml.newSerializer();
    writer = new StringWriter();

    try {
      serializer.setOutput(writer);
      serializer.startDocument("UTF-8", true);

      serializer.startTag("", "workflow");

      serializer.startTag("", FLOW_NAME);
      serializer.text(workflow.toString());
      serializer.endTag("", FLOW_NAME);

      for (int i = 0; i < workflow.getFlow().size(); i++) {
        serializer.startTag("", WORK);
        serializer.startTag("", NAME);
        serializer.text(workflow.getWorkName(i));
        serializer.endTag("", NAME);
        serializer.startTag("", ID);
        serializer.text(String.valueOf(workflow.getWorkId(i)));
        serializer.endTag("", ID);
        serializer.endTag("", WORK);
      }

      serializer.endTag("", "workflow");
      serializer.endDocument();
      serializer.flush();

      fos.write(writer.toString().getBytes());
      fos.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void writeXml(FileOutputStream fos, Appliance appliance) {
    serializer = Xml.newSerializer();
    writer = new StringWriter();

    try {
      serializer.setOutput(writer);
      serializer.startDocument("UTF-8", true);

      serializer.startTag("", "appliance");

      serializer.startTag("", APPLIANCE_NAME);
      serializer.text(appliance.toString());
      serializer.endTag("", APPLIANCE_NAME);

      for (int i = 0; i < appliance.getDevice().size(); i++) {
        serializer.startTag("", FUNCTION);
        serializer.startTag("", NAME);
        serializer.text(appliance.getFuncName(i));
        serializer.endTag("", NAME);
        serializer.startTag("", ID);
        serializer.text(String.valueOf(appliance.getFuncId(i)));
        serializer.endTag("", ID);
        serializer.endTag("", FUNCTION);
      }

      serializer.endTag("", "appliance");
      serializer.endDocument();
      serializer.flush();

      fos.write(writer.toString().getBytes());
      fos.close();

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
