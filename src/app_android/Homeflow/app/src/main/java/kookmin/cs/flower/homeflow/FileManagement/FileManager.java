package kookmin.cs.flower.homeflow.FileManagement;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import kookmin.cs.flower.homeflow.data.Appliance;
import kookmin.cs.flower.homeflow.data.FileContent;
import kookmin.cs.flower.homeflow.data.Workflow;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2 ---
 * @date 2015-04-07
 */
public class FileManager {

  public final static String FLOW_NAME = "flowname";
  public final static String WORK = "work";
  public final static String APPLIANCE_NAME = "appname";
  public final static String FUNCTION = "function";
  public final static String NAME = "name";
  public final static String ID = "id";

  public final static String FLOW = "flow";
  public final static String TRIGGER = "trigger";
  public final static String COWORK = "cowork";
  public final static String LOOP = "loop";
  public final static String CONDITION = "condition";
  public final static String ACTION = "id";

  private static int i = 0;

  private static boolean existFlowDir = false;
  private static boolean existApplianceDir = false;

  private static FileContent fileContent = new FileContent();

  public static ArrayList<Workflow> getFlowList() {
    return fileContent.getFlowList();
  }

  public static ArrayList<Appliance> getApplianceList() {
    return fileContent.getApplianceList();
  }

  public void addWorkflow(String[] argu, ArrayList<Workflow.Work> flowList) {
    XMLwrite(argu, flowList);
    updateFlow();
  }

  public void addApplianceflow(Appliance appliance) {
    fileContent.addAppliance(appliance);
  }

  static {
    updateFlow();
    updateAppliance();
  }

  public void updateFlowdata() {
    updateFlow();
  }

  public void updateAppliancedata() {
    updateAppliance();
  }

  private static void updateFlow() {
    if (fileContent.getFlowList().size() > 0) {
      fileContent.getFlowList().clear();
    }

    if (!existFlowDir) {
      mkFlowDir("workflow");
      existFlowDir = true;
    }

    File
        file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/workflow");
    String[] flowlist = file.list();

    if (flowlist == null) {
      return;
    }

    for (int i = 0; i < flowlist.length; i++) {
      try {
        fileContent.addFlow(
            new XMLInput().parse(new FileInputStream(file + "/" + flowlist[i]), new Workflow()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static void updateAppliance() {
    if (fileContent.getApplianceList().size() > 0) {
      fileContent.getApplianceList().clear();
    }

    if (!existApplianceDir) {
      mkFlowDir("appliance");
      existApplianceDir = true;
    }

    File
        file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/appliance/");
    String[] appliancelist = file.list();

    if (appliancelist == null) {
      return;
    }

    for (int i = 0; i < appliancelist.length; i++) {
      try {
        Appliance app = new Appliance();
        FileInputStream is = new FileInputStream(file.getPath() + "/" + appliancelist[i]);

        fileContent.addAppliance(new XMLInput().parse(is, app));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void removeFlow(String name) {
    //workflow.remove(name);
  }

  public void removeAppliance(String name) {
    //appliance.remove(name);
  }

  private static void mkFlowDir(String type) {
    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow");

    if (!file.exists()) {
      file.mkdir();
    }

    if (type.equalsIgnoreCase("workflow")) {
      file = new File(file.getPath() + "/workflow");
    } else if (type.equalsIgnoreCase("appliance")) {
      file = new File(file.getPath() + "/appliance");
    }

    if (!file.exists()) {
      file.mkdir();
    }
  }

  private String XMLwrite(String[] argu, ArrayList<Workflow.Work> flow) {

    Workflow workflow = new Workflow();

    workflow.setName(argu[0]);
    workflow.setDescription(argu[1]);
    workflow.setIsAuto(argu[2]);

    for (int i = 0; i < flow.size(); i++) {
      workflow.addWork(flow.get(i).getType(), flow.get(i).getId());
      workflow.getWork(i)
          .setArgu(flow.get(i).getCommand(), flow.get(i).getCond(), flow.get(i).getValue());
    }

    File
        file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/workflow/flow");

    int fileId = 1;

    while (new File(file.getPath() + fileId + ".xml").exists()) {
      fileId++;
    }

    workflow.setFlowId(fileId);
    file = new File(file.getPath() + fileId + ".xml");

    try {
      FileOutputStream fos = new FileOutputStream(file);
      new XMLOutput().writeXml(fos, workflow);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "flow" + fileId;

  }
}