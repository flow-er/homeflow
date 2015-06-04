package kookmin.cs.flower.homeflow;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import kookmin.cs.flower.homeflow.FileManagement.FileManager;
import kookmin.cs.flower.homeflow.Management.WorkflowManager;
import kookmin.cs.flower.homeflow.Service.SocketService;
import kookmin.cs.flower.homeflow.data.DataSheet;
import kookmin.cs.flower.homeflow.data.Node;

/**
 * @author Jinsung Choi, bugslife102401@nate.com
 * @version 0.0.2
 * @brief class for showing addflow.xml layout
 * @details This class includes add_flow_list listview and add_flow_btn button. If you click
 * add_flow_list, workentry.xml layout will appear. If you click add_flow_btn, flowreg.xml layout
 * will appear.
 * @date 2015-04-06
 */
public class AddFlow extends Fragment
    implements OnItemClickListener, View.OnClickListener {

  ListView listView;
  static ArrayList<Node> addNodeList = new ArrayList<>();

  static int i = 0;

  static {
    addNodeList.clear();
    addNodeList.add(new Node(WorkflowManager.TRIGGER, 14, 0, 0));
    i++;
  }

  /**
   * @return rootView
   * @brief method for showing addflow.xml
   * @details This method sets clicked-events on add_flow_list and add_flow_btn
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {


    if (getArguments() != null) {
      Log.i("mytag6", ""+getArguments().getInt("funcp"));

      addNodeList.add(new Node(getArguments().getString("type"),
                               DataSheet.getApplianceId(getArguments().getString("appli")), getArguments().getInt("funcp")+1, 0));
      Log.i("mytag", "input type : " + i + " " + getArguments().getString("type"));
      i++;
    }
    View rootView = inflater.inflate(R.layout.addflow, container, false);

    listView = (ListView) rootView.findViewById(R.id.add_flow_list);
    ArrayAdapter adapter =
        new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, addNodeList);
    listView.setAdapter(adapter);

    listView.setOnItemClickListener(this);

    Button add_flow_btn = (Button) rootView.findViewById(R.id.add_flow_btn);
    Button add_item_btn = (Button) rootView.findViewById(R.id.add_item_btn);

    add_flow_btn.setOnClickListener(this);
    add_item_btn.setOnClickListener(this);

    return rootView;
  }

  /**
   * @brief method for determining action of add_flow_list
   * @details If you click add_flow_list, workentry.xml layout will appear.
   */
  @Override
  public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    WorkEntry workEntry = new WorkEntry();
    getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
  }

  /**
   * @brief method for determining action of add_flow_btn
   * @details If you click add_flow_btn, flowreg.xml layout will appear.
   */
  @Override
  public void onClick(View v) {
    Log.i("mytag3", "id : "+v.getId() + "R.id.add_flow_btn : " + R.id.add_flow_btn + " i = " + i);
    if (v.getId() == R.id.add_flow_btn && i > 1) {

      final ArrayList<Node> workList = new ArrayList<>();
      Log.i("mytag", "flow size : " + addNodeList.size());
      for (int i = 0; i < addNodeList.size(); i++) {
        workList.add(addNodeList.get(i));
      }

      AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
      final AlertDialog.Builder alert2 = new AlertDialog.Builder(getActivity());

      alert.setTitle("등록");
      alert.setMessage("Flow의 이름을 입력하세요");

      alert2.setTitle("등록");
      alert2.setMessage("Flow의 설명을 입력하세요");

      final EditText flowName = new EditText(getActivity());
      final EditText flowDesc = new EditText(getActivity());

      flowName.setHint("Flow 제목을 입력하세요");
      flowDesc.setHint("Flow의 설명을 입력하세요");

      alert.setView(flowName);
      alert2.setView(flowDesc);

      final Bundle args = new Bundle();

      alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          args.putString(WorkflowManager.FLOWNAME, flowName.getText().toString());
          alert2.show();
        }
      });

      alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          FlowReg flowReg = new FlowReg();
          getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
        }
      });

      alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          args.putString(WorkflowManager.DESCRIPTION, flowDesc.getText().toString());
          args.putString(WorkflowManager.ISAUTO, "false");

          String filename = FileManager.writeFlow(args, workList);
          Intent sendFilename = new Intent(getActivity(), SocketService.class);
          sendFilename.putExtra("filename", filename + ".xml");
          sendFilename.putExtra("mode", 1);
          getActivity().startService(sendFilename);

          FlowReg flowReg = new FlowReg();
          getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
        }
      });

      alert2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          FlowReg flowReg = new FlowReg();
          getFragmentManager().beginTransaction().replace(R.id.realtabcontent, flowReg).commit();
        }
      });

      alert.show();

      addNodeList.clear();
      addNodeList.add(new Node(WorkflowManager.TRIGGER, 14, 0, 0));

      i = 1;

    } else if (v.getId() == R.id.add_item_btn) {
      WorkEntry workEntry = new WorkEntry();
      getFragmentManager().beginTransaction().replace(R.id.realtabcontent, workEntry).commit();
    }

    Log.i("mytag", "" + v.getId() + ' ' + R.id.add_item_btn);
  }
}