/**
 * @namespace kookmin.cs.homeflow.data
 * @brief data classes of HomeFlow app
 * @details HomeFlow의 data class files
 */
package kookmin.cs.homeflow.data;

import java.util.ArrayList;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.1
 * @brief an Class is data of Device
 * @details Device의 data를 가지고 있는 class이다. device의 이름과 기능들의 리스트를 담고 있다.
 */
public class Device {

  public String name;
  public ArrayList<Function> listFunction;

  public Device(String deviceName) {
    name = deviceName;
    listFunction = new ArrayList<>();
  }

  public void addFunction(Function func) {
    listFunction.add(func);
  }

  public String getName() {
    return name;
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
