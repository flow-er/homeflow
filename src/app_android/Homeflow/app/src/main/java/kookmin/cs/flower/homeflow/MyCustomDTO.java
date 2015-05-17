package kookmin.cs.flower.homeflow;

import java.util.ArrayList;

/**
 * @brief class for listview Customizing
 * @details This class implies construction of each row in listview.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.4
 * @date 2015-05-17
 */
public class MyCustomDTO {
  String text;
  int btn1, btn2, btn3;
  ArrayList<String> childList;

  /**
   * @brief constructor of MyCustomDTO class
   * @param btn1
   * @param text
   * @param btn2
   * @param btn3
   * @param childList
   */
  public MyCustomDTO(int btn1, String text, int btn2, int btn3, ArrayList<String> childList) {
    this.btn1 = btn1;
    this.text = text;
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
  public String getFlowText() { return text; }

  /**
   * @brief method for setting text
   * @param text
   */
  public void setFlowText(String text) { this.text = text; }

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
  public void setChildList(ArrayList<String> childList) { this.childList = childList; }
}
