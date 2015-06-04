package kookmin.cs.flower.homeflow.data;

import java.util.ArrayList;

/**
 * Created by sloth on 2015-05-26.
 */
public class Flow {
  private String filename;
  private int id;
  private String name;
  private String description;
  private boolean auto;
  private ArrayList<Node> nodelist = new ArrayList<>();

  public Flow() { }
  public Flow(int flowId, String flowName, String flowDesc, boolean flowAuto) {
    id = flowId;
    name = flowName;
    description = flowDesc;
    auto = flowAuto;
  }

  public int getId() { return id; }
  public String getName() { return name; }
  public String getDescription() { return description; }
  public boolean getAuto() { return auto; }
  public ArrayList<Node> getNodeList() { return nodelist; }
  public String getFilename() { return filename; }
  public String toString() { return name; }

  public void setId(int flowId) { id = flowId; }
  public void setName(String flowName) { name = flowName; }
  public void setDescription(String flowDesc) { description = flowDesc; }
  public void setAuto(boolean flowAuto) { auto = flowAuto; }
  public void setFilename(String flowFilename) { filename = flowFilename; }

  public void addnode(Node newNode) { nodelist.add(newNode); }
}