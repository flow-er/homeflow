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
 * @brief an Class is data of workflow
 * @details workflow의 data를 가지고 있는 class이다. 동작할 일들의 이름과 순서를 담고 있다.
 * @date 2015-04-07
 */
public class Workflow {

  private String fName;
  private ArrayList<Work> workflow;

  public Workflow() {
    workflow = new ArrayList<>();
  }

  public void addWork(String workName, int workId) {
    workflow.add(Work.newInstance(workName, workId));
  }

  public String toString() {
    return fName;
  }

  public ArrayList<Work> getFlow() {
    return workflow;
  }

  public String getWorkName(int index) {
    return workflow.get(index).getName();
  }

  public int getWorkId(int index) {
    return workflow.get(index).getId();
  }

  public void setName(String name) {
    fName = name;
  }

  /**
   * @author Jongho Lim, sloth@kookmin.ac.kr
   * @author Jinsung choi, bugslife102401@nate.com
   * @version 0.0.1
   * @brief an Class is inner class of Workflow class
   * @details Workflow class 의 inner class이다. workflow 의 각 각의 work를 담을 수 있다.
   */
  private static class Work {

    private String wName;
    private int wId;

    public static Work newInstance(String workName, int workId) {
      Work work = new Work(workName, workId);

      return work;
    }

    public Work(String workName, int workId) {
      wName = workName;
      wId = workId;
    }

    public String getName() {
      return wName;
    }

    public int getId() {
      return wId;
    }
  }
}