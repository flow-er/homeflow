package kookmin.cs.flower.homeflow;

/**
 * @brief class for listview Customizing
 * @details This class implies construction of each row in listview.
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @date 2015-04-08
 */
public class MyCustomDTO {
  int imgIcon;
  String text;
  int btn1, btn2;

  public MyCustomDTO(int imgIcon, String text, int btn1, int btn2) {
    this.imgIcon = imgIcon;
    this.text = text;
    this.btn1 = btn1;
    this.btn2 = btn2;
  }

  public int getImgIcon() {
    return imgIcon;
  }

  public void setImgIcon(int imgIcon) {
    this.imgIcon = imgIcon;
  }

  public String getFlowText() {
    return text;
  }

  public void setFlowText(String text) {
    this.text = text;
  }

  public int getBtn1() {
    return btn1;
  }

  public void setBtn1(int btn1) {
    this.btn1 = btn1;
  }

  public int getBtn2() {
    return btn2;
  }

  public void setBtn2(int btn2) {
    this.btn2 = btn2;
  }
}
