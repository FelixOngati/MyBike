package xyz.fegati.mybike;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import xyz.fegati.mybike.util.JSONParser;
import xyz.fegati.mybike.util.Util;

public class UserRequestDetailsActivity extends Activity
  implements View.OnClickListener
{
  public static final String dataSendUrl = "http://futureline.lk/taxi/app/update-request.php";
  public static HashMap<String, String> request;
  Context con;
  TextView dropTv;
  JSONParser jparser = new JSONParser();
  TextView nameTv;
  TextView numberTv;
  TextView pickupTv;
  Button trackBtn;

  private void init()
  {
    this.nameTv = ((TextView)findViewById(R.id.userRequestDetailsName));
    this.numberTv = ((TextView)findViewById(R.id.userRequestDetailsNumber));
    this.pickupTv = ((TextView)findViewById(R.id.userRequestDetailsPickupLocation));
    this.dropTv = ((TextView)findViewById(R.id.userRequestDetailsDropLocation));
    this.trackBtn = ((Button)findViewById(R.id.driverTrackBtn));
    this.trackBtn.setOnClickListener(this);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131165273:
    }
    if (((String)request.get("accept")).equals("1"))
    {
      Intent localIntent = new Intent(this, DriverTrackActivity.class);
      localIntent.putExtra("driver_email", (String)request.get("driver_email"));
      startActivity(localIntent);
      return;
    }
    Util.showToast(this.con, "Only accepted requests are alowed to track");
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.user_request_details_layout);
    this.con = this;
    init();
    this.nameTv.setText("Driver name : " + ((String)request.get("driver_name")).trim());
    this.numberTv.setText("Number : " + ((String)request.get("phone")).trim());
    this.pickupTv.setText("Pickup Location : " + ((String)request.get("location")).trim());
    this.dropTv.setText("Drop Location : " + ((String)request.get("droplocation")).trim());
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
      localArrayList.add(new BasicNameValuePair("id", (String)UserRequestDetailsActivity.request.get("id")));
      localArrayList.add(new BasicNameValuePair("accept", paramArrayOfString[0]));
      try
      {
        JSONObject localJSONObject = UserRequestDetailsActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/update-request.php", "POST", localArrayList);
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
        if (Util.isConnectingToInternet(UserRequestDetailsActivity.this.con))
          Toast.makeText(UserRequestDetailsActivity.this.con, "Server is down, Please try again later", Toast.LENGTH_SHORT).show();
      do
      {

        Util.showNoInternetDialog(UserRequestDetailsActivity.this.con);

        Toast.makeText(UserRequestDetailsActivity.this.con, this.s, Toast.LENGTH_LONG).show();
      }
      while ((this.success == 0) || (this.success != 1));
      UserRequestDetailsActivity.this.setResult(-1);
      UserRequestDetailsActivity.this.finish();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(UserRequestDetailsActivity.this.con);
      this.pDialog.setMessage("Sending data. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.UserRequestDetailsActivity
 * JD-Core Version:    0.6.2
 */