package xyz.fegati.mybike;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.nas.cruzer.util.JSONParser;
import com.nas.cruzer.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class RequestedUserActivity extends Activity
  implements View.OnClickListener
{
  public static final String dataSendUrl = "http://futureline.lk/taxi/app/update-request.php";
  public static HashMap<String, String> request;
  Button accBtn;
  Button cancelBtn;
  Button completeBtn;
  Context con;
  TextView dropLocTv;
  JSONParser jparser = new JSONParser();
  TextView nameTv;
  TextView numberTv;
  TextView pickupTv;

  private void init()
  {
    this.nameTv = ((TextView)findViewById(2131165257));
    this.numberTv = ((TextView)findViewById(2131165258));
    this.pickupTv = ((TextView)findViewById(2131165259));
    this.dropLocTv = ((TextView)findViewById(2131165260));
    this.cancelBtn = ((Button)findViewById(2131165261));
    this.accBtn = ((Button)findViewById(2131165262));
    this.completeBtn = ((Button)findViewById(2131165263));
    this.accBtn.setOnClickListener(this);
    this.cancelBtn.setOnClickListener(this);
    this.completeBtn.setOnClickListener(this);
    if (((String)request.get("accept")).equals("1"))
      this.accBtn.setVisibility(8);
    if (((String)request.get("accept")).equals("2"))
    {
      this.accBtn.setVisibility(8);
      this.cancelBtn.setVisibility(8);
      this.completeBtn.setVisibility(8);
    }
    if (((String)request.get("accept")).equals("3"))
    {
      this.accBtn.setVisibility(8);
      this.cancelBtn.setVisibility(8);
      this.completeBtn.setVisibility(8);
    }
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131165261:
      new SendData().execute(new String[] { "3" });
      return;
    case 2131165262:
      new SendData().execute(new String[] { "1" });
      return;
    case 2131165263:
    }
    new SendData().execute(new String[] { "2" });
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903048);
    this.con = this;
    init();
    this.nameTv.setText("Name : " + (String)request.get("name"));
    this.numberTv.setText("Phone no. : " + (String)request.get("phone"));
    this.pickupTv.setText("Pickup Location : " + ((String)request.get("location")).trim());
    this.dropLocTv.setText("Drop Location : " + ((String)request.get("droplocation")).trim());
    getActionBar().setHomeButtonEnabled(true);
    getActionBar().setDisplayHomeAsUpEnabled(true);
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

  class SendData extends AsyncTask<String, String, String>
  {
    boolean driver = false;
    int error = 0;
    ProgressDialog pDialog;
    String s = "";
    int success = -1;

    SendData()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("id", (String)RequestedUserActivity.request.get("id")));
      localArrayList.add(new BasicNameValuePair("accept", paramArrayOfString[0]));
      try
      {
        JSONObject localJSONObject = RequestedUserActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/update-request.php", "POST", localArrayList);
        this.success = localJSONObject.getInt("success");
        this.s = localJSONObject.getString("message");
        return null;
      }
      catch (Exception localException)
      {
        while (true)
          this.error = 1;
      }
    }

    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      this.pDialog.dismiss();
      if (this.error == 1)
        if (Util.isConnectingToInternet(RequestedUserActivity.this.con))
          Toast.makeText(RequestedUserActivity.this.con, "Server is down, Please try again later", 0).show();
      do
      {
        return;
        Util.showNoInternetDialog(RequestedUserActivity.this.con);
        return;
        Toast.makeText(RequestedUserActivity.this.con, this.s, 1).show();
      }
      while ((this.success == 0) || (this.success != 1));
      RequestedUserActivity.this.setResult(-1);
      RequestedUserActivity.this.finish();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(RequestedUserActivity.this.con);
      this.pDialog.setMessage("Sending data. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.RequestedUserActivity
 * JD-Core Version:    0.6.2
 */