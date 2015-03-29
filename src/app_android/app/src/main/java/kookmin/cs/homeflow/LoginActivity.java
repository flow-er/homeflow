/**
 * @namespace kookmin.cs.homeflow
 * @brief java package of HomeFlow app
 * @details HomeFlow의 java file 집합
 */
package kookmin.cs.homeflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author Jongho Lim, sloth@kookmin.ac.kr
 * @author Jinsung choi, bugslife102401@nate.com
 * @version 0.0.1
 * @brief an Activity for Login
 * @details ActionBar를 가지고있고 Login 기능을 지원하는 Activity Class이다. UI로는 id와 password를 적을 수 있는 EditText 가 두 개 있고 sign in(login) 기능을 지원하는 Button이 한 개 있다.
 * @todo function develop user check with server communication
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

  private EditText id;
  private EditText password;
  private Button btnSignin;

  /**
   * @brief Activity init
   * @details EditText와 Button을 init 시키고 Button에 click event를 등록한다.
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    id = (EditText) findViewById(R.id.editId);
    password = (EditText) findViewById(R.id.editPwd);
    btnSignin = (Button) findViewById(R.id.btnSignin);

    btnSignin.setOnClickListener(this);
  }

  /**
   * @param v clicked view
   * @brief process click event
   * @details Sign in Button이 클릭되면 실행되는 method이다. 사용자가 확인 되면 DashBoard Activity로 넘어간다.
   * @todo implement function user check with server communication
   */
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnSignin:
        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        finish();
        break;
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
}
