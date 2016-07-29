package xyz.fegati.mybike.util;

import java.io.InputStream;
import org.json.JSONObject;

public class JSONParser
{
  static InputStream is = null;
  static JSONObject jObj = null;
  static String json = "";

  // ERROR //
  public JSONObject makeHttpRequest(String paramString1, String paramString2, java.util.List<org.apache.http.NameValuePair> paramList)
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc 37
    //   3: invokevirtual 43	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   6: ifeq +130 -> 136
    //   9: new 45	org/apache/http/impl/client/DefaultHttpClient
    //   12: dup
    //   13: invokespecial 46	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   16: astore 15
    //   18: new 48	org/apache/http/client/methods/HttpPost
    //   21: dup
    //   22: aload_1
    //   23: invokespecial 51	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   26: astore 16
    //   28: aload 16
    //   30: new 53	org/apache/http/client/entity/UrlEncodedFormEntity
    //   33: dup
    //   34: aload_3
    //   35: invokespecial 56	org/apache/http/client/entity/UrlEncodedFormEntity:<init>	(Ljava/util/List;)V
    //   38: invokevirtual 60	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   41: aload 15
    //   43: aload 16
    //   45: invokevirtual 64	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   48: invokeinterface 70 1 0
    //   53: invokeinterface 76 1 0
    //   58: putstatic 14	com/nas/cruzer/util/JSONParser:is	Ljava/io/InputStream;
    //   61: new 78	java/io/BufferedReader
    //   64: dup
    //   65: new 80	java/io/InputStreamReader
    //   68: dup
    //   69: getstatic 14	com/nas/cruzer/util/JSONParser:is	Ljava/io/InputStream;
    //   72: ldc 82
    //   74: invokespecial 85	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   77: bipush 8
    //   79: invokespecial 88	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   82: astore 5
    //   84: new 90	java/lang/StringBuilder
    //   87: dup
    //   88: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   91: astore 6
    //   93: aload 5
    //   95: invokevirtual 95	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   98: astore 11
    //   100: aload 11
    //   102: ifnonnull +107 -> 209
    //   105: getstatic 14	com/nas/cruzer/util/JSONParser:is	Ljava/io/InputStream;
    //   108: invokevirtual 100	java/io/InputStream:close	()V
    //   111: aload 6
    //   113: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   116: putstatic 20	com/nas/cruzer/util/JSONParser:json	Ljava/lang/String;
    //   119: new 105	org/json/JSONObject
    //   122: dup
    //   123: getstatic 20	com/nas/cruzer/util/JSONParser:json	Ljava/lang/String;
    //   126: invokespecial 106	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   129: putstatic 16	com/nas/cruzer/util/JSONParser:jObj	Lorg/json/JSONObject;
    //   132: getstatic 16	com/nas/cruzer/util/JSONParser:jObj	Lorg/json/JSONObject;
    //   135: areturn
    //   136: aload_2
    //   137: ldc 108
    //   139: invokevirtual 43	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   142: ifeq -81 -> 61
    //   145: new 45	org/apache/http/impl/client/DefaultHttpClient
    //   148: dup
    //   149: invokespecial 46	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   152: new 110	org/apache/http/client/methods/HttpGet
    //   155: dup
    //   156: aload_1
    //   157: invokespecial 111	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   160: invokevirtual 64	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   163: invokeinterface 70 1 0
    //   168: invokeinterface 76 1 0
    //   173: putstatic 14	com/nas/cruzer/util/JSONParser:is	Ljava/io/InputStream;
    //   176: goto -115 -> 61
    //   179: astore 14
    //   181: aload 14
    //   183: invokevirtual 114	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   186: goto -125 -> 61
    //   189: astore 13
    //   191: aload 13
    //   193: invokevirtual 115	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   196: goto -135 -> 61
    //   199: astore 4
    //   201: aload 4
    //   203: invokevirtual 116	java/io/IOException:printStackTrace	()V
    //   206: goto -145 -> 61
    //   209: aload 6
    //   211: new 90	java/lang/StringBuilder
    //   214: dup
    //   215: aload 11
    //   217: invokestatic 120	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   220: invokespecial 121	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   223: ldc 123
    //   225: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   231: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: pop
    //   235: goto -142 -> 93
    //   238: astore 7
    //   240: ldc 129
    //   242: new 90	java/lang/StringBuilder
    //   245: dup
    //   246: ldc 131
    //   248: invokespecial 121	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   251: aload 7
    //   253: invokevirtual 132	java/lang/Exception:toString	()Ljava/lang/String;
    //   256: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   259: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   262: invokestatic 138	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   265: pop
    //   266: goto -147 -> 119
    //   269: astore 9
    //   271: ldc 140
    //   273: new 90	java/lang/StringBuilder
    //   276: dup
    //   277: ldc 142
    //   279: invokespecial 121	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   282: aload 9
    //   284: invokevirtual 143	org/json/JSONException:toString	()Ljava/lang/String;
    //   287: invokevirtual 127	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   290: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   293: invokestatic 138	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   296: pop
    //   297: goto -165 -> 132
    //
    // Exception table:
    //   from	to	target	type
    //   0	61	179	java/io/UnsupportedEncodingException
    //   136	176	179	java/io/UnsupportedEncodingException
    //   0	61	189	org/apache/http/client/ClientProtocolException
    //   136	176	189	org/apache/http/client/ClientProtocolException
    //   0	61	199	java/io/IOException
    //   136	176	199	java/io/IOException
    //   61	93	238	java/lang/Exception
    //   93	100	238	java/lang/Exception
    //   105	119	238	java/lang/Exception
    //   209	235	238	java/lang/Exception
    //   119	132	269	org/json/JSONException
    return null;
  }
}
