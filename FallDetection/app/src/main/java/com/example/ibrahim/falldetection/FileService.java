package com.example.ibrahim.falldetection;

import android.content.Context;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterWriter;
import java.io.IOException;

/**
 * Created by Ibrahim on 29/9/17.
 */

public class FileService {

    private Context context;
    public FileService(Context context){
        this.context = context;
    }

    public boolean saveContentToFile(String fileName,int mode,byte[] data){
        boolean flag = false;
        FileOutputStream outputstream = null;
        try {
            outputstream = context.openFileOutput(fileName,mode);
            outputstream.write(data,0,data.length);
            flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputstream!=null){
                try {
                    outputstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public String readContentFromFile(String fileName){

        FileInputStream fileInputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            fileInputStream = context.openFileInput(fileName);
            int len = 0;
            byte[] data = new byte[4096];
            try {
                while((len=fileInputStream.read(data))!=-1){
                    outputStream.write(data,0,len);
                }
                return new String(outputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "";

    }

    public void cleanContent(String fileName) {
        String strs = "";
        saveContentToFile(fileName, Context.MODE_PRIVATE, strs.getBytes());
    }

}

