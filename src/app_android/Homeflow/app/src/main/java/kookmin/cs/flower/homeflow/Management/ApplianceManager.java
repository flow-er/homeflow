package kookmin.cs.flower.homeflow.Management;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.data.Appliance;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.2
 * @date 2015-04-07
 */
public class ApplianceManager {

  private FileManager fileManager = new FileManager();

  public ArrayList<String> getList() { return fileManager.getApplianceList(); }
  public void addAppliance(Appliance appliance) { fileManager.addApplianceflow(appliance); }
}
