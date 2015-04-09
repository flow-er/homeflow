package kookmin.cs.flower.homeflow.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class FileContent {

  private static ArrayList<Workflow> workflowList = new ArrayList<Workflow>();
  private static ArrayList<Appliance> applianceList = new ArrayList<Appliance>();
  private static HashMap<String, Integer> applianceId = new HashMap<String, Integer>();

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
  }

  public void addFlow(Workflow workflow) {
    workflowList.add(workflow);
  }

  public void addAppliance(Appliance appliance) {
    applianceList.add(appliance);
  }

  public ArrayList<Workflow> getFlowList() {
    return workflowList;
  }

  public ArrayList<Appliance> getApplianceList() {
    return applianceList;
  }

  public int getApplianceId(String key) {
    return applianceId.get(key);
  }
}