package com.bw.zhuguiquan20191126;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * DateTime:2019/11/26 0026
 * author:朱贵全(Administrator)
 * function:
 */
public class NetUtils {
    //单例模式
    private static NetUtils netUtils=new NetUtils();

    private NetUtils() {
    }

    public static NetUtils getInstance() {
        return netUtils;
    }
    //判断有网无网方法
    public boolean hasNet(Context context){
        ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
        if (activeNetworkInfo != null&&activeNetworkInfo.isAvailable()) {
            return true;

        }else{
            return false;
        }
    }
    public boolean isWifi(Context context){
        ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
        if (activeNetworkInfo != null&&activeNetworkInfo.isAvailable()&&activeNetworkInfo.getType()==ConnectivityManager.TYPE_WIFI) {
            return true;

        }else{
            return false;
        }
    }
    public boolean isMobe(Context context){
        ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = systemService.getActiveNetworkInfo();
        if (activeNetworkInfo != null&&activeNetworkInfo.isAvailable()&&activeNetworkInfo.getType()==ConnectivityManager.TYPE_MOBILE) {
            return true;

        }else{
            return false;
        }
    }
    @SuppressLint("StaticFieldLeak")
    //获取数据的方法
    public void getJson(final String str, final MyCallBack myCallBack){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPostExecute(String value) {
                //设置
               myCallBack.ongetJson(value);
               //打印数据
               Log.i("xxx",value);
            }

            @Override
            protected String doInBackground(Void... voids) {
                InputStream inputStream=null;
                String json="";
                try {
                    URL url = new URL(str);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //设置get方法
                    urlConnection.setRequestMethod("GET");
                    //设置时间
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.connect();
                    if (urlConnection .getResponseCode()==200) {
                        //获取流
                       inputStream = urlConnection.getInputStream();
                         json = oiString(inputStream);
                    }else{
                        Log.i("xxx","无法链接");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream != null) {
                        try {
                            //关闭流
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                return json;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private String oiString(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int len=-1;
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        while ((len=inputStream.read(bytes))!=-1){
            byteArrayOutputStream.write(bytes,0,len);
        }
        byte[] bytes1 = byteArrayOutputStream.toByteArray();
        String s = new String(bytes1);
        return s;
    }
    @SuppressLint("StaticFieldLeak")
    //获取图片方法
    public void getPhoto(final String sr, final ImageView imageView){
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
               imageView.setImageBitmap(bitmap);
            }

            @Override
            protected Bitmap doInBackground(Void... voids) {
                InputStream inputStream=null;
                Bitmap bitmap=null;
                try {
                    URL url = new URL(sr);
                    //获取
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //get方法
                    urlConnection.setRequestMethod("GET");
                    //设置时间
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.connect();
                    if (urlConnection .getResponseCode()==200) {
                        //获取流
                        inputStream = urlConnection.getInputStream();
                         bitmap = oiBitMap(inputStream);
                    }else{
                        Log.i("xxx","无法链接");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (inputStream != null) {
                        try {
                            //关闭流
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return bitmap;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private Bitmap oiBitMap(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }
//接口
    interface MyCallBack{
        void ongetJson(String json);
    }
}
