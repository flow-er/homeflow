package kookmin.cs.flower.homeflow;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.data.Flow;
import kookmin.cs.flower.homeflow.data.Node;

/**
 * @brief class for listview Customizing
 * @details This class implies construction of each row in listview.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-17
 */
public class MyCustomDTO {
  Flow flow;
  int btn1, btn2, btn3;
  ArrayList<Node> childList;

  /**
   * @brief constructor of MyCustomDTO class
   * @param btn1
   * @param flow
   * @param btn2
   * @param btn3
   * @param childList
   */
  public MyCustomDTO(int btn1, Flow flow, int btn2, int btn3, ArrayList<Node> childList) {
    this.btn1 = btn1;
    this.flow = flow;
    this.btn2 = btn2;
    this.btn3 = btn3;
    this.childList = childList;
  }
  /**
   * @brief method for getting btn1
   * @return
   */
  public int getBtn1() { return btn1; }

  /**
   * @brief method for setting btn1
   * @param btn1
   */
  public void setBtn1(int btn1) { this.btn1 = btn1; }

  /**
   * @brief method for getting text
   * @return
   */
  public String getFlowText() { return flow.toString(); }

  /**
   * @brief method for setting text
   * @param text
   */
  public void setFlowText(String text) { this.flow = flow; }

  /**
   * @brief method for getting btn2
   * @return
   */
  public int getBtn2() { return btn2; }

  /**
   * @brief method for setting btn2
   * @param btn2
   */
  public void setBtn2(int btn2) { this.btn2 = btn2; }

  /**
   * @brief method for getting btn3
   * @return
   */
  public int getBtn3() { return btn3; }

  /**
   * @brief method for setting btn3
   * @param btn3
   */
  public void setBtn3(int btn3) { this.btn3 = btn3; }

  /**
   * @brief method for getting childlist
   * @return
   */
  public Object getChildList() { return childList; }

  /**
   * @brief method for setting childlist
   * @param childList
   */
  public void setChildList(ArrayList<Node> childList) { this.childList = childList; }
}