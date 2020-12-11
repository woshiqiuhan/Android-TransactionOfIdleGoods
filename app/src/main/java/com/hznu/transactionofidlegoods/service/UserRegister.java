package com.hznu.transactionofidlegoods.service;

import android.util.Log;

import com.hznu.transactionofidlegoods.domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class UserRegister {
    public static String path = "http://woshiqiuhan.free.idcfengye.com/demo/register";
    static boolean flag = false;

    public static boolean register(User user) {
        Log.d("Register", path);
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    PrintWriter out = null;
                    BufferedReader in = null;
                    HttpURLConnection httpURLConnection;
                    String data;//设置数据
                    try {
                        httpURLConnection = (HttpURLConnection) new URL(path).openConnection();
                        data = new StringBuilder("userloginid=").append(URLEncoder.encode(user.getUserLoginId(), "UTF-8")).
                                append("&userpassword=").append(URLEncoder.encode(user.getUserPassword(), "UTF-8")).
                                append("&username=").append(URLEncoder.encode(user.getUserName(), "UTF-8")).
                                append("&useremail=").append(URLEncoder.encode(user.getUserEmail(), "UTF-8")).
                                append("&usephonenumber=").append(URLEncoder.encode(user.getUserPhoneNum(), "UTF-8")).toString();
                        Log.d("Register", data);


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
                            Log.d("Register", "发送成功");
                            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                            flag = in.readLine().equals("1");
                            Log.d("Register", "注册" + (flag ? "成功" : "失败"));
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
