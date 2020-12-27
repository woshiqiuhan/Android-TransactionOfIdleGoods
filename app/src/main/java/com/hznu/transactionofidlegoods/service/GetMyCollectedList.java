package com.hznu.transactionofidlegoods.service;

import android.util.Log;

import com.hznu.transactionofidlegoods.domain.IdleGoods;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class GetMyCollectedList {
    public static String path = "http://wowowowo.vipgz1.idcfengye.com/demo/android/getmycollectedlist";
    static List<IdleGoods> idleGoods = null;

    public static List<IdleGoods> getMyCollectedList(String userId) {
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    PrintWriter out = null;
                    BufferedInputStream in = null;
                    HttpURLConnection httpURLConnection = null;
                    String data = null;//设置数据
                    try {
                        httpURLConnection = (HttpURLConnection) new URL(path).openConnection();

                        data = new StringBuilder("userId=").append(URLEncoder.encode(userId, "UTF-8")).toString();
                        Log.d("GetMyCollectedList", data);

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
                            in = new BufferedInputStream(httpURLConnection.getInputStream());
                            Log.d("GetMyCollectedList", "发送成功");

                            StringBuilder strb = new StringBuilder();
                            byte[] bytes = new byte[2048];
                            int len;
                            while ((len = in.read(bytes)) != -1) {
                                strb.append(new String(bytes, 0, len));
                            }
                            String result = URLDecoder.decode(strb.toString(), "UTF-8");
                            Log.d("GetMyCollectedList", result);

                            idleGoods = IdleGoods.parseToList(result);
                            Log.d("GetMyCollectedList", idleGoods.toString());
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
        return idleGoods;
    }
}
