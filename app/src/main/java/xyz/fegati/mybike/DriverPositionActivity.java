package xyz.fegati.mybike;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import xyz.fegati.mybike.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverPositionActivity extends Activity
        implements GoogleMap.OnMarkerClickListener {
  public static final String getDataUrl = "http://futureline.lk/taxi/app/getlocations.php";
  public static final String taxiRequUrl = "http://futureline.lk/taxi/app/requesttaxi.php";
  private final int REQ_CODE_SPEECH_INPUT = 100;
  Intent alIntent;
  AlarmManager alarmManager;
  PendingIntent appIntent;
  Context con;
  ArrayList<Driver> drivers;
  private GoogleMap googleMap;
  GPSTracker gps;
  Handler handler;
  JSONParser jparser = new JSONParser();
  ArrayList<Marker> mapMarker;
  HashMap<Marker, Driver> markers;
  Runnable run;
  Driver selectedDriver;
  SharedPreferences sh;
  Button spchBtn;

  private void initilizeMap() {
    if (this.googleMap == null) {
      this.googleMap = ((MapFragment) getFragmentManager().findFragmentById(2131165232)).getMap();
      if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return;
      }
      this.googleMap.setMyLocationEnabled(true);
      CameraUpdate localCameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(this.gps.getLatitude(), this.gps.getLongitude()), 14.0F);
      this.googleMap.animateCamera(localCameraUpdate);
      this.googleMap.setOnMarkerClickListener(this);
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

  public void findTheNearestDriver(String paramString)
  {
    Location localLocation1 = new Location("My");
    ArrayList localArrayList;
    int i;
    label47: int j;
    float f;
    if (paramString == null)
    {
      localLocation1.setLatitude(this.gps.getLatitude());
      localLocation1.setLongitude(this.gps.getLongitude());
      localArrayList = new ArrayList();
      i = 0;
      if (i < this.mapMarker.size())
        break label117;
      j = 0;
      f = ((Float)localArrayList.get(0)).floatValue();
    }
    for (int k = 0; ; k++)
    {
      if (k >= localArrayList.size())
      {
        onMarkerClick((Marker)this.mapMarker.get(j));
        return;
        localLocation1 = new Location(paramString);
        break;
        label117: LatLng localLatLng = ((Marker)this.mapMarker.get(i)).getPosition();
        Location localLocation2 = new Location("Driver");
        localLocation2.setLatitude(localLatLng.latitude);
        localLocation2.setLongitude(localLatLng.longitude);
        localArrayList.add(Float.valueOf(localLocation1.distanceTo(localLocation2)));
        i++;
        break label47;
      }
      if (((Float)localArrayList.get(k)).floatValue() < f)
      {
        f = ((Float)localArrayList.get(k)).floatValue();
        j = k;
      }
    }
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    default:
    case 100:
    }
    String str;
    do
    {
      do
        return;
      while ((paramInt2 != -1) || (paramIntent == null));
      str = (String)paramIntent.getStringArrayListExtra("android.speech.extra.RESULTS").get(0);
      Toast.makeText(this.con, str, 1).show();
      if ((str.contains("driver near to my location")) || (str.contains("driver near to me")) || (str.contains("nearest taxi")) || (str.contains("nearest cab")) || (str.contains("taxi")) || (str.contains("nearest driver")) || (str.contains("who is near to me")) || (str.contains("nearest driver to my location")) || (str.contains("nearest driver to me")) || (str.contains("request nearest driver")) || (str.contains("who is the nearest driver")) || (str.contains("closest driver to me")) || (str.contains("find the closest driver")))
      {
        findTheNearestDriver(null);
        return;
      }
    }
    while ((!str.contains("the driver near")) && (!str.contains("driver near")) && (!str.contains("taxi near to")) && (!str.contains("driver near to")) && (!str.contains("taxi near")) && (!str.contains("driver at")) && (!str.contains("taxi at")) && (!str.contains("cabs near")) && (!str.contains("cab near")) && (!str.contains("cabs at")) && (!str.contains("cab near to")) && (!str.contains("cabs near to")));
    Toast.makeText(this.con, str.substring(15), 1).show();
    findTheNearestDriver(str.substring(15).trim());
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903043);
    this.sh = getSharedPreferences("CRUZER_PREF", 0);
    this.drivers = new ArrayList();
    this.gps = new GPSTracker(this);
    this.con = this;
    this.spchBtn = ((Button)findViewById(2131165233));
    if (!Util.isGPSOn(this))
      GPSTracker.showSettingsAlert(this);
    try
    {
      initilizeMap();
      this.markers = new HashMap();
      this.mapMarker = new ArrayList();
      this.spchBtn.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
          localIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
          localIntent.putExtra("android.speech.extra.LANGUAGE", Locale.getDefault());
          localIntent.putExtra("android.speech.extra.PROMPT", DriverPositionActivity.this.getString(2131099677));
          try
          {
            DriverPositionActivity.this.startActivityForResult(localIntent, 100);
            return;
          }
          catch (ActivityNotFoundException localActivityNotFoundException)
          {
            Toast.makeText(DriverPositionActivity.this.getApplicationContext(), DriverPositionActivity.this.getString(2131099678), 0).show();
          }
        }
      });
      new GetLocations().execute(new String[0]);
      startUpdateCheck();
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131361794, paramMenu);
    return true;
  }

  protected void onDestroy()
  {
    super.onDestroy();
    this.alIntent = new Intent(this, UpdateReceiver.class);
    this.appIntent = PendingIntent.getBroadcast(this, 1000, this.alIntent, 134217728);
    this.alarmManager = ((AlarmManager)getSystemService("alarm"));
    this.alarmManager.cancel(this.appIntent);
  }

  public boolean onMarkerClick(Marker paramMarker)
  {
    Toast.makeText(this.con, "Loading vehicle information..", 0).show();
    Driver localDriver = (Driver)this.markers.get(paramMarker);
    this.selectedDriver = localDriver;
    showDriverDetailsWindow(paramMarker, localDriver.getId(), localDriver.getInfo(), localDriver.getCost(), localDriver.getNumber());
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
      return false;
    case 2131165279:
      startActivity(new Intent(this, UserEditProfileActivity.class));
      return false;
    case 2131165280:
      startActivity(new Intent(this, UserRequestActivity.class));
      return false;
    case 2131165281:
    }
    SharedPreferences.Editor localEditor = this.sh.edit();
    localEditor.putString("loginemail", null);
    localEditor.putString("loginpass", null);
    localEditor.putBoolean("type", false);
    localEditor.commit();
    startActivity(new Intent(this, LoginActivity.class));
    finish();
    return false;
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

  protected void onResume()
  {
    super.onResume();
    initilizeMap();
    if (this.handler == null)
      scheduleThread();
  }

  public void scheduleThread()
  {
    this.handler = new Handler();
    this.run = new Runnable()
    {
      public void run()
      {
        if (Util.isConnectingToInternet(DriverPositionActivity.this.con))
        {
          new DriverPositionActivity.GetLocations(DriverPositionActivity.this).execute(new String[0]);
          return;
        }
        Toast.makeText(DriverPositionActivity.this.con, "Internet is not active", 0).show();
      }
    };
    this.handler.postDelayed(this.run, 30000L);
  }

  public void showDriverDetailsWindow(Marker paramMarker, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    final Dialog localDialog = new Dialog(this);
    localDialog.requestWindowFeature(1);
    localDialog.setContentView(2130903041);
    localDialog.setCancelable(true);
    TextView localTextView1 = (TextView)localDialog.findViewById(2131165211);
    TextView localTextView2 = (TextView)localDialog.findViewById(2131165213);
    TextView localTextView3 = (TextView)localDialog.findViewById(2131165215);
    TextView localTextView4 = (TextView)localDialog.findViewById(2131165216);
    final EditText localEditText1 = (EditText)localDialog.findViewById(2131165221);
    final LinearLayout localLinearLayout = (LinearLayout)localDialog.findViewById(2131165222);
    Button localButton1 = (Button)localDialog.findViewById(2131165224);
    Button localButton2 = (Button)localDialog.findViewById(2131165223);
    final RadioButton localRadioButton1 = (RadioButton)localDialog.findViewById(2131165218);
    RadioButton localRadioButton2 = (RadioButton)localDialog.findViewById(2131165217);
    final EditText localEditText2 = (EditText)localDialog.findViewById(2131165219);
    localRadioButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localEditText2.setVisibility(0);
      }
    });
    localRadioButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localEditText2.setVisibility(8);
      }
    });
    localTextView1.setText(paramString2);
    localTextView2.setText("Rs " + paramString3);
    localTextView3.setText(paramString4);
    Geocoder localGeocoder = new Geocoder(this.con, Locale.getDefault());
    try
    {
      List localList = localGeocoder.getFromLocation(paramMarker.getPosition().latitude, paramMarker.getPosition().longitude, 1);
      Object localObject;
      if (localList.size() > 0)
      {
        localObject = "";
        if (localList.size() <= 0);
      }
      for (int i = 0; ; i++)
      {
        int j = ((Address)localList.get(0)).getMaxAddressLineIndex();
        if (i >= j)
        {
          localTextView4.setText(((String)localObject).trim());
          final Button localButton3 = (Button)localDialog.findViewById(2131165220);
          localButton3.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              localButton3.setVisibility(8);
              localLinearLayout.setVisibility(0);
              localEditText1.setVisibility(0);
            }
          });
          localButton1.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              if (TextUtils.isEmpty(localEditText1.getText().toString()))
              {
                Toast.makeText(DriverPositionActivity.this.con, "Please enter the drop location", 0).show();
                return;
              }
              if (localRadioButton1.isChecked())
              {
                if (TextUtils.isEmpty(localEditText2.getText().toString()))
                {
                  Toast.makeText(DriverPositionActivity.this.con, "Please enter your pickup address", 0).show();
                  return;
                }
                DriverPositionActivity.RequestTaxi localRequestTaxi3 = new DriverPositionActivity.RequestTaxi(DriverPositionActivity.this);
                String[] arrayOfString3 = new String[2];
                arrayOfString3[0] = localEditText2.getText().toString();
                arrayOfString3[1] = localEditText1.getText().toString();
                localRequestTaxi3.execute(arrayOfString3);
                localDialog.cancel();
                return;
              }
              Geocoder localGeocoder = new Geocoder(DriverPositionActivity.this.con, Locale.getDefault());
              DriverPositionActivity.this.gps = new GPSTracker(DriverPositionActivity.this.con);
              while (true)
              {
                List localList;
                int i;
                try
                {
                  localList = localGeocoder.getFromLocation(DriverPositionActivity.this.gps.getLatitude(), DriverPositionActivity.this.gps.getLongitude(), 1);
                  if (localList.size() <= 0)
                    break;
                  localObject = "";
                  if (localList.size() > 0)
                  {
                    i = 0;
                    if (i < ((Address)localList.get(0)).getMaxAddressLineIndex())
                      break label382;
                  }
                  DriverPositionActivity.RequestTaxi localRequestTaxi2 = new DriverPositionActivity.RequestTaxi(DriverPositionActivity.this);
                  String[] arrayOfString2 = new String[2];
                  arrayOfString2[0] = localObject;
                  arrayOfString2[1] = localEditText1.getText().toString();
                  localRequestTaxi2.execute(arrayOfString2);
                }
                catch (IOException localIOException)
                {
                  Util.showToast(DriverPositionActivity.this.con, localIOException.getMessage());
                  DriverPositionActivity.RequestTaxi localRequestTaxi1 = new DriverPositionActivity.RequestTaxi(DriverPositionActivity.this);
                  String[] arrayOfString1 = new String[2];
                  arrayOfString1[0] = "";
                  arrayOfString1[1] = localEditText1.getText().toString();
                  localRequestTaxi1.execute(arrayOfString1);
                  localIOException.printStackTrace();
                }
                break;
                label382: String str = localObject + ((Address)localList.get(0)).getAddressLine(i) + "\n";
                Object localObject = str;
                i++;
              }
            }
          });
          localButton2.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              localDialog.cancel();
            }
          });
          localDialog.show();
          return;
        }
        String str = localObject + ((Address)localList.get(0)).getAddressLine(i) + "\n";
        localObject = str;
      }
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public void startUpdateCheck()
  {
    this.alIntent = new Intent(this, UpdateReceiver.class);
    this.appIntent = PendingIntent.getBroadcast(this, 1000, this.alIntent, 134217728);
    this.alarmManager = ((AlarmManager)getSystemService("alarm"));
    this.alarmManager.setRepeating(1, System.currentTimeMillis(), 30000L, this.appIntent);
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
      JSONObject localJSONObject1 = DriverPositionActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/getlocations.php", "POST", localArrayList);
      try
      {
        this.success = localJSONObject1.getInt("success");
        if (this.success == 1)
        {
          DriverPositionActivity.this.drivers = new ArrayList();
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
            DriverPositionActivity.this.drivers.add(localDriver);
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
        if (Util.isConnectingToInternet(DriverPositionActivity.this.con))
          Toast.makeText(DriverPositionActivity.this.con, "Server is down. Please try again", 0).show();
      do
      {
        return;
        Util.showNoInternetDialog(DriverPositionActivity.this.con);
        return;
        if (this.success == 0)
        {
          Toast.makeText(DriverPositionActivity.this.con, "Data loading failed", 0).show();
          return;
        }
      }
      while (this.success != 1);
      DriverPositionActivity.this.markers = new HashMap();
      DriverPositionActivity.this.removeMarkers();
      for (int i = 0; ; i++)
      {
        if (i >= DriverPositionActivity.this.drivers.size())
        {
          DriverPositionActivity.this.scheduleThread();
          return;
        }
        MarkerOptions localMarkerOptions = new MarkerOptions();
        localMarkerOptions.position(new LatLng(Double.parseDouble(((Driver)DriverPositionActivity.this.drivers.get(i)).getLatitude()), Double.parseDouble(((Driver)DriverPositionActivity.this.drivers.get(i)).getLongitude())));
        localMarkerOptions.icon(BitmapDescriptorFactory.fromResource(2130837543));
        Marker localMarker = DriverPositionActivity.this.googleMap.addMarker(localMarkerOptions);
        DriverPositionActivity.this.mapMarker.add(localMarker);
        DriverPositionActivity.this.markers.put(localMarker, (Driver)DriverPositionActivity.this.drivers.get(i));
      }
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(DriverPositionActivity.this.con);
      this.pDialog.setMessage("Updating drivers locations. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }

  class RequestTaxi extends AsyncTask<String, String, String>
  {
    int error = 0;
    String msg = "";
    ProgressDialog pDialog;
    String regiresult = "";
    int success = 0;
    String toastText = "Internet Problem";

    RequestTaxi()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      Calendar localCalendar = Calendar.getInstance();
      String str = localCalendar.get(5) + "-" + (1 + localCalendar.get(2)) + "-" + localCalendar.get(1) + " " + localCalendar.get(11) + ":" + localCalendar.get(12) + ":" + localCalendar.get(13);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("driver_id", DriverPositionActivity.this.selectedDriver.getId()));
      localArrayList.add(new BasicNameValuePair("driver_email", DriverPositionActivity.this.selectedDriver.getEmail()));
      localArrayList.add(new BasicNameValuePair("driver_name", DriverPositionActivity.this.selectedDriver.getName()));
      localArrayList.add(new BasicNameValuePair("sender_id", UserInfo.getId()));
      localArrayList.add(new BasicNameValuePair("name", UserInfo.getName()));
      localArrayList.add(new BasicNameValuePair("phone", UserInfo.getPhonenumber()));
      localArrayList.add(new BasicNameValuePair("location", paramArrayOfString[0]));
      localArrayList.add(new BasicNameValuePair("droplocation", paramArrayOfString[1]));
      localArrayList.add(new BasicNameValuePair("latitude", DriverPositionActivity.this.gps.getLatitude()));
      localArrayList.add(new BasicNameValuePair("longitude", DriverPositionActivity.this.gps.getLongitude()));
      localArrayList.add(new BasicNameValuePair("timedate", str));
      JSONObject localJSONObject = DriverPositionActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/requesttaxi.php", "POST", localArrayList);
      try
      {
        this.success = localJSONObject.getInt("success");
        this.msg = localJSONObject.getString("message");
        return null;
      }
      catch (Exception localException)
      {
        while (true)
        {
          this.error = 1;
          this.msg = localException.getMessage();
        }
      }
    }

    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      this.pDialog.dismiss();
      Toast.makeText(DriverPositionActivity.this.con, this.msg, 0).show();
      if (this.error == 1)
      {
        if (Util.isConnectingToInternet(DriverPositionActivity.this.con))
          Toast.makeText(DriverPositionActivity.this.con, "Server is down. Please try again", 0).show();
      }
      else
        return;
      Util.showNoInternetDialog(DriverPositionActivity.this.con);
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(DriverPositionActivity.this.con);
      this.pDialog.setMessage("Requesting for Taxi. Please wait...");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.DriverPositionActivity
 * JD-Core Version:    0.6.2
 */