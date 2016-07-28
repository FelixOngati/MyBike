package xyz.fegati.mybike;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import xyz.fegati.mybike.util.GPSTracker;
import xyz.fegati.mybike.util.JSONParser;
import xyz.fegati.mybike.util.UserInfo;
import xyz.fegati.mybike.util.Util;

public class DriverActivity extends Activity
{
  public static final String getRidesUrl = "http://futureline.lk/taxi/app/requested-rides-list.php";
  public static final String modeSendUrl = "http://futureline.lk/taxi/app/receievemode.php";
  Spinner categoryFilter;
  Context con;
  final String dataSendUrl = "http://futureline.lk/taxi/app/receievelocation.php";
  String[] dropdownItems = { "All", "New Requests", "Accepted Rides", "Completed Rides", "Cancelled Rides" };
  GPSTracker gps;
  Handler handler;
  JSONParser jparser = new JSONParser();
  ToggleButton modeBtn;
  int notAccReq = 0;
  ListView rideList;
  ArrayList<HashMap<String, String>> rides;
  Runnable run;
  SharedPreferences sh;

  private void showNotification()
  {
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(this.con);
    Intent localIntent = new Intent(this.con, DriverActivity.class);
    localBuilder.setContentTitle("New taxi request!");
    localBuilder.setContentText("Click to see the customer information ");
    localBuilder.setSmallIcon(17301543);
    localBuilder.setTicker("New notification from Cruzer");
    localBuilder.setSound(RingtoneManager.getDefaultUri(2));
    PendingIntent localPendingIntent = PendingIntent.getActivity(this.con, 100, localIntent, 134217728);
    localBuilder.setAutoCancel(true);
    localBuilder.setContentIntent(localPendingIntent);
    ((NotificationManager)this.con.getSystemService(Context.NOTIFICATION_SERVICE)).notify(110, localBuilder.build());
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 101) && (paramInt2 == -1))
      new GetRequestedRides(DriverActivity.this).execute(new String[0]);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.gps = new GPSTracker(this);
    this.con = this;
    setContentView(2130903040);
    this.rideList = ((ListView)findViewById(2131165208));
    this.modeBtn = ((ToggleButton)findViewById(2131165205));
    this.categoryFilter = ((Spinner)findViewById(2131165207));
    this.categoryFilter.setAdapter(new ArrayAdapter(this, 17367050, this.dropdownItems));
    this.modeBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          new DriverActivity.SendModeData(DriverActivity.this).execute(new String[] { "1" });
          return;
        }
        new DriverActivity.SendModeData(DriverActivity.this).execute(new String[] { "0" });
      }
    });
    this.categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
    {
      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        new DriverActivity.GetRequestedRides(DriverActivity.this).execute(new String[0]);
      }

      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
      {
      }
    });
    this.sh = getSharedPreferences("CRUZER_PREF", 0);
    this.notAccReq = this.sh.getInt("notaccreq", 0);
    new GetRequestedRides(DriverActivity.this).execute(new String[0]);
    new SendLocation().execute(new String[0]);
    this.rideList.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        RequestedUserActivity.request = (HashMap)DriverActivity.this.rides.get(paramAnonymousInt);
        Intent localIntent = new Intent(DriverActivity.this, RequestedUserActivity.class);
        DriverActivity.this.startActivityForResult(localIntent, 101);
      }
    });
    if (!Util.isGPSOn(this))
      GPSTracker.showSettingsAlert(this);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131361792, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.handler != null)
    {
      this.handler.removeCallbacks(this.run);
      this.run = null;
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
    case 2131165276:
    case 2131165277:
    }
    while (true)
    {
      return super.onOptionsItemSelected(paramMenuItem);
      new GetRequestedRides(DriverActivity.this).execute(new String[0]);
      continue;
      SharedPreferences.Editor localEditor = this.sh.edit();
      localEditor.putString("loginemail", null);
      localEditor.putString("loginpass", null);
      localEditor.putBoolean("type", false);
      localEditor.commit();
      startActivity(new Intent(this, LoginActivity.class));
      finish();
    }
  }

  public void scheduleThread()
  {
    this.handler = new Handler();
    this.run = new Runnable()
    {
      public void run()
      {
        if (Util.isConnectingToInternet(DriverActivity.this))
        {
          DriverActivity.this.gps = new GPSTracker(DriverActivity.this.con);
          new DriverActivity.SendLocation(DriverActivity.this).execute(new String[0]);
          new DriverActivity.GetRequestedRides(DriverActivity.this).execute(new String[0]);
          Toast.makeText(DriverActivity.this.con, DriverActivity.this.gps.getLatitude() + " " + DriverActivity.this.gps.getLongitude(), 0).show();
          return;
        }
        Toast.makeText(DriverActivity.this.con, "Internet is not active", 0).show();
      }
    };
    this.handler.postDelayed(this.run, 30000L);
  }

  class GetRequestedRides extends AsyncTask<String, String, String>
  {
    int error = 0;
    ProgressDialog pDialog;
    String s = "";
    int success = -1;

    GetRequestedRides(DriverActivity driverActivity)
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("user_email", UserInfo.getEmail()));
      while (true)
      {
        int i;
        try
        {
          JSONObject localJSONObject1 = DriverActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/requested-rides-list.php", "POST", localArrayList);
          this.success = localJSONObject1.getInt("success");
          this.s = localJSONObject1.getString("message");
          if (this.success == 1)
          {
            DriverActivity.this.rides = new ArrayList();
            JSONArray localJSONArray = localJSONObject1.getJSONArray("ridelist");
            i = 0;
            if (i < localJSONArray.length())
            {
              localJSONObject2 = localJSONArray.getJSONObject(i);
              localHashMap = new HashMap();
              localHashMap.put("id", localJSONObject2.getString("id"));
              localHashMap.put("driver_id", localJSONObject2.getString("driver_id"));
              localHashMap.put("sender_id", localJSONObject2.getString("sender_id"));
              localHashMap.put("name", localJSONObject2.getString("name"));
              localHashMap.put("phone", localJSONObject2.getString("phone"));
              localHashMap.put("droplocation", localJSONObject2.getString("droplocation"));
              localHashMap.put("location", localJSONObject2.getString("location"));
              localHashMap.put("latitude", localJSONObject2.getString("latitude"));
              localHashMap.put("longitude", localJSONObject2.getString("longitude"));
              localHashMap.put("timedate", localJSONObject2.getString("timedate"));
              localHashMap.put("accept", localJSONObject2.getString("accept"));
              if (DriverActivity.this.categoryFilter.getSelectedItemPosition() == 0)
              {
                DriverActivity.this.rides.add(localHashMap);
              }
              else if (DriverActivity.this.categoryFilter.getSelectedItemPosition() == 1)
              {
                if (!localJSONObject2.getString("accept").equals("0"))
                  break label517;
                DriverActivity.this.rides.add(localHashMap);
              }
            }
          }
        }
        catch (Exception localException)
        {
          JSONObject localJSONObject2;
          HashMap localHashMap;
          this.error = 1;
          break label515;
          if (DriverActivity.this.categoryFilter.getSelectedItemPosition() == 2)
          {
            if (localJSONObject2.getString("accept").equals("1"))
              DriverActivity.this.rides.add(localHashMap);
          }
          else if (DriverActivity.this.categoryFilter.getSelectedItemPosition() == 3)
          {
            if (localJSONObject2.getString("accept").equals("2"))
              DriverActivity.this.rides.add(localHashMap);
          }
          else if ((DriverActivity.this.categoryFilter.getSelectedItemPosition() == 4) && (localJSONObject2.getString("accept").equals("3")))
            DriverActivity.this.rides.add(localHashMap);
        }
        label515: return null;
        label517: i++;
      }
    }

    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      this.pDialog.dismiss();
      if (this.error == 1)
        if (Util.isConnectingToInternet(DriverActivity.this.con))
          Toast.makeText(DriverActivity.this.con, "Server is down, Please try again later", 0).show();
      do
      {
        return;
        Util.showNoInternetDialog(DriverActivity.this.con);
        return;
        DriverActivity.this.notAccReq = DriverActivity.this.sh.getInt("notaccreq", 0);
        if (this.success != 1)
          break;
        DriverActivity.ListAdapter localListAdapter = new DriverActivity.ListAdapter(DriverActivity.this);
        DriverActivity.this.rideList.setAdapter(localListAdapter);
      }
      while ((DriverActivity.this.categoryFilter.getSelectedItemPosition() != 0) && (DriverActivity.this.categoryFilter.getSelectedItemPosition() != 2));
      int i = 0;
      for (int j = 0; ; j++)
      {
        if (j >= DriverActivity.this.rides.size())
        {
          if (i > DriverActivity.this.notAccReq)
            DriverActivity.this.showNotification();
          SharedPreferences.Editor localEditor = DriverActivity.this.sh.edit();
          localEditor.putInt("notaccreq", i);
          localEditor.commit();
          return;
        }
        if (((String)((HashMap)DriverActivity.this.rides.get(j)).get("accept")).equals("0"))
          i++;
      }
      Toast.makeText(DriverActivity.this, this.s, 0).show();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(DriverActivity.this.con);
      this.pDialog.setMessage("Getting data. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }

  class ListAdapter extends BaseAdapter
  {
    ListAdapter()
    {
    }

    public int getCount()
    {
      return DriverActivity.this.rides.size();
    }

    public Object getItem(int paramInt)
    {
      return DriverActivity.this.rides.get(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = DriverActivity.this.getLayoutInflater().inflate(2130903047, paramViewGroup, false);
      TextView localTextView1 = (TextView)localView.findViewById(2131165252);
      TextView localTextView2 = (TextView)localView.findViewById(2131165253);
      TextView localTextView3 = (TextView)localView.findViewById(2131165254);
      TextView localTextView4 = (TextView)localView.findViewById(2131165255);
      TextView localTextView5 = (TextView)localView.findViewById(2131165256);
      localTextView1.setText("Passenger Name: " + ((String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("name")).trim());
      localTextView2.setText("Pickup Location : " + ((String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("location")).trim());
      localTextView4.setText("Time : " + (String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("timedate"));
      localTextView3.setText("Drop Location: " + ((String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("droplocation")).trim());
      if (((String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("accept")).equals("0"))
      {
        localTextView5.setText("Not accepted yet");
        localTextView5.setTextColor(Color.parseColor("#AD1400"));
      }
      do
      {
        return localView;
        if (((String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("accept")).equals("1"))
        {
          localTextView5.setText("Accepted");
          localTextView5.setTextColor(Color.parseColor("#5AB83B"));
          return localView;
        }
        if (((String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("accept")).equals("2"))
        {
          localTextView5.setText("Completed");
          return localView;
        }
      }
      while (!((String)((HashMap)DriverActivity.this.rides.get(paramInt)).get("accept")).equals("3"));
      localTextView5.setText("Cancelled");
      return localView;
    }
  }

  class SendLocation extends AsyncTask<String, String, String>
  {
    boolean driver = false;
    int error = 0;
    ProgressDialog pDialog;
    String s = "";
    int success = -1;

    SendLocation()
    {
    }

    public SendLocation(DriverActivity driverActivity) {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("email", UserInfo.getEmail()));
      localArrayList.add(new BasicNameValuePair("latitude", DriverActivity.this.gps.getLatitude()));
      localArrayList.add(new BasicNameValuePair("longitude", DriverActivity.this.gps.getLongitude()));
      try
      {
        JSONObject localJSONObject = DriverActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/receievelocation.php", "POST", localArrayList);
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
        if (Util.isConnectingToInternet(DriverActivity.this.con))
          Toast.makeText(DriverActivity.this.con, "Server is down, Please try again later", 0).show();
      do
      {
        return;
        Util.showNoInternetDialog(DriverActivity.this.con);
        return;
        Toast.makeText(DriverActivity.this.con, this.s, 1).show();
      }
      while ((this.success == 0) || (this.success != 1));
      DriverActivity.this.scheduleThread();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(DriverActivity.this.con);
      this.pDialog.setMessage("Updating locations. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }

  class SendModeData extends AsyncTask<String, String, String>
  {
    int error = 0;
    ProgressDialog pDialog;
    String s = "";
    int success = -1;

    SendModeData()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("email", UserInfo.getEmail()));
      localArrayList.add(new BasicNameValuePair("mode", paramArrayOfString[0]));
      try
      {
        JSONObject localJSONObject = DriverActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/receievemode.php", "POST", localArrayList);
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
      {
        if (Util.isConnectingToInternet(DriverActivity.this.con))
        {
          Toast.makeText(DriverActivity.this.con, "Server is down, Please try again later", 0).show();
          return;
        }
        Util.showNoInternetDialog(DriverActivity.this.con);
        return;
      }
      Toast.makeText(DriverActivity.this.con, this.s, 1).show();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(DriverActivity.this.con);
      this.pDialog.setMessage("Sending data. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.DriverActivity
 * JD-Core Version:    0.6.2
 */