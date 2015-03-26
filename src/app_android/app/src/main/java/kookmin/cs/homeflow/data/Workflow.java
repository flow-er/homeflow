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
 * @brief an Class is data of workflow
 * @details workflow의 data를 가지고 있는 class이다. 동작할 일들의 이름과 순서를 담고 있다.
 */
public class Workflow {

  private String fName;
  private ArrayList<Work> workflow;

  public Workflow(String flowName) {
    fName = flowName;
    workflow = new ArrayList<>();
  }

  public void addWork(Work work) {
    workflow.add(work);
  }

  public String getName() {
    return fName;
  }

  /**
   * @author Jongho Lim, sloth@kookmin.ac.kr
   * @author Jinsung choi, bugslife102401@nate.com
   * @version 0.0.1
   * @brief an Class is inner class of Workflow class
   * @details Workflow class 의 inner class이다. workflow 의 각 각의 work를 담을 수 있다.
   */
  private class Work {

    private String wName;
    private int wId;

    public Work newInstance(String workName, int workId) {
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