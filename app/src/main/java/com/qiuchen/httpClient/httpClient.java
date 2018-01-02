package com.qiuchen.httpClient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Auther: cheny
 * CreateDate 2017/7/1.
 * 2017.7.1 重构网络请求模块代码 此后无需改动
 */

public class httpClient {

  public static String cookies;

  public static void setCookies(String cookies) {
    httpClient.cookies = cookies;
  }

  public static String getCookies() {
    return cookies;
  }

  public static Bitmap Request_Image(String url, String Cookie) throws IOException {
    ResponseData responseData = Request(url, 0, null, Cookie, null, 10000, 10000, false);
    if (responseData != null) {
      return DecodeByteToBitmap(responseData.responseByte);
    }
    return null;
  }

  public static Bitmap Request_Image(String url) throws IOException {
    return Request_Image(url, "");
  }

  public static String Request_Str(String url, String cookie) throws IOException {
    ResponseData responseData = Request(url, 0, null, cookie, null, 10000, 10000, false);
    if (responseData != null) {
      return DecodeByteToString(responseData.responseByte);
    }
    return null;
  }

  public static String Request_Str(String url) throws IOException {
    return Request_Str(url, "");
  }

  public static String Request_Str(String url, String Data, String Cookie, String Chaset) throws
          IOException {
    ResponseData responseData = Request(url, 1, Data, Cookie, null, 10000, 10000, false);
    if (responseData != null) {
      return DecodeByteToString(responseData.responseByte, Chaset);
    }
    return null;
  }

  public static String Request_Str(String url, String Data, String Cookie) throws
          IOException {
    return Request_Str(url, Data, Cookie, "UTF-8");
  }

  public static ResponseData Request(String url, String Data, String Cookie) throws IOException {
    return Request(url, 1, Data, Cookie, null, 10000, 10000, false);
  }

  public static ResponseData Request(String url, String Cookie) throws IOException {
    return Request(url, 0, null, Cookie, null, 10000, 10000, false);
  }

  public static ResponseData Request(String url) throws IOException {
    return Request(url, 0, null, "", null, 10000, 10000, false);
  }

  public static ResponseData Request(String urls, int RequestCategory, String RequestData, String
          RequestCookie, String RequestHeader, int ConnectTimeout, int ReadTimeout
          , boolean isAllowRedirect) throws IOException {
    URL mUrl = new URL(urls);
    HttpURLConnection mHttpUrlConnection = (HttpURLConnection) mUrl.openConnection();
    String Method;
    switch (RequestCategory) {
      case 0:
        Method = "GET";
        break;
      case 1:
        Method = "POST";
        break;
      default:
        return null;
    }
    ResponseData responseData = new ResponseData();
    mHttpUrlConnection.setRequestMethod(Method);
    mHttpUrlConnection.setDoInput(true);
    if (RequestCategory == 1) {
      mHttpUrlConnection.setDoOutput(true);
    }
    mHttpUrlConnection.setConnectTimeout(ConnectTimeout);
    mHttpUrlConnection.setReadTimeout(ReadTimeout);
    mHttpUrlConnection.setInstanceFollowRedirects(isAllowRedirect);
    mHttpUrlConnection.setRequestProperty("Accept", "*/*");
    mHttpUrlConnection.setRequestProperty("Referer", mHttpUrlConnection.getURL().toString());
    mHttpUrlConnection.setRequestProperty("Accept-Language", "zh-cn");
    mHttpUrlConnection.setRequestProperty("Cookie", RequestCookie);
    mHttpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    if (RequestHeader != null) {
      String[] Headers = RequestHeader.split("\\n");
      for (String head : Headers) {
        String[] Temp = head.split(":");
        if (Temp.length >= 2) {
          mHttpUrlConnection.setRequestProperty(Temp[0].trim(), Temp[1].trim());
        }
      }
    }

    if (RequestCategory == 1) {
      mHttpUrlConnection.setRequestProperty("Content-Length",
              String.valueOf(RequestData.length()));
      OutputStream outputStream = mHttpUrlConnection.getOutputStream();
      outputStream.write(RequestData.getBytes());
    }

    int responseCode = 0;
    try {
      responseCode = mHttpUrlConnection.getResponseCode();
    } catch (Exception e) {
      e.printStackTrace();
    }

    String ResponseCookie = null;
    responseData.responseCode = responseCode;
    switch (responseCode) {
      case 0:
        return null;
      case 200:
        ResponseCookie = getSetCookies(mHttpUrlConnection);
        responseData.responseByte = DecodeInputStreamToByte(mHttpUrlConnection.getInputStream());
        responseData.responseText = DecodeByteToString(responseData.responseByte);
        break;
      case 302:
        ResponseCookie = getSetCookies(mHttpUrlConnection);
        responseData.RedrictUrl = mHttpUrlConnection.getHeaderField("Location");
        break;
      default:
        ResponseCookie = getSetCookies(mHttpUrlConnection);
        responseData.responseByte = DecodeInputStreamToByte(mHttpUrlConnection.getInputStream());
        responseData.responseText = DecodeByteToString(responseData.responseByte);
        break;
    }
    CookieUpdata(ResponseCookie);
    responseData.responseCookie = ResponseCookie;
    return responseData;
  }

  private static String getSetCookies(URLConnection Field) {

    Map<String, List<String>> listMap = Field.getHeaderFields();
    List<String> list = listMap.get("Set-Cookie");
    StringBuffer result = new StringBuffer();
    if (list != null) {
      Iterator<String> iterator = list.iterator();
      while (iterator.hasNext())
        result.append(iterator.next() + ";");
    }
    return result.toString();
  }

  private static byte[] DecodeInputStreamToByte(InputStream inputStream) throws IOException {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    int len;
    byte[] bytes = new byte[1024];
    while ((len = inputStream.read(bytes)) != -1) {
      byteArrayOutputStream.write(bytes, 0, len);
    }
    return byteArrayOutputStream.toByteArray();
  }

  public static String DecodeByteToString(byte[] bytes) {
    return new String(bytes);
  }

  public static String DecodeByteToString(byte[] bytes, String charset) throws UnsupportedEncodingException {
    return new String(bytes, charset);
  }

  public static Bitmap DecodeByteToBitmap(byte[] bytes) {
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
  }

  public static String EncodeStr(String str) throws UnsupportedEncodingException {
    return URLEncoder.encode(str, "UTF-8");
  }

  public static String EncodeStr(String str, String chaset) throws UnsupportedEncodingException {
    return URLEncoder.encode(str, chaset);
  }

  public static Bitmap BlurBitmap(Bitmap bitmap, float blur, Context context) {
    Bitmap TempBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
            Bitmap.Config.ARGB_8888);
    RenderScript renderScript = RenderScript.create(context);
    ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript,
            Element.U8_4(renderScript));
    Allocation allocationIn = Allocation.createFromBitmap(renderScript, bitmap);
    Allocation allocationOut = Allocation.createFromBitmap(renderScript, TempBitmap);
    scriptIntrinsicBlur.setRadius(blur);
    scriptIntrinsicBlur.setInput(allocationIn);
    scriptIntrinsicBlur.forEach(allocationOut);
    allocationOut.copyTo(TempBitmap);
    bitmap.recycle();
    renderScript.destroy();
    return TempBitmap;
  }

  public static void CookieUpdata(String NewCookie) {
    cookies = NewCookie;
  }

  public static Bitmap getBingImage() throws IOException {
    ResponseData s = Request("http://guolin.tech/api/bing_pic");
    s = Request(s.responseText);
    return DecodeByteToBitmap(s.responseByte);
  }

}