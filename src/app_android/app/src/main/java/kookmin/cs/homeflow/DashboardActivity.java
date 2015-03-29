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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.3
 * @brief an Activity is dashboard of the progress of workflow
 * @details workflow 의 진행상황을 보여주는 Activity Class이다. ActionBar의 메뉴에서 workflow edit와 appliance edit 등을 할 수 있다.
 * @todo Read workflow file(xml parse), check progress of workflow, etc...
 */
public class DashboardActivity extends ActionBarActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    getSupportActionBar().setTitle("DashBoard");

    // set data
    ArrayList<String> dashlist = new ArrayList<String>();

    dashlist.add("if 시간되면?");
    dashlist.add("세탁기를 돌린다");
    dashlist.add("종료되면 보고한다");

    // set adapter
    ArrayAdapter<String> adapter;
    adapter =
        new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, dashlist);

    // connect adapter
    ListView list = (ListView) findViewById(R.id.dash_list);
    list.setAdapter(adapter);

    // ListView attribute
    list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    list.setDivider(new ColorDrawable(Color.WHITE));
    list.setDividerHeight(2);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_dashboard, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.

    //noinspection SimplifiableIfStatement
    switch (item.getItemId()) {
      case R.id.action_start_workflowEditor:
        startActivity(new Intent(DashboardActivity.this, FlowListActivity.class));
        break;
      case R.id.action_start_deviceEditor:
        startActivity(new Intent(DashboardActivity.this, DeviceListActivity.class));
        break;
    }

    return super.onOptionsItemSelected(item);
  }
}
