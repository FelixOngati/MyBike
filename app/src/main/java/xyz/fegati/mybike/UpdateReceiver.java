package xyz.fegati.mybike;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;
import xyz.fegati.mybike.util.JSONParser;
import xyz.fegati.mybike.util.UserInfo;
import xyz.fegati.mybike.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class UpdateReceiver extends BroadcastReceiver
{
  public static final String getRidesUrl = "http://futureline.lk/taxi/app/user-rides-list.php";
  Context con;
  JSONParser jparser = new JSONParser();
  ArrayList<HashMap<String, String>> reqRides;
  int rideAcceptNo = 0;
  SharedPreferences sh;

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.con = paramContext;
    this.sh = this.con.getSharedPreferences("CRUZER_PREF", 0);
    this.rideAcceptNo = this.sh.getInt("rideaccno", 0);
    new GetRequestedRides().execute(new String[0]);
  }

  class GetRequestedRides extends AsyncTask<String, String, String>
  {
    int error = 0;
    ProgressDialog pDialog;
    String s = "";
    int success = -1;

    GetRequestedRides()
    {
    }

    private void showNotification()
    {
      NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(UpdateReceiver.this.con);
      Intent localIntent = new Intent(UpdateReceiver.this.con, UserRequestActivity.class);
      localBuilder.setContentTitle("Driver accepted your request!");
      localBuilder.setContentText("Click to see the taxi information ");
      localBuilder.setSmallIcon(17301543);
      localBuilder.setTicker("New Notification from Cruzer");
      localBuilder.setSound(RingtoneManager.getDefaultUri(2));
      PendingIntent localPendingIntent = PendingIntent.getActivity(UpdateReceiver.this.con, 100, localIntent, 134217728);
      localBuilder.setAutoCancel(true);
      localBuilder.setContentIntent(localPendingIntent);
      ((NotificationManager)UpdateReceiver.this.con.getSystemService(Context.NOTIFICATION_SERVICE)).notify(110, localBuilder.build());
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("user_id", UserInfo.getId()));
      try
      {
        JSONObject localJSONObject1 = UpdateReceiver.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/user-rides-list.php", "POST", localArrayList);
        this.success = localJSONObject1.getInt("success");
        this.s = localJSONObject1.getString("message");
        if (this.success == 1)
        {
          UpdateReceiver.this.reqRides = new ArrayList();
          JSONArray localJSONArray = localJSONObject1.getJSONArray("ridelist");
          for (int i = 0; i < localJSONArray.length(); i++)
          {
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
            HashMap localHashMap = new HashMap();
            localHashMap.put("id", localJSONObject2.getString("id"));
            localHashMap.put("driver_name", localJSONObject2.getString("driver_name"));
            localHashMap.put("sender_id", localJSONObject2.getString("sender_id"));
            localHashMap.put("name", localJSONObject2.getString("name"));
            localHashMap.put("phone", localJSONObject2.getString("phone"));
            localHashMap.put("droplocation", localJSONObject2.getString("droplocation"));
            localHashMap.put("location", localJSONObject2.getString("location"));
            localHashMap.put("latitude", localJSONObject2.getString("latitude"));
            localHashMap.put("longitude", localJSONObject2.getString("longitude"));
            localHashMap.put("accept", localJSONObject2.getString("accept"));
            UpdateReceiver.this.reqRides.add(localHashMap);
          }
        }
      }
      catch (Exception localException)
      {
        this.error = 1;
      }
      return null;
    }

    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      UpdateReceiver.this.rideAcceptNo = UpdateReceiver.this.sh.getInt("rideaccno", 0);
      if (this.error == 1)
      {
        if (Util.isConnectingToInternet(UpdateReceiver.this.con))
        {
          Toast.makeText(UpdateReceiver.this.con, "Server is down, Please try again later", Toast.LENGTH_SHORT).show();
          return;
        }
        Util.showNoInternetDialog(UpdateReceiver.this.con);
        return;
      }
      if (this.success == 1)
      {
        int i = 0;
        for (int j = 0; ; j++)
        {
          if (j >= UpdateReceiver.this.reqRides.size())
          {
            if (i > UpdateReceiver.this.rideAcceptNo)
              showNotification();
            SharedPreferences.Editor localEditor = UpdateReceiver.this.sh.edit();
            localEditor.putInt("rideaccno", i);
            localEditor.commit();
            return;
          }
          if (((String)((HashMap)UpdateReceiver.this.reqRides.get(j)).get("accept")).equals("1"))
            i++;
        }
      }
      Toast.makeText(UpdateReceiver.this.con, this.s, Toast.LENGTH_SHORT).show();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(UpdateReceiver.this.con);
      this.pDialog.setMessage("Getting data. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
    }
  }
}