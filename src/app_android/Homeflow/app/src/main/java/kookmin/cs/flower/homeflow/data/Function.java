package kookmin.cs.flower.homeflow.data;

/**
 * Created by sloth on 2015-05-26.
 */
/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.1
 * @brief an Class is inner class of Device class
 * @details Device class 의 inner class이다. Device의 기능들을 담을 수 있다.
 */
public class Function {

  private String functionName;
  private int functionId;

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

  public String toString() { return functionName; }
}
