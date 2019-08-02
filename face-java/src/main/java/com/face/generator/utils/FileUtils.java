package com.face.generator.utils;

import lombok.extern.slf4j.Slf4j;

import javax.activation.MimetypesFileTypeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

@Slf4j
public final class FileUtils {
    private static MimetypesFileTypeMap CONTENT_TYPE_MAP = new MimetypesFileTypeMap();

    private FileUtils(){

    }

    /**
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     *
     * @param fileUrl
     * @return
     */
    public static  String getFileName(String fileUrl){
        return fileUrl.substring(fileUrl.lastIndexOf("/")+1);
    }

    public static  String getFileContentType(String fileName){
        return CONTENT_TYPE_MAP.getContentType(fileName);
    }


    /**
     * 读一个文件的行记录,返回ArrayList
     * @param fileUrl
     * @return
     * @throws Exception
     */
    public static ArrayList<String> getFileLine(String fileUrl) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(fileUrl));
        String longStr = null;
        ArrayList<String> al = new ArrayList<String>();
        while ((longStr = br.readLine()) != null) {
            al.add(longStr);
        }
        return al;
    }


}
