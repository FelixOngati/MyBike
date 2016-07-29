package xyz.fegati.mybike;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserRequestActivity extends Activity
{
//  public static final String getRidesUrl = "http://futureline.lk/taxi/app/user-rides-list.php";
//  Spinner categoryFilter;
//  Context con;
//  String[] dropdownItems = { "All", "Pending Requests", "Accepted Requests", "Completed Rides", "Cancelled" };
//  JSONParser jparser = new JSONParser();
//  ListView requestList;
//  ArrayList<HashMap<String, String>> rides;
//
//  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
//  {
//    super.onActivityResult(paramInt1, paramInt2, paramIntent);
//    if ((paramInt1 == 101) && (paramInt2 == -1))
//      new GetRequestedRides().execute(new String[0]);
//  }
//
//  protected void onCreate(Bundle paramBundle)
//  {
//    super.onCreate(paramBundle);
//    setContentView(2130903051);
//    this.con = this;
//    this.categoryFilter = ((Spinner)findViewById(2131165274));
//    this.categoryFilter.setAdapter(new ArrayAdapter(this, 17367050, this.dropdownItems));
//    this.requestList = ((ListView)findViewById(2131165275));
//    new GetRequestedRides().execute(new String[0]);
//    this.requestList.setOnItemClickListener(new AdapterView.OnItemClickListener()
//    {
//      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
//      {
//        UserRequestDetailsActivity.request = (HashMap)UserRequestActivity.this.rides.get(paramAnonymousInt);
//        UserRequestActivity.this.startActivityForResult(new Intent(UserRequestActivity.this.con, UserRequestDetailsActivity.class), 101);
//      }
//    });
//    this.categoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//    {
//      public void onItemSelected(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
//      {
//        new UserRequestActivity.GetRequestedRides(UserRequestActivity.this).execute(new String[0]);
//      }
//
//      public void onNothingSelected(AdapterView<?> paramAnonymousAdapterView)
//      {
//      }
//    });
//    getActionBar().setHomeButtonEnabled(true);
//    getActionBar().setDisplayHomeAsUpEnabled(true);
//  }
//
//  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
//  {
//    switch (paramMenuItem.getItemId())
//    {
//    default:
//      return super.onOptionsItemSelected(paramMenuItem);
//    case 16908332:
//    }
//    finish();
//    return true;
//  }
//
//  class GetRequestedRides extends AsyncTask<String, String, String>
//  {
//    int error = 0;
//    ProgressDialog pDialog;
//    String s = "";
//    int success = -1;
//
//    GetRequestedRides()
//    {
//    }
//
//    protected String doInBackground(String[] paramArrayOfString)
//    {
//      ArrayList localArrayList = new ArrayList();
//      localArrayList.add(new BasicNameValuePair("user_id", UserInfo.getId()));
//      while (true)
//      {
//        int i;
//        try
//        {
//          JSONObject localJSONObject1 = UserRequestActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/user-rides-list.php", "POST", localArrayList);
//          this.success = localJSONObject1.getInt("success");
//          this.s = localJSONObject1.getString("message");
//          if (this.success == 1)
//          {
//            UserRequestActivity.this.rides = new ArrayList();
//            JSONArray localJSONArray = localJSONObject1.getJSONArray("ridelist");
//            i = 0;
//            if (i < localJSONArray.length())
//            {
//              localJSONObject2 = localJSONArray.getJSONObject(i);
//              localHashMap = new HashMap();
//              localHashMap.put("id", localJSONObject2.getString("id"));
//              localHashMap.put("driver_name", localJSONObject2.getString("driver_name"));
//              localHashMap.put("driver_email", localJSONObject2.getString("driver_email"));
//              localHashMap.put("sender_id", localJSONObject2.getString("sender_id"));
//              localHashMap.put("name", localJSONObject2.getString("name"));
//              localHashMap.put("phone", localJSONObject2.getString("phone"));
//              localHashMap.put("droplocation", localJSONObject2.getString("droplocation"));
//              localHashMap.put("location", localJSONObject2.getString("location"));
//              localHashMap.put("latitude", localJSONObject2.getString("latitude"));
//              localHashMap.put("longitude", localJSONObject2.getString("longitude"));
//              localHashMap.put("timedate", localJSONObject2.getString("timedate"));
//              localHashMap.put("accept", localJSONObject2.getString("accept"));
//              if (UserRequestActivity.this.categoryFilter.getSelectedItemPosition() == 0)
//              {
//                UserRequestActivity.this.rides.add(localHashMap);
//              }
//              else if (UserRequestActivity.this.categoryFilter.getSelectedItemPosition() == 1)
//              {
//                if (!localJSONObject2.getString("accept").equals("0"))
//                  break label532;
//                UserRequestActivity.this.rides.add(localHashMap);
//              }
//            }
//          }
//        }
//        catch (Exception localException)
//        {
//          JSONObject localJSONObject2;
//          HashMap localHashMap;
//          this.error = 1;
//          break label530;
//          if (UserRequestActivity.this.categoryFilter.getSelectedItemPosition() == 2)
//          {
//            if (localJSONObject2.getString("accept").equals("1"))
//              UserRequestActivity.this.rides.add(localHashMap);
//          }
//          else if (UserRequestActivity.this.categoryFilter.getSelectedItemPosition() == 3)
//          {
//            if (localJSONObject2.getString("accept").equals("2"))
//              UserRequestActivity.this.rides.add(localHashMap);
//          }
//          else if ((UserRequestActivity.this.categoryFilter.getSelectedItemPosition() == 4) && (localJSONObject2.getString("accept").equals("3")))
//            UserRequestActivity.this.rides.add(localHashMap);
//        }
//        label530: return null;
//        label532: i++;
//      }
//    }
//
//    protected void onPostExecute(String paramString)
//    {
//      super.onPostExecute(paramString);
//      this.pDialog.dismiss();
//      if (this.error == 1)
//      {
//        if (Util.isConnectingToInternet(UserRequestActivity.this.con))
//        {
//          Toast.makeText(UserRequestActivity.this.con, "Server is down, Please try again later", 0).show();
//          return;
//        }
//        Util.showNoInternetDialog(UserRequestActivity.this.con);
//        return;
//      }
//      if (this.success == 1)
//      {
//        UserRequestActivity.ListAdapter localListAdapter = new UserRequestActivity.ListAdapter(UserRequestActivity.this);
//        UserRequestActivity.this.requestList.setAdapter(localListAdapter);
//        return;
//      }
//      Toast.makeText(UserRequestActivity.this.con, this.s, 0).show();
//    }
//
//    protected void onPreExecute()
//    {
//      super.onPreExecute();
//      this.pDialog = new ProgressDialog(UserRequestActivity.this.con);
//      this.pDialog.setMessage("Getting data. Please wait...");
//      this.pDialog.setIndeterminate(false);
//      this.pDialog.setCancelable(true);
//      this.pDialog.show();
//    }
//  }
//
//  class ListAdapter extends BaseAdapter
//  {
//    ListAdapter()
//    {
//    }
//
//    public int getCount()
//    {
//      return UserRequestActivity.this.rides.size();
//    }
//
//    public Object getItem(int paramInt)
//    {
//      return UserRequestActivity.this.rides.get(paramInt);
//    }
//
//    public long getItemId(int paramInt)
//    {
//      return paramInt;
//    }
//
//    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
//    {
//      View localView = UserRequestActivity.this.getLayoutInflater().inflate(2130903047, paramViewGroup, false);
//      TextView localTextView1 = (TextView)localView.findViewById(2131165252);
//      TextView localTextView2 = (TextView)localView.findViewById(2131165253);
//      TextView localTextView3 = (TextView)localView.findViewById(2131165254);
//      TextView localTextView4 = (TextView)localView.findViewById(2131165255);
//      TextView localTextView5 = (TextView)localView.findViewById(2131165256);
//      localTextView1.setText("Driver Name: " + ((String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("driver_name")).trim());
//      localTextView2.setText("Pickup Location : " + ((String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("location")).trim());
//      localTextView4.setText("Time : " + (String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("timedate"));
//      localTextView3.setText("Drop Location: " + ((String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("droplocation")).trim());
//      if (((String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("accept")).equals("0"))
//      {
//        localTextView5.setText("Not accepted yet");
//        localTextView5.setTextColor(Color.parseColor("#AD1400"));
//      }
//      do
//      {
//        return localView;
//        if (((String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("accept")).equals("1"))
//        {
//          localTextView5.setText("Accepted");
//          localTextView5.setTextColor(Color.parseColor("#5AB83B"));
//          return localView;
//        }
//        if (((String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("accept")).equals("2"))
//        {
//          localTextView5.setText("Completed");
//          return localView;
//        }
//      }
//      while (!((String)((HashMap)UserRequestActivity.this.rides.get(paramInt)).get("accept")).equals("3"));
//      localTextView5.setText("Cancelled");
//      return localView;
//    }
//  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.UserRequestActivity
 * JD-Core Version:    0.6.2
 */