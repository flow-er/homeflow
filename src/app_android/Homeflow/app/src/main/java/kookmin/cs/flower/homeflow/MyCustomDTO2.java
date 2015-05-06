package kookmin.cs.flower.homeflow;

/**
 * @brief class for listview Customizing
 * @details This class implies construction of each row in listview.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.0
 * @date 2015-05-06
 */
public class MyCustomDTO2 {
  String text;

  public MyCustomDTO2(String text) {
    this.text = text;
  }

  public String getFlowText() {
    return text;
  }

  public void setFlowText(String text) {
    this.text = text;
  }
}