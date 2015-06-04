package kookmin.cs.flower.homeflow.data;

import java.util.ArrayList;

/**
 * Created by sloth on 2015-05-26.
 */
public class Node {
  private String type;
  private int appid;
  private int command;
  private int option;
  private int number;

  private ArrayList<Node> nextNode = new ArrayList<>();

  public Node() {}
  public Node(String nodeType, int nodeAppId, int nodeCommand, int nodeOption) {
    type = nodeType;
    appid = nodeAppId;
    command = nodeCommand;
    option = nodeOption;
  }

  public String getType() { return type; }
  public int getAppId() { return appid; }
  public int getCommand() { return command; }
  public int getOption() { return option; }
  public int getNumber() { return number; }
  public ArrayList<Node> getNextNode() { return nextNode; }

  public void setType(String nodeType) { type = nodeType; }
  public void setappid(int nodeAppId) { appid = nodeAppId; }
  public void setCommand(int nodeCommand) { command = nodeCommand; }
  public void setOption(int nodeOption) { option = nodeOption; }
  public void setNumber(int nodeNumber) {
    option = nodeNumber;
  }
  public String toString() { return DataSheet.getApplianceName(appid); }

  public void addNode(Node newNode) { nextNode.add(newNode); }
}
