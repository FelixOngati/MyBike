package xyz.fegati.mybike;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import xyz.fegati.mybike.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverTrackActivity extends Activity
{
  public static String driver_email = "";
  public static final String getDataUrl = "http://futureline.lk/taxi/app/getlocations.php";
  Context con;
  ArrayList<Driver> drivers;
  private GoogleMap googleMap;
  Handler handler;
  JSONParser jparser = new JSONParser();
  ArrayList<Marker> mapMarker;
  HashMap<Marker, Driver> markers;
  Runnable run;

  private void initilizeMap()
  {
    if (this.googleMap == null)
    {
      this.googleMap = ((MapFragment)getFragmentManager().findFragmentById(2131165232)).getMap();
      this.googleMap.setMyLocationEnabled(true);
      if (this.googleMap == null)
        Toast.makeText(getApplicationContext(), "Sorry! unable to create maps", 0).show();
    }
  }

  private void removeMarkers()
  {
    Iterator localIterator = this.mapMarker.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        this.mapMarker.clear();
        return;
      }
      ((Marker)localIterator.next()).remove();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903043);
    this.con = this;
    driver_email = getIntent().getStringExtra("driver_email");
    this.markers = new HashMap();
    this.mapMarker = new ArrayList();
    try
    {
      initilizeMap();
      getActionBar().setHomeButtonEnabled(true);
      getActionBar().setDisplayHomeAsUpEnabled(true);
      new GetLocations().execute(new String[0]);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
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

  protected void onPause()
  {
    super.onPause();
    if (this.handler != null)
    {
      this.handler.removeCallbacks(this.run);
      this.run = null;
      this.handler = null;
    }
  }

  public void scheduleThread()
  {
    this.handler = new Handler();
    this.run = new Runnable()
    {
      public void run()
      {
        if (Util.isConnectingToInternet(DriverTrackActivity.this.con))
        {
          new DriverTrackActivity.GetLocations(DriverTrackActivity.this).execute(new String[0]);
          return;
        }
        Toast.makeText(DriverTrackActivity.this.con, "Internet is not active", 0).show();
      }
    };
    this.handler.postDelayed(this.run, 30000L);
  }

  class GetLocations extends AsyncTask<String, String, String>
  {
    String errmsg = "Server is down";
    int error = 0;
    ProgressDialog pDialog;
    String regiresult = "";
    int success = 0;
    String toastText = "Internet Problem";

    GetLocations()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      ArrayList localArrayList = new ArrayList();
      JSONObject localJSONObject1 = DriverTrackActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/getlocations.php", "POST", localArrayList);
      try
      {
        this.success = localJSONObject1.getInt("success");
        if (this.success == 1)
        {
          DriverTrackActivity.this.drivers = new ArrayList();
          JSONArray localJSONArray = localJSONObject1.getJSONArray("location");
          for (int i = 0; i < localJSONArray.length(); i++)
          {
            JSONObject localJSONObject2 = localJSONArray.getJSONObject(i);
            Driver localDriver = new Driver();
            localDriver.setId(localJSONObject2.getString("id"));
            localDriver.setName(localJSONObject2.getString("name"));
            localDriver.setEmail(localJSONObject2.getString("email"));
            localDriver.setNumber(localJSONObject2.getString("number"));
            localDriver.setLatitude(localJSONObject2.getString("latitude"));
            localDriver.setLongitude(localJSONObject2.getString("longitude"));
            localDriver.setInfo(localJSONObject2.getString("info"));
            localDriver.setCost(localJSONObject2.getString("cost"));
            DriverTrackActivity.this.drivers.add(localDriver);
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        this.error = 1;
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
      this.pDialog.dismiss();
      if (this.error == 1)
        if (Util.isConnectingToInternet(DriverTrackActivity.this.con))
          Toast.makeText(DriverTrackActivity.this.con, "Server is down. Please try again", 0).show();
      do
      {
        return;
        Util.showNoInternetDialog(DriverTrackActivity.this.con);
        return;
        if (this.success == 0)
        {
          Toast.makeText(DriverTrackActivity.this.con, "Data loading failed", 0).show();
          return;
        }
      }
      while (this.success != 1);
      DriverTrackActivity.this.markers = new HashMap();
      DriverTrackActivity.this.removeMarkers();
      for (int i = 0; ; i++)
      {
        if (i >= DriverTrackActivity.this.drivers.size())
        {
          DriverTrackActivity.this.scheduleThread();
          return;
        }
        if (((Driver)DriverTrackActivity.this.drivers.get(i)).getEmail().equals(DriverTrackActivity.driver_email))
        {
          MarkerOptions localMarkerOptions = new MarkerOptions();
          localMarkerOptions.position(new LatLng(Double.parseDouble(((Driver)DriverTrackActivity.this.drivers.get(i)).getLatitude()), Double.parseDouble(((Driver)DriverTrackActivity.this.drivers.get(i)).getLongitude())));
          localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(2130837543));
          Marker localMarker = DriverTrackActivity.this.googleMap.addMarker(localMarkerOptions);
          DriverTrackActivity.this.mapMarker.add(localMarker);
          DriverTrackActivity.this.markers.put(localMarker, (Driver)DriverTrackActivity.this.drivers.get(i));
          DriverTrackActivity.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(((Driver)DriverTrackActivity.this.drivers.get(i)).getLatitude()), Double.parseDouble(((Driver)DriverTrackActivity.this.drivers.get(i)).getLongitude())), 16.0F));
        }
      }
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(DriverTrackActivity.this.con);
      this.pDialog.setMessage("Updating drivers locations. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.DriverTrackActivity
 * JD-Core Version:    0.6.2
 */