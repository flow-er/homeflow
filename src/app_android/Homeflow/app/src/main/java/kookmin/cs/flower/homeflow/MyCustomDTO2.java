package kookmin.cs.flower.homeflow;

/**
 * @brief class for listview Customizing
 * @details This class implies construction of each row in listview.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-05-27
 */
public class MyCustomDTO2 {
  String text;

  /**
   * @brief constructor of MyCustomDTO2 class
   * @param text
   */
  public MyCustomDTO2(String text) {
    this.text = text;
  }

  /**
   * @brief method for getting text
   * @return
   */
  public String getFlowText() {
    return text;
  }

  /**
   * @brief method for setting text
   * @param text
   */
  public void setFlowText(String text) {
    this.text = text;
  }
}