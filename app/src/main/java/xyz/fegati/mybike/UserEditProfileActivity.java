package xyz.fegati.mybike;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import xyz.fegati.mybike.util.JSONParser;
import xyz.fegati.mybike.util.Util;

public class UserEditProfileActivity extends Activity
  implements View.OnClickListener
{
  public static final String updateURL = "http://futureline.lk/taxi/app/user-edit-profile.php";
  Button cancelBtn;
  Context con;
  JSONParser jparser = new JSONParser();
  EditText nameEt;
  EditText numberEt;
  EditText passwordEt;
  Button saveBtn;

//  private void init()
//  {
//    this.nameEt = ((EditText)findViewById(2131165264));
//    this.numberEt = ((EditText)findViewById(2131165265));
//    this.passwordEt = ((EditText)findViewById(2131165266));
//    this.cancelBtn = ((Button)findViewById(2131165267));
//    this.saveBtn = ((Button)findViewById(2131165268));
//    this.cancelBtn.setOnClickListener(this);
//    this.saveBtn.setOnClickListener(this);
//  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131165267:
      finish();
      return;
    case 2131165268:
    }
    if (Util.isConnectingToInternet(this))
    {
      if (!TextUtils.isEmpty(this.nameEt.getText().toString()))
      {
        if (!TextUtils.isEmpty(this.numberEt.getText().toString()))
        {
          if (!TextUtils.isEmpty(this.passwordEt.getText().toString()))
          {
            new UpdateInfo().execute(new String[0]);
            return;
          }
          Util.showToast(this, "Please enter your password");
          return;
        }
        Util.showToast(this, "Please enter your phone number");
        return;
      }
      Util.showToast(this, "Please enter your name");
      return;
    }
    Util.showNoInternetDialog(this);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
//    setContentView(2130903049);
//    this.con = this;
//    init();
//    if (UserInfo.getName() != null)
//      this.nameEt.setText(UserInfo.getName());
//    if (UserInfo.getPhonenumber() != null)
//      this.numberEt.setText(UserInfo.getPhonenumber());
//    if (UserInfo.getPassword() != null)
//      this.passwordEt.setText(UserInfo.getPassword());
//    getActionBar().setHomeButtonEnabled(true);
//    getActionBar().setDisplayHomeAsUpEnabled(true);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return super.onOptionsItemSelected(paramMenuItem);
    case 16908332:
    }
    finish();
    return true;
  }

  class UpdateInfo extends AsyncTask<String, String, String>
  {
    int error = 0;
    ProgressDialog pDialog;
    String s = "";
    int success = -1;

    UpdateInfo()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
//      String str1 = UserInfo.getEmail();
//      String str2 = UserEditProfileActivity.this.passwordEt.getText().toString();
//      String str3 = UserEditProfileActivity.this.nameEt.getText().toString();
//      String str4 = UserEditProfileActivity.this.numberEt.getText().toString();
//      ArrayList localArrayList = new ArrayList();
//      localArrayList.add(new BasicNameValuePair("email", str1));
//      localArrayList.add(new BasicNameValuePair("name", str3));
//      localArrayList.add(new BasicNameValuePair("password", str2));
//      localArrayList.add(new BasicNameValuePair("number", str4));
//      try
//      {
//        JSONObject localJSONObject = UserEditProfileActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/user-edit-profile.php", "POST", localArrayList);
//        this.success = localJSONObject.getInt("success");
//        this.s = localJSONObject.getString("message");
//        if (this.success == 1)
//        {
//          UserInfo.setName(str3);
//          UserInfo.setPhonenumber(str4);
//          UserInfo.setPassword(str2);
//        }
//        return null;
//      }
//      catch (JSONException localJSONException)
//      {
//        while (true)
//          this.error = 1;
//      }
//      catch (Exception localException)
//      {
//        while (true)
//          this.error = 1;
//      }
      return null;
    }

    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      this.pDialog.dismiss();
      if (this.error == 1)
      {
        if (Util.isConnectingToInternet(UserEditProfileActivity.this.con))
        {
          Toast.makeText(UserEditProfileActivity.this.con, "Server is down, Please try again later", Toast.LENGTH_SHORT).show();
          return;
        }
        Util.showNoInternetDialog(UserEditProfileActivity.this.con);
        return;
      }
      Toast.makeText(UserEditProfileActivity.this.con, this.s, Toast.LENGTH_SHORT).show();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(UserEditProfileActivity.this.con);
      this.pDialog.setMessage("Data updating, Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.UserEditProfileActivity
 * JD-Core Version:    0.6.2
 */