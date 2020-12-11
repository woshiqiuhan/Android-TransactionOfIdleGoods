

package com.hznu.transactionofidlegoods.service;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserLogin {
    public static String path = "http://woshiqiuhan.free.idcfengye.com/demo/login";
    static boolean flag = false;

    public static boolean login(String userloginid, String userpassword) {
        Log.d("Login", path);
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    PrintWriter out = null;
                    BufferedReader in = null;
                    HttpURLConnection httpURLConnection = null;
                    String data = null;//设置数据
                    try {
                        httpURLConnection = (HttpURLConnection) new URL(path).openConnection();
                        data = new StringBuilder("userloginid=").append(URLEncoder.encode(userloginid, "UTF-8")).
                                append("&userpassword=").append(URLEncoder.encode(userpassword, "UTF-8")).toString();
                        Log.d("Login", data);


                        httpURLConnection.setConnectTimeout(8000);//设置连接超时时间
                        httpURLConnection.setReadTimeout(8000);//设置读取超时时间
                        httpURLConnection.setRequestMethod("POST");//设置请求方法,post

                        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//设置响应类型
                        httpURLConnection.setRequestProperty("Content-Length", data.length() + "");//设置内容长度
                        httpURLConnection.setDoOutput(true);//允许输出

                        httpURLConnection.connect();
                        out = new PrintWriter(httpURLConnection.getOutputStream());
                        out.print(data);
                        out.flush();


                        /**
                         * 坑点：若不调用httpURLConnection.getResponseCode();方法
                         * 请求没有发送出去！
                         */
                        int responseCode = httpURLConnection.getResponseCode();
                        if (responseCode == 200) {
                            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            Log.d("Login", "发送成功");
                            flag = in.readLine().equals("1");
                            Log.d("Login", "登录" + (flag ? "成功" : "失败"));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


            });
            thread.start();
            thread.join();  //使得调用线程等待该线程完成后，才能继续用下运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return flag;
    }
}
