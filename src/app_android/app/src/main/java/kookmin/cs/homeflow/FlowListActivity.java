/**
 * @namespace kookmin.cs.homeflow
 * @brief java package of HomeFlow app
 * @details HomeFlow의 java file 집합
 */
package kookmin.cs.homeflow;

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
 * @brief an Activity is show workflow list existent(able to edit)
 * @details 현재 등록된 workflow의 list를 보여준다. workflow를 새로 만들수 있는 Button이 있고 list를 클릭하면 workflow를 수정 또는
 * 삭제할 수 있다.
 * @todo develop UI, List Item Click, Button Click etc...
 */
public class FlowListActivity extends ActionBarActivity {

  /**
   * @brief Activity init
   * @details Activity를 init한다. 데이터를 listview로 보여준다. listview의 속성을 변경한다.
   * @todo read list of workflow xml file and show
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_workflow_list);

    getSupportActionBar().setTitle("FlowList");
    // set data
    ArrayList<String> dashlist = new ArrayList<String>();

    dashlist.add("세탁기 workflow");
    dashlist.add("집 나갈때 workflow");
    dashlist.add("잠 자기 전 workflow");

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
}
