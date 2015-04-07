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

  public String name;
  public ArrayList<Function> listFunction;

  public Appliance() {
    listFunction = new ArrayList<>();
  }

  public void addFunction(String funcName, int funcId) {
    listFunction.add(Function.newInstance(funcName, funcId));

  }

  public String toString() {
    return name;
  }

  public ArrayList<Function> getDevice() {
    return listFunction;
  }

  public String getFuncName(int index) {
    return listFunction.get(index).functionName;
  }

  public int getFuncId(int index) {
    return listFunction.get(index).getId();
  }

  public void setName(String devName) {
    name = devName;
  }

  /**
   * @author Jongho Lim, sloth@kookmin.ac.kr
   * @author Jinsung choi, bugslife102401@nate.com
   * @version 0.0.1
   * @brief an Class is inner class of Device class
   * @details Device class 의 inner class이다. Device의 기능들을 담을 수 있다.
   */
  private static class Function {

    private String functionName;
    private int functionId;

    public static Function newInstance(String funcName, int funcId) {
      Function func = new Function(funcName, funcId);
      return func;
    }

    public Function(String funcName, int funcId) {
      functionName = funcName;
      functionId = funcId;
    }

    public int getId() {
      return functionId;
    }

    public String getName() {
      return functionName;
    }
  }
}
