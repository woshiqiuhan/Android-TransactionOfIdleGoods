package com.hznu.transactionofidlegoods.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 安卓本地文件存取
 */
public class FilePersistenceUtil {

    //搜索记录文件名
    public static final String SREARCH_RECORDS_FILE_NAME = "searchrecords";

    //将数据存入对应文件中
    //覆盖原有内容
    public static void save(Context content, String str, String fileName) {
        try (FileOutputStream out = content.openFileOutput(fileName, Context.MODE_PRIVATE);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write(str);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    //直接添加至文件头部
    public static void addHead(Context content, String str, String fileName) {
        StringBuilder strb = new StringBuilder();
        strb.append(str + "\n");
        try {
            strb.append(load(content, fileName));
            save(content, strb.toString(), fileName);
        } catch (Exception ex) {
            save(content, str, fileName);
        }
    }

    //直接添加至文件末尾
    public static void add(Context content, String str, String fileName) {
        try (FileOutputStream out = content.openFileOutput(fileName, Context.MODE_APPEND);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write(str + "\n");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    //添加至文件头部且保证字符唯一
    public static boolean addHeadDistinct(Context content, String str, String fileName) {
        if (!load(content, fileName).contains(str)) {
            addHead(content, str, fileName);
            return true;
        } else {
            //若出现过了，则将当前记录提至文件顶部
            StringBuilder strb = new StringBuilder();
            strb.append(str + "\n");
            String[] splitStrs = load(content, fileName).split("\n");
            for (String splitStr : splitStrs) {
                if (!splitStr.equals(str)) {
                    strb.append(splitStr + "\n");
                }
            }
            save(content, strb.toString(), fileName);
            return false;
        }
    }

    //添加至文件末尾且保证字符唯一
    public static boolean addDistinct(Context content, String str, String fileName) {
        if (!load(content, fileName).contains(str)) {
            add(content, str, fileName);
            return true;
        }
        return false;
    }

    //删除某条记录
    public static void remove(Context content, String str, String fileName) {
        StringBuilder strb = new StringBuilder();
        try {
            String[] splits = load(content, fileName).split("\n");
            for (String split : splits) {
                if (!split.equals(str)) {
                    strb.append(split + "\n");
                }
            }
            save(content, strb.toString(), fileName);
        } catch (Exception ex) {
            save(content, str, fileName);
        }
    }

    //读取对应文件的内容
    public static String load(Context content, String fileName) {
        StringBuilder strb = new StringBuilder();
        try (FileInputStream in = content.openFileInput(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                strb.append(tmp + "\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return strb.toString();
    }
}
