package kookmin.cs.flower.homeflow;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for listview Customizing
 * @details This class implies construction of each row in listview.
 * @date 2015-04-08
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