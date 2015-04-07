package kookmin.cs.flower.homeflow.data;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07

 */
public class FileContent {

  private static ArrayList<String> workflow = new ArrayList<String>();
  private static ArrayList<String> appliance = new ArrayList<String>();
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

  public void addFlow(String name) {
    workflow.add(name);
  }

  public void addAppliance(String name) {
    appliance.add(name);
  }

  public ArrayList<String> getFlowList() { return workflow; }
  public ArrayList<String> getApplianceList() {
    return appliance;
  }
  public int getApplianceId(String key) { return applianceId.get(key); }

}
