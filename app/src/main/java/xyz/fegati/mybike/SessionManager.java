package xyz.fegati.mybike;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;

public class SessionManager
{
  private static final String IS_LOGIN = "IsLoggedIn";
  public static final String KEY_EMAIL = "email";
  public static final String KEY_NAME = "name";
  private static final String PREF_NAME = "AndroidHivePref";
  int PRIVATE_MODE = 0;
  Context _context;
  SharedPreferences.Editor editor;
  SharedPreferences pref;

  public SessionManager(Context paramContext)
  {
    this._context = paramContext;
    this.pref = this._context.getSharedPreferences("AndroidHivePref", this.PRIVATE_MODE);
    this.editor = this.pref.edit();
  }

  public void checkLogin()
  {
    if (!isLoggedIn())
    {
      Intent localIntent = new Intent(this._context, LoginActivity.class);
      localIntent.addFlags(67108864);
      localIntent.setFlags(268435456);
      this._context.startActivity(localIntent);
    }
  }

  public void createLoginSession(String paramString1, String paramString2)
  {
    this.editor.putBoolean("IsLoggedIn", true);
    this.editor.putString("name", paramString1);
    this.editor.putString("email", paramString2);
    this.editor.commit();
  }

  public HashMap<String, String> getUserDetails()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("name", this.pref.getString("name", null));
    localHashMap.put("email", this.pref.getString("email", null));
    return localHashMap;
  }

  public boolean isLoggedIn()
  {
    return this.pref.getBoolean("IsLoggedIn", false);
  }

  public void logoutUser()
  {
    this.editor.clear();
    this.editor.commit();
    Intent localIntent = new Intent(this._context, LoginActivity.class);
    localIntent.addFlags(67108864);
    localIntent.setFlags(268435456);
    this._context.startActivity(localIntent);
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.SessionManager
 * JD-Core Version:    0.6.2
 */