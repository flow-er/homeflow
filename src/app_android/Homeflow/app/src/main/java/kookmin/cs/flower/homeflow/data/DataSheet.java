package kookmin.cs.flower.homeflow.data;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class DataSheet {

  private static ArrayList<Flow> flows = new ArrayList<>();

  private static ArrayList<Appliance> applianceList = new ArrayList<>();

  private static HashMap<Integer, Integer> hashflowId = new HashMap<>();
  private static HashMap<Integer, String> flowState = new HashMap<>();
  private static HashMap<Integer, Integer> nodeState = new HashMap<>();

  public static int fileId = 0;

  public static void addFlow(Flow newFlow) {
    flows.add(newFlow);
  }

  public static void addAppliance(Appliance appliance) {
    applianceList.add(appliance);
  }

  public static ArrayList<Flow> getFlowList() {
    return flows;
  }

  public static ArrayList<Appliance> getApplianceList() {
    return applianceList;
  }

  public static HashMap<Integer, Integer> getHashFlow() {
    return hashflowId;
  }

  public static HashMap<Integer, String> getFlowState() {
    return flowState;
  }

  public static HashMap<Integer, Integer> getNodeState() {
    return nodeState;
  }

  public static int getApplianceId(String name) {
    for (int i = 0; i < applianceList.size(); i++) {
      if (applianceList.get(i).getName().equalsIgnoreCase(name)) {
        return applianceList.get(i).getAppid();
      }
    }

    return 0;
  }

  public static String getApplianceName(int id) {
    for (int i = 0; i < applianceList.size(); i++) {
      if (applianceList.get(i).getAppid() == id) {
        return applianceList.get(i).getName();
      }
    }

    return "트리거";
  }

  public static int getApplianceNum(String name) {
    for (int i = 0; i < applianceList.size(); i++) {
      if (applianceList.get(i).getName().equalsIgnoreCase(name)) {
        return i;
      }
    }

    return 0;
  }
}