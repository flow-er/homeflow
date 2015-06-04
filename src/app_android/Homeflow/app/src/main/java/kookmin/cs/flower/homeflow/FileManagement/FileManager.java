package kookmin.cs.flower.homeflow.FileManagement;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import kookmin.cs.flower.homeflow.Management.ApplianceManager;
import kookmin.cs.flower.homeflow.Management.WorkflowManager;
import kookmin.cs.flower.homeflow.data.Appliance;
import kookmin.cs.flower.homeflow.data.DataSheet;
import kookmin.cs.flower.homeflow.data.Flow;
import kookmin.cs.flower.homeflow.data.Node;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class FileManager {
  public static String TARGET_SERVER_IP = "52.68.106.249";
  public static String TARGET_SERVER_PORT = "52222";

  private static boolean existFlowDir = false;
  private static boolean existApplianceDir = false;

  static {
    updateFlow();
    updateAppliance();
  }

  public static void updateFlow() {
    if (DataSheet.getFlowList().size() > 0) {
      DataSheet.getFlowList().clear();
    }

    if (DataSheet.getHashFlow().size() > 0) {
      DataSheet.getHashFlow().clear();
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
        FileInputStream is = new FileInputStream(file + "/" + flowlist[i]);
        WorkflowManager.parseFlow(is);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void updateAppliance() {
    if (DataSheet.getApplianceList().size() > 0) {
      for(int i=0; i<DataSheet.getApplianceList().size(); i++) {
        DataSheet.getApplianceList().get(i).getFunctions().clear();
      }
      DataSheet.getApplianceList().clear();
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
        ApplianceManager.addAppliance(is);

        Log.i("mytag", file.getPath() + "/" + appliancelist[i]);
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

  public static String writeFlow(Bundle args, ArrayList<Node> nodes) {

    Flow workflow = new Flow();

    workflow.setName(args.getString(WorkflowManager.FLOWNAME));
    workflow.setDescription(args.getString(WorkflowManager.DESCRIPTION));
    workflow.setAuto( args.getString(WorkflowManager.ISAUTO).equalsIgnoreCase("true") );

    for (int i = 0; i < nodes.size(); i++) {
      Log.i("mytag", "type : " + nodes.get(i).getType());
      workflow.addnode(new Node(nodes.get(i).getType(), nodes.get(i).getAppId(), nodes.get(i).getCommand(), 0));
    }

    File file =
        new File(Environment.getExternalStorageDirectory().getPath() + "/HomeFlow/workflow/flow");

    int fileId = 1;

    while (new File(file.getPath() + fileId + ".xml").exists()) {
      fileId++;
    }

    workflow.setId(fileId);

    file = new File(file.getPath() + fileId + ".xml");
    workflow.setFilename(file.getName());

    try {
      FileOutputStream fos = new FileOutputStream(file);
      StringWriter writer = WorkflowManager.createFlow(workflow);

      fos.write(writer.toString().getBytes());
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    updateFlow();
    return "flow" + fileId;
  }
}