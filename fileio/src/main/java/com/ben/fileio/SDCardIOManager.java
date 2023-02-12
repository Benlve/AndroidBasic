package com.ben.fileio;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SDCardIOManager {

    private static final String TAG = "SDCardIOManager";

    private String sdCardRoot = null;

    private SDCardIOManager() {
        //1.获取SD卡的根目录
        sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.d(TAG, "sdCardRoot = " + sdCardRoot);
    }

    public static SDCardIOManager getInstance() {
        if (instance == null) {
            synchronized (SDCardIOManager.class) {
                if (instance == null) {
                    instance = new SDCardIOManager();
                }
            }
        }
        return instance;
    }

    private static SDCardIOManager instance;


    //2.在SD卡上创建文件夹目录
    public File createDirOnSDCard(String dir) {
        File dirFile = new File(sdCardRoot + File.separator + dir + File.separator);
        boolean isSuccess = dirFile.mkdir();
        Log.d(TAG, "createDirOnSDCard() dirFile = " + dirFile + ", isSuccess = " + isSuccess);
        return dirFile;
    }

    //3.在SD卡上创建文件
    public File createFileOnSDCard(String fileName, String dir) throws IOException {
        File file = new File(sdCardRoot + File.separator + dir + File.separator + fileName);
        file.createNewFile();
        return file;
    }

    //4.判断文件是否存在于SD卡的某个目录
    public boolean isFileExist(String fileName, String path) {
        File file = new File(sdCardRoot + path + File.separator + fileName);
        return file.exists();
    }

    //5.写入数据到SD卡中
    public File writeData2SDCard(String path, String fileName, InputStream data) {
        File file = null;
        OutputStream output = null;

        try {
            createDirOnSDCard(path);  //创建目录
            file = createFileOnSDCard(fileName, path);  //创建文件
            output = new FileOutputStream(file);
            byte buffer[] = new byte[2 * 1024];          //每次写2K数据
            int temp;
            while ((temp = data.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }
            output.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();    //关闭数据流操作
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return file;
    }
}
