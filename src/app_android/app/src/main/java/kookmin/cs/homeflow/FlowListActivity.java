/**
 * @namespace kookmin.cs.homeflow
 * @brief java package of HomeFlow app
 * @details HomeFlow의 java file 집합
 */
package kookmin.cs.homeflow;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import kookmin.cs.homeflow.filestream.FileContent;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.9
 * @brief an Activity is show workflow list existent(able to edit)
 * @details 현재 등록된 workflow의 list를 보여준다. workflow를 새로 만들수 있는 Button이 있고 list를 클릭하면 workflow를 수정 또는 삭제할 수 있다. "/storage/emulated/0/homeflow/workflow/"
 * 폴더의 workflow file을 불러와 list로 보여준다.
 * @todo develop UI, List Item Click, Button Click etc...
 */
public class FlowListActivity extends ActionBarActivity implements View.OnClickListener {

  private Button addFlow_btn;
  private ArrayAdapter<String> adapter;

  /**
   * @brief Activity init
   * @details Activity를 init한다. workflow의 이름을 listview로 보여준다. listview의 속성을 변경한다.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_workflow_list);

    addFlow_btn = (Button) findViewById(R.id.btn_addflow);
    addFlow_btn.setOnClickListener(this);

    getSupportActionBar().setTitle("FlowList");

    // set adapter
    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, FileContent.getFlowList());

    // connect adapter
    ListView list = (ListView) findViewById(R.id.work_list);
    list.setAdapter(adapter);

    // ListView attribute
    list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    list.setDivider(new ColorDrawable(Color.WHITE));
    list.setDividerHeight(2);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btn_addflow:
        startActivity(new Intent(FlowListActivity.this, AddFlowActivity.class));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_login, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onStart() {
    super.onStart();
    adapter.notifyDataSetChanged();
  }
}
