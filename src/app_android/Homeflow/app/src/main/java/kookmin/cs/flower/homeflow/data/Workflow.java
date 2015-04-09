/**
 * @namespace kookmin.cs.homeflow.data
 * @brief data classes of HomeFlow app
 * @details HomeFlow의 data class files
 */
package kookmin.cs.flower.homeflow.data;

import java.util.ArrayList;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @version 0.0.4 ---
 * @brief an Class is data of workflow
 * @details workflow의 data를 가지고 있는 class이다. 동작할 일들의 이름과 순서를 담고 있다.
 * @date 2015-04-07
 */
public class Workflow {

  private int flowId;
  private String flowName;
  private String description;
  private String isAuto;
  private ArrayList<Work> workflow;

  public Workflow() {
    workflow = new ArrayList<>();
  }

  public void addWork(String type, String workId) {
    workflow.add(Work.newInstance(type, workId));
  }

  public String toString() {
    return flowName;
  }

  public ArrayList<Work> getFlow() {
    return workflow;
  }

  public int getFlowId() {
    return flowId;
  }

  public String getDescription() {
    return description;
  }

  public String getIsAuto() {
    return isAuto;
  }

  public Work getWork(int index) {
    return workflow.get(index);
  }

  public void setFlowId(int id) {
    flowId = id;
  }

  public void setName(String name) {
    flowName = name;
  }

  public void setDescription(String desc) {
    description = desc;
  }

  public void setIsAuto(String auto) {
    isAuto = auto;
  }

  /**
   * @author Jongho Lim, sloth@kookmin.ac.kr
   * @author Jinsung choi, bugslife102401@nate.com
   * @version 0.0.1
   * @brief an Class is inner class of Workflow class
   * @details Workflow class 의 inner class이다. workflow 의 각 각의 work를 담을 수 있다.
   */
  public static class Work {

    private String type;
    private String workId;
    private String command;
    private String cond;
    private String value;

    public static Work newInstance(String _type, String _workId) {
      Work work = new Work(_type, _workId);

      return work;
    }

    private Work(String _type, String _workId) {
      type = _type;
      workId = _workId;
    }

    public String getType() {
      return type;
    }

    public String getId() {
      return workId;
    }

    public String getCommand() {
      return command;
    }

    public String getCond() {
      return cond;
    }

    public String getValue() {
      return value;
    }

    public void setArgu(String _command, String _cond, String _value) {
      command = _command;
      cond = _cond;
      value = _value;
    }
  }
}