/**
 * @namespace kookmin.cs.homeflow.filestream
 * @brief java package of HomeFlow app
 * @details XML 파일을 읽고 쓰는데 필요한 모듈
 */
package kookmin.cs.flower.homeflow.FileManagement;

import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import kookmin.cs.flower.homeflow.data.Appliance;
import kookmin.cs.flower.homeflow.data.Workflow;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @brief an class is write xml file
 * @details workflow class 나 Appliance 클래스와 저장할 경로를 받아서 xml 파일로 변환하여 저장하는 class 이다.
 * @date 2015-04-07
 * @todo develop ...
 */
public class XMLOutput {

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

      // start flow tag
      serializer.startTag("", FileManager.FLOW);
      serializer.attribute("", "id", "" + workflow.getFlowId());
      serializer.attribute("", "name", workflow.toString());
      serializer.attribute("", "description", workflow.getDescription());
      serializer.attribute("", "isAuto", workflow.getIsAuto());

      for (int i = 0; i < workflow.getFlow().size(); i++) {

        serializer.startTag("", workflow.getWork(i).getType());
        serializer.attribute("", "id", workflow.getWork(i).getId());
        serializer.attribute("", "command", workflow.getWork(i).getCommand());
        serializer.attribute("", "cond", workflow.getWork(i).getCond());
        serializer.attribute("", "value", workflow.getWork(i).getValue());
        serializer.endTag("", workflow.getWork(i).getType());
      }

      serializer.endTag("", FileManager.FLOW);
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

      serializer.startTag("", FileManager.APPLIANCE_NAME);
      serializer.text(appliance.toString());
      serializer.endTag("", FileManager.APPLIANCE_NAME);

      for (int i = 0; i < appliance.getDevice().size(); i++) {
        serializer.startTag("", FileManager.FUNCTION);
        serializer.startTag("", FileManager.NAME);
        serializer.text(appliance.getFuncName(i));
        serializer.endTag("", FileManager.NAME);
        serializer.startTag("", FileManager.ID);
        serializer.text(String.valueOf(appliance.getFuncId(i)));
        serializer.endTag("", FileManager.ID);
        serializer.endTag("", FileManager.FUNCTION);
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