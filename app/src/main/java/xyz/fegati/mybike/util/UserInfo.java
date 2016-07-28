package xyz.fegati.mybike.util;

public class UserInfo
{
  private static String email;
  private static String id;
  private static String name;
  private static String password;
  private static String phonenumber;

  public static String getEmail()
  {
    return email;
  }

  public static String getId()
  {
    return id;
  }

  public static String getName()
  {
    return name;
  }

  public static String getPassword()
  {
    return password;
  }

  public static String getPhonenumber()
  {
    return phonenumber;
  }

  public static void setEmail(String paramString)
  {
    email = paramString;
  }

  public static void setId(String paramString)
  {
    id = paramString;
  }

  public static void setName(String paramString)
  {
    name = paramString;
  }

  public static void setPassword(String paramString)
  {
    password = paramString;
  }

  public static void setPhonenumber(String paramString)
  {
    phonenumber = paramString;
  }
}

/* Location:           C:\Users\Erick\Desktop\extract\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.nas.cruzer.util.UserInfo
 * JD-Core Version:    0.6.2
 */