package com.lovcreate.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件操作工具
 * Created by Albert.Ma on 2015/8/22.
 */
public class FileUtil {

    private String filePath;//文件目录
    private String fileName;//文件名
    private String filePathName;//目录+文件

    public FileUtil(String filePathName) {
        this.filePathName = filePathName;
    }

    public FileUtil(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /**
     * 创建文件目录
     */
    public void createFile() {

        File file = null;

        if (this.filePathName != null) {
            file = new File(filePathName);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = new File(filePath + fileName);
            if (!file.exists()) {//创建目录
                new File(filePath).mkdirs();
            } else {//清空文件
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * 向已创建的文件中写入数据
     */
    public void write(String str) {
        createFile();

        FileWriter fw = null;
        BufferedWriter bw = null;

        String pathName = filePathName != null ? filePathName : filePath + fileName;

        try {
            fw = new FileWriter(pathName, true);
            bw = new BufferedWriter(fw);

            bw.write(str); // 写入文件
            bw.flush(); // 刷新该流的缓冲

            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中获取字符
     */
    public static String readContentFromFile(String filePath) {
        File file = new File(filePath);
        InputStreamReader reader = null;
        String content = "";
        try {
            reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                content = content + lineTxt;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExist(String filePathName) {
        File file = new File(filePathName);
        if (file.exists()) {//文件是否存在
            if (file.isDirectory()) {//文件是否是目录
                return false;
            }
            return true;
        }
        return false;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
