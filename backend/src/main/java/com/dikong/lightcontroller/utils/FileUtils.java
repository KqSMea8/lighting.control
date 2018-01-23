package com.dikong.lightcontroller.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Description
 * </p>
 * <p>
 *
 * @author lengrongfu
 * @create 2018年01月21日下午10:54
 * @see
 *      </P>
 */
public class FileUtils {

    public static void uploadFile(byte[] file, String filePath, String fileName)
            throws IOException {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static List<String> readFileByLine(InputStream inputStream) throws IOException {
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader br = new BufferedReader(reader);
        String line = "";
        List<String> files = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            files.add(line);
        }
        br.close();
        reader.close();
        inputStream.close();
        return files;
    }
}
