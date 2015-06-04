/**
 * @namespace kookmin.cs.homeflow.data
 * @brief data classes of HomeFlow app
 * @details HomeFlow의 data class files
 */
package kookmin.cs.flower.homeflow.data;

import java.util.ArrayList;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.4
 * @brief an Class is data of Device (appliance)
 * @details Device의 data를 가지고 있는 class이다. device의 이름과 기능들의 리스트를 담고 있다.
 * @date 2015-04-07
 */
public class Appliance {

  public String serialNumber;
  public String name;
  public int appid;
  public ArrayList<Function> listFunction;

  public Appliance() {
    listFunction = new ArrayList<>();
  }

  public Appliance(String SN) {
    serialNumber = SN;
  }

  public void addFunction(String funcName, int funcId) {
    listFunction.add(new Function(funcName, funcId));
  }

  public String toString() {
    return name;
  }

  public ArrayList<Function> getFunctions() {
    return listFunction;
  }

  public int getAppid() { return appid;  }

  public String getName() { return name; }

  public void setName(String devName) {
    name = devName;
  }

  public void setAppid(int appId) {
    appid = appId;
  }

}
