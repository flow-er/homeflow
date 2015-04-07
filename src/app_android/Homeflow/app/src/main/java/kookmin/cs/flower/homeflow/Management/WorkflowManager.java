package kookmin.cs.flower.homeflow.Management;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class WorkflowManager {

  private FileManager fileManager = new FileManager();

  public ArrayList<String> getList() { return fileManager.getFlowList(); }
  public void addFlow(ArrayList<String> flowList) {
    fileManager.addWorkflow(flowList);
  }
}
