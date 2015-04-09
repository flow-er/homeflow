package kookmin.cs.flower.homeflow.Management;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.data.Workflow;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class WorkflowManager {

  private FileManager fileManager = new FileManager();

  public ArrayList<Workflow> getList() {
    return fileManager.getFlowList();
  }

  public void addFlow(String[] argu, ArrayList<Workflow.Work> flowList) {
    fileManager.addWorkflow(argu, flowList);
  }
}
