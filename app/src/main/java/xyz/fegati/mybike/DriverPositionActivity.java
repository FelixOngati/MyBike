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

public class DriverPositionActivity extends Activity implements GoogleMap.OnMarkerClickListener {
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


  public boolean onMarkerClick(Marker paramMarker)
  {
    Toast.makeText(this.con, "Loading vehicle information..", Toast.LENGTH_SHORT).show();
    Driver localDriver = (Driver)this.markers.get(paramMarker);
    this.selectedDriver = localDriver;
//    showDriverDetailsWindow(paramMarker, localDriver.getId(), localDriver.getInfo(), localDriver.getCost(), localDriver.getNumber());
    return true;
  }

}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.DriverPositionActivity
 * JD-Core Version:    0.6.2
 */