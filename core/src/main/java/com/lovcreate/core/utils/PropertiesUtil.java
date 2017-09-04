package com.lovcreate.core.utils;

import android.content.Context;

import com.lovcreate.core.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 配置文件工具类
 * Created by Albert.Ma on 2015/10/27.
 */
public class PropertiesUtil {

    private static Context context;
    private static Properties properties;

    /**
     * 配置初始化
     * 配置文件存放在 /data/data/package_name/files
     */
    public static void initProperties(Context context) {

        PropertiesUtil.context = context;

        properties = loadConfig(context.getString(R.string.properties_path));

        //第一次运行初始化配置文件
        if (SharedPreferencesUtil.isFirstRun(context)) {
            properties = new Properties();

            /**
             * 存储例子
             */
            //项目名称
            properties.put("appName", AppUtil.getAPPName(context));

            saveConfig(context.getString(R.string.properties_path), properties);
        }

    }

    /**
     * 获取配置
     */
    public static String getPropertiesByKey(String key) {
        String value = null;
        try {
            value = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 设置配置
     *
     * @param key   键
     * @param value 值
     */
    public static void setPropertiesByKey(String key, String value) {
        properties.put(key, value);//put方法可以直接修改配置信息，不会重复添加
        saveConfig(context.getString(R.string.properties_path), properties);
    }


    //读取配置文件
    private static Properties loadConfig(String file) {
        Properties properties = new Properties();
        try {
            FileInputStream s = new FileInputStream(file);
            properties.load(s);
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    //保存配置文件
    private static boolean saveConfig(String file, Properties properties) {
        try {
            File fil = new File(file);
            if (!fil.exists())
                fil.createNewFile();
            FileOutputStream s = new FileOutputStream(fil);
            properties.store(s, "");
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
