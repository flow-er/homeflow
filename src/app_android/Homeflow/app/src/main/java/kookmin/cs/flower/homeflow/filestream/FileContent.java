package kookmin.cs.homeflow.filestream;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import kookmin.cs.homeflow.data.Appliance;
import kookmin.cs.homeflow.data.Workflow;

public class FileContent {

  private static ArrayList<String> workflow = new ArrayList<String>();
  private static ArrayList<String> appliance = new ArrayList<String>();
  private static HashMap<String, Integer> applianceId = new HashMap<String, Integer>();
  private static boolean existFlowDir;
  private static boolean existApplianceDir;

  public final static String FLOW_NAME = "flowname";
  public final static String WORK = "work";
  public final static String APPLIANCE_NAME = "appname";
  public final static String FUNCTION = "function";
  public final static String NAME = "name";
  public final static String ID = "id";

  static {
    applianceId.put("세탁기", 1);
    applianceId.put("청소기", 2);
    applianceId.put("침대전등", 3);
    applianceId.put("컴퓨터", 4);
    applianceId.put("현관 형광등", 5);
    applianceId.put("공기청정기", 6);
    applianceId.put("전자레인지", 7);
    applianceId.put("토스터기", 8);
    applianceId.put("보일러", 9);

    existFlowDir = false;
    existApplianceDir = false;

    updateFlow();
    updateAppliance();
  }

  public static void addFlow(String name) {
    workflow.add(name);
  }

  public static void addAppliance(String name) {
    appliance.add(name);
  }

  public static ArrayList<String> getFlowList() {
    return workflow;
  }

  public static ArrayList<String> getApplianceList() {
    return appliance;
  }

  public static void updateFlow() {
    if (workflow.size() > 0) {
      workflow.clear();
    }

    if (!existFlowDir) {
      mkFlowDir("workflow");
      existFlowDir = true;
    }

    File
        file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/workflow");
    String[] flowlist = file.list();

    for (int i = 0; i < flowlist.length; i++) {
      workflow.add(flowlist[i].substring(0, flowlist[i].length() - 4));
    }
  }

  public static void updateAppliance() {
    if (appliance.size() > 0) {
      appliance.clear();
    }

    if (!existApplianceDir) {
      mkFlowDir("appliance");
      existApplianceDir = true;
    }

    File
        file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/appliance");
    String[] appliancelist = file.list();

    for (int i = 0; i < appliancelist.length; i++) {
      try {
        Appliance app = new Appliance();
        FileInputStream is = new FileInputStream(file.getPath() + "/" + appliancelist[i]);

        appliance.add(new XMLInput().parse(is, app).toString());
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

  public static void XMLwrite(ArrayList<String> flow) {

    Workflow workflow = new Workflow();

    for (int i = 0; i < flow.size(); i++) {
      String workname = flow.get(i).toString();
      workflow.addWork(workname, applianceId.get(workname));
    }

    File
        file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/workflow/flow");
    int fileId = 1;

    while (new File(file.getPath() + fileId + ".xml").exists()) {
      fileId++;
    }

    file = new File(file.getPath() + fileId + ".xml");
    workflow.setName("flow" + fileId);

    try {
      FileOutputStream fos = new FileOutputStream(file);
      new XMLOutput().writeXml(fos, workflow);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
