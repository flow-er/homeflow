package kookmin.cs.flower.homeflow.FileManagement;

import android.os.Environment;
import android.util.Log;

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
 * @version 0.0.2
 * @date 2015-04-07
 */
public class FileManager {

  public final static String FLOW_NAME = "flowname";
  public final static String WORK = "work";
  public final static String APPLIANCE_NAME = "appname";
  public final static String FUNCTION = "function";
  public final static String NAME = "name";
  public final static String ID = "id";
  private static int i = 0;

  private static boolean existFlowDir = false;
  private static boolean existApplianceDir = false;

  private static FileContent fileContent = new FileContent();

  public static ArrayList<String> getFlowList() {
    return fileContent.getFlowList();
  }
  public static ArrayList<String> getApplianceList() { return fileContent.getApplianceList(); }

  public void addWorkflow(ArrayList<String> flowList) {
    fileContent.addFlow("flow" + i++);
    XMLwrite(flowList);
  }
  public void addApplianceflow(Appliance appliance) { fileContent.addAppliance(appliance.toString()); }

  static {
    updateFlow();
    updateAppliance();
  }
  public void updateFlowdata() { updateFlow(); }
  public void updateAppliancedata() { updateAppliance(); }
  private static void updateFlow() {
    if (fileContent.getFlowList().size() > 0) {
      fileContent.getFlowList().clear();
    }

    if (!existFlowDir) {
      mkFlowDir("workflow");
      existFlowDir = true;
      Log.i("mytag", "mkdir");
    }

    File
        file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/workflow");
    String[] flowlist = file.list();

    Log.i("mytag", file.getPath());

    if(flowlist == null) {
      return;
    }
    Log.i("mytag", "flow list num : " + flowlist.length);

    for (int i = 0; i < flowlist.length; i++) {
      fileContent.addFlow(flowlist[i].substring(0, flowlist[i].length() - 4));
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

    Log.i("mytag", file.getPath());
    if(appliancelist == null) {
      return ;
    }

    Log.i("mytag", "appliance list num : " + appliancelist.length);
    for (int i = 0; i < appliancelist.length; i++) {
      try {
        Appliance app = new Appliance();
        FileInputStream is = new FileInputStream(file.getPath() + "/" + appliancelist[i]);

        fileContent.addAppliance(new XMLInput().parse(is, app).toString());
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

  private void XMLwrite(ArrayList<String> flow) {

    Workflow workflow = new Workflow();

    for (int i = 0; i < flow.size(); i++) {
      String workname = flow.get(i).toString();
      workflow.addWork(workname, fileContent.getApplianceId(workname));
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
