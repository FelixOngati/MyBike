package xyz.fegati.mybike.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Patterns;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util
{
  public static boolean isConnectingToInternet(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    NetworkInfo[] arrayOfNetworkInfo;
    if (localConnectivityManager != null)
    {
      arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
      if (arrayOfNetworkInfo == null);
    }
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfNetworkInfo.length)
        return false;
      if (arrayOfNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
        return true;
    }
  }

  public static boolean isGPSOn(Context paramContext)
  {
    return Boolean.valueOf(((LocationManager)paramContext.getSystemService("location")).isProviderEnabled("gps")).booleanValue();
  }

  public static void showNoInternetDialog(Context paramContext)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setTitle("No Internet");
    localBuilder.setMessage("Internet is not available. Please check your connection");
    localBuilder.setCancelable(true);
    localBuilder.setPositiveButton("Settings", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Intent localIntent = new Intent("android.settings.WIRELESS_SETTINGS");
        Util.this.startActivity(localIntent);
      }
    });
    localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    });
    localBuilder.create().show();
  }

  public static void showToast(Context paramContext, String paramString)
  {
    Toast.makeText(paramContext, paramString, 0).show();
  }

  public static boolean validEmail(String paramString)
  {
    return Patterns.EMAIL_ADDRESS.matcher(paramString).matches();
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.util.Util
 * JD-Core Version:    0.6.2
 */