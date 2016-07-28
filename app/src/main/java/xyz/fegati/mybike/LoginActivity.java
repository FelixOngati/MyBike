package xyz.fegati.mybike;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import xyz.fegati.mybike.util.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity
  implements View.OnClickListener
{
  public static final String getDataURL = null;
  public static final String loginURL = "http://futureline.lk/taxi/app/login.php";
  public static final String recoverPasswordURL = "http://itechbd.tk/texibooking/sendmail.php";
  private static final String regiURL = "http://futureline.lk/taxi/app/registration.php";
  Context con;
  EditText drloginEmail;
  EditText drloginPassword;
  TextView drloginResult;
  EditText emailEt;
  Button forgotpassBtn;
  JSONParser jparser = new JSONParser();
  Button loginSubmitBtn;
  EditText lostPassEmail;
  TextView lostPassResltText;
  EditText passwordEt;
  Button registerBtn;
  EditText registrationConfirmPassword;
  EditText registrationEmail;
  EditText registrationName;
  EditText registrationPassword;
  EditText registrationPhone;
  TextView registrationResult;
  SharedPreferences sh;
  Button submitBtn;

  private void init()
  {
    this.submitBtn = ((Button)findViewById(R.id.loginSubmitBtn));
    this.registerBtn = ((Button)findViewById(R.id.loginRegisterBtn));
    this.forgotpassBtn = ((Button)findViewById(R.id.loginForgotPassBtn));
    this.submitBtn.setOnClickListener(this);
    this.registerBtn.setOnClickListener(this);
    this.forgotpassBtn.setOnClickListener(this);
    this.emailEt = ((EditText)findViewById(R.id.loginEmailEt));
    this.passwordEt = ((EditText)findViewById(R.id.loginPassEt));
  }

  public void onClick(View paramView)
  {
    if (!Util.isConnectingToInternet(this))
    {
      Util.showNoInternetDialog(this);
      return;
    }
    switch (paramView.getId())
    {
    default:
      return;
    case R.id.loginSubmitBtn:
      if (!TextUtils.isEmpty(this.emailEt.getText().toString()))
      {
        if (!TextUtils.isEmpty(this.passwordEt.getText().toString()))
        {
          new Login().execute(new String[] { "client" });
          return;
        }
        Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        return;
      }
      Toast.makeText(this, "Please enter your email address", Toast.LENGTH_SHORT).show();
      return;
    case R.id.loginRegisterBtn:
      showRegisterDialog();
      return;
    case R.id.loginForgotPassBtn:
    }
    showLostPasswordDialog();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.login_layout);
    this.con = this;
    this.sh = getSharedPreferences("CRUZER_PREF", 0);
    init();
    String str1 = this.sh.getString("loginemail", null);
    String str2 = this.sh.getString("loginpass", null);
    boolean bool = this.sh.getBoolean("type", false);
    if ((str1 != null) && (str2 != null) && (!bool))
    {
      this.emailEt.setText(str1);
      this.passwordEt.setText(str2);
      this.submitBtn.performClick();
    }
    while ((str1 == null) || (str2 == null) || (!bool))
      return;
    showDriverLoginDialog();
    this.drloginEmail.setText(str1);
    this.drloginPassword.setText(str2);
    this.loginSubmitBtn.performClick();
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.login, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    default:
    case R.id.driverloginmenu:
    }
    while (true)
    {
//      return super.onOptionsItemSelected(paramMenuItem);
      showDriverLoginDialog();
    }
  }

  public void showDriverLoginDialog()
  {
    final Dialog localDialog = new Dialog(this);
    localDialog.requestWindowFeature(1);
    localDialog.setContentView(R.layout.driver_login_layout);
    localDialog.setCancelable(true);
    this.drloginEmail = ((EditText)localDialog.findViewById(R.id.loginEmailEt));
    this.drloginPassword = ((EditText)localDialog.findViewById(R.id.loginPassword));
    this.drloginResult = ((TextView)localDialog.findViewById(R.id.loginResultText));
    this.loginSubmitBtn = ((Button)localDialog.findViewById(R.id.loginSubmitBtn));
    this.loginSubmitBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if ((!TextUtils.isEmpty(LoginActivity.this.drloginEmail.getText().toString())) && (!TextUtils.isEmpty(LoginActivity.this.drloginPassword.getText().toString())))
        {
          new LoginActivity.Login().execute(new String[] { "driver" });
          return;
        }
        Toast.makeText(LoginActivity.this.con, "Please enter value", Toast.LENGTH_SHORT).show();
      }
    });
//    ((Button)localDialog.findViewById(2131165227)).setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramAnonymousView)
//      {
//        LoginActivity.this.showLostPasswordDialog();
//        localDialog.dismiss();
//      }
//    });
    localDialog.show();
    ((Button)localDialog.findViewById(R.id.loginCancelButton)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localDialog.cancel();
      }
    });
  }

  void showLostPasswordDialog()
  {
    Dialog localDialog = new Dialog(this);
    localDialog.setTitle("Recover Password");
    localDialog.setContentView(R.layout.lostpassword_layout);
    localDialog.setCancelable(true);
    this.lostPassEmail = ((EditText)localDialog.findViewById(R.id.lostPassEmailEditText));
    this.lostPassResltText = ((TextView)localDialog.findViewById(R.id.lostPassworResultText));
    ((Button)localDialog.findViewById(R.id.lostPasswordSubmitButton)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!TextUtils.isEmpty(LoginActivity.this.lostPassEmail.getText().toString()))
        {
          new LoginActivity.RecoverPassword().execute(new String[0]);
          return;
        }
        LoginActivity.this.lostPassResltText.setText("Please enter your email");
        LoginActivity.this.lostPassResltText.setVisibility(View.VISIBLE);
      }
    });
    localDialog.show();
  }

  public void showRegisterDialog()
  {
    final Dialog localDialog = new Dialog(this);
    localDialog.requestWindowFeature(1);
    localDialog.setContentView(R.layout.register_layout);
    localDialog.setCancelable(true);
    this.registrationName = ((EditText)localDialog.findViewById(R.id.registrationName));
    this.registrationEmail = ((EditText)localDialog.findViewById(R.id.registrationEmail));
    this.registrationPhone = ((EditText)localDialog.findViewById(R.id.registrationPhnNumber));
    this.registrationPassword = ((EditText)localDialog.findViewById(R.id.registrationPassword));
    this.registrationConfirmPassword = ((EditText)localDialog.findViewById(R.id.registrationConfirmPassword));
    this.registrationResult = ((TextView)localDialog.findViewById(R.id.registrationResultText));
    ((Button)localDialog.findViewById(R.id.registerSubmitBtn)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        String str = "Please Wait";
        if (!TextUtils.isEmpty(LoginActivity.this.registrationName.getText().toString()))
          if ((!TextUtils.isEmpty(LoginActivity.this.registrationEmail.getText().toString())) && (Util.validEmail(LoginActivity.this.registrationEmail.getText().toString())))
            if (!TextUtils.isEmpty(LoginActivity.this.registrationPhone.getText().toString()))
              if ((LoginActivity.this.registrationPassword.getText().toString().equals(LoginActivity.this.registrationConfirmPassword.getText().toString())) && (!TextUtils.isEmpty(LoginActivity.this.registrationPassword.getText().toString())))
                new LoginActivity.Registration().execute(new String[0]);
        while (true)
        {
          LoginActivity.this.registrationResult.setText(str);
          LoginActivity.this.registrationResult.setVisibility(View.VISIBLE);
          str = "Your password is not the same";
          continue;
//          str = "Please enter your mobile number";
//          continue;
//          str = "Please enter your email address";
//          continue;
//          str = "Please enter your name";
        }
      }
    });
    ((Button)localDialog.findViewById(R.id.registerCancelButton)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localDialog.cancel();
      }
    });
    localDialog.show();
  }

  class Login extends AsyncTask<String, String, String>
  {
    boolean driver = false;
    String email = "";
    int error = 0;
    ProgressDialog pDialog;
    String password = "";
    String s = "";
    int success = -1;

    Login()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      if ((paramArrayOfString != null) && (paramArrayOfString[0].equals("driver")))
      {
        this.email = LoginActivity.this.drloginEmail.getText().toString();
        this.password = LoginActivity.this.drloginPassword.getText().toString();
        this.driver = true;
      }
      while (true)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(new BasicNameValuePair("email", this.email));
        localArrayList.add(new BasicNameValuePair("password", this.password));
        if (this.driver)
        {
          localArrayList.add(new BasicNameValuePair("category", "driver"));
          UserInfo.setEmail(this.email);
          UserInfo.setPassword(this.password);
        }
        try
        {
          JSONObject localJSONObject1 = LoginActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/login.php", "POST", localArrayList);
          this.success = localJSONObject1.getInt("success");
          this.s = localJSONObject1.getString("message");
          if (this.success == 1)
          {
            JSONObject localJSONObject2 = localJSONObject1.getJSONArray("info").getJSONObject(0);
            UserInfo.setName(localJSONObject2.getString("name"));
            UserInfo.setPhonenumber(localJSONObject2.getString("number"));
            UserInfo.setId(localJSONObject2.getString("id"));
          }
          return null;
          this.email = LoginActivity.this.emailEt.getText().toString();
          this.password = LoginActivity.this.passwordEt.getText().toString();
          continue;
          localArrayList.add(new BasicNameValuePair("category", "client"));
        }
        catch (JSONException localJSONException)
        {
          while (true)
            this.error = 1;
        }
        catch (Exception localException)
        {
          while (true)
            this.error = 1;
        }
      }
    }

    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      this.pDialog.dismiss();
      if (this.error == 1)
        if (Util.isConnectingToInternet(LoginActivity.this.con))
          Toast.makeText(LoginActivity.this.con, "Server is down, Please try again later", Toast.LENGTH_SHORT).show();
      do
      {
//        return;
        Util.showNoInternetDialog(LoginActivity.this.con);
//        return;
        if (this.success == 0)
        {
          Toast.makeText(LoginActivity.this.con, this.s, Toast.LENGTH_LONG).show();
          return;
        }
      }
      while (this.success != 1);
      SharedPreferences.Editor localEditor = LoginActivity.this.sh.edit();
      localEditor.putString("loginemail", this.email);
      localEditor.putString("loginpass", this.password);
      localEditor.putBoolean("type", this.driver);
      localEditor.commit();
      Intent localIntent = new Intent(LoginActivity.this.con, DriverPositionActivity.class);
      if (this.driver)
        localIntent = new Intent(LoginActivity.this.con, DriverActivity.class);
      LoginActivity.this.startActivity(localIntent);
      Toast.makeText(LoginActivity.this.con, this.s, Toast.LENGTH_SHORT).show();
      LoginActivity.this.finish();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(LoginActivity.this.con);
      this.pDialog.setMessage("Login is processing......");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }

  class RecoverPassword extends AsyncTask<String, String, String>
  {
    int error = 0;
    String message = "";
    ProgressDialog pDialog;
    int success = 0;
    String toastText = "Internet is not available";

    RecoverPassword()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("email", LoginActivity.this.lostPassEmail.getText().toString()));
      try
      {
        JSONObject localJSONObject = LoginActivity.this.jparser.makeHttpRequest("http://itechbd.tk/texibooking/sendmail.php", "POST", localArrayList);
        this.success = localJSONObject.getInt("success");
        this.message = localJSONObject.getString("message");
        return null;
      }
      catch (JSONException localJSONException)
      {
        while (true)
          localJSONException.printStackTrace();
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
        if (Util.isConnectingToInternet(LoginActivity.this.con))
        {
          Toast.makeText(LoginActivity.this.con, "Server is down, Please try again later", Toast.LENGTH_SHORT).show();
          return;
        }
        Util.showNoInternetDialog(LoginActivity.this.con);
        return;
      }
      LoginActivity.this.lostPassResltText.setText(this.message);
      LoginActivity.this.lostPassResltText.setVisibility(View.VISIBLE);
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(LoginActivity.this.con);
      this.pDialog.setMessage("Sending Information.Please wait......");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(false);
      this.pDialog.show();
    }
  }

  class Registration extends AsyncTask<String, String, String>
  {
    String errmsg = "Server is down";
    int error = 0;
    ProgressDialog pDialog;
    String regiresult = "";
    int success = 0;
    String toastText = "Internet Problem";

    Registration()
    {
    }

    protected String doInBackground(String[] paramArrayOfString)
    {
      String str1 = LoginActivity.this.registrationName.getText().toString();
      String str2 = LoginActivity.this.registrationEmail.getText().toString();
      String str3 = LoginActivity.this.registrationPassword.getText().toString();
      String str4 = LoginActivity.this.registrationPhone.getText().toString();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("name", str1));
      localArrayList.add(new BasicNameValuePair("email", str2));
      localArrayList.add(new BasicNameValuePair("password", str3));
      localArrayList.add(new BasicNameValuePair("number", str4));
      localArrayList.add(new BasicNameValuePair("category", "client"));
      UserInfo.setEmail(str2);
      UserInfo.setName(str1);
      UserInfo.setPassword(str3);
      UserInfo.setPhonenumber(str4);
      try
      {
        JSONObject localJSONObject = LoginActivity.this.jparser.makeHttpRequest("http://futureline.lk/taxi/app/registration.php", "POST", localArrayList);
        this.success = localJSONObject.getInt("success");
        if (this.success == 1)
        {
          this.toastText = "Registration complete";
        }
        else if (this.success == 0)
        {
          this.regiresult = localJSONObject.getString("message");
          this.toastText = "Problem in registration";
        }
      }
      catch (JSONException localJSONException)
      {
        this.toastText = "There are some problem";
        localJSONException.printStackTrace();
//        break;
        this.toastText = "Link not found";
      }
      catch (Exception localException)
      {
        this.error = 1;
      }
      label291: return null;
    }

    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      this.pDialog.dismiss();
      if (this.error == 1)
      {
        Toast.makeText(LoginActivity.this.con, this.errmsg, Toast.LENGTH_SHORT).show();
        if (Util.isConnectingToInternet(LoginActivity.this.con))
        {
          LoginActivity.this.registrationResult.setText("Server is down. Please try again later");
          LoginActivity.this.registrationResult.setVisibility(View.VISIBLE);
        }
      }
      do
      {
//        return;
        Util.showNoInternetDialog(LoginActivity.this.con);
//        return;
        if (this.success == 0)
        {
          LoginActivity.this.registrationResult.setText(this.regiresult);
          LoginActivity.this.registrationResult.setVisibility(0);
          return;
        }
      }
      while (this.success != 1);
      LoginActivity.this.startActivity(new Intent(LoginActivity.this.con, DriverPositionActivity.class));
      Toast.makeText(LoginActivity.this.con, this.toastText, 0).show();
      LoginActivity.this.finish();
    }

    protected void onPreExecute()
    {
      super.onPreExecute();
      this.pDialog = new ProgressDialog(LoginActivity.this.con);
      this.pDialog.setMessage("Registration is processing......");
      this.pDialog.setIndeterminate(false);
      this.pDialog.setCancelable(true);
      this.pDialog.show();
    }
  }
}