package com.zjft.microservice.treasurybrain.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {

    private static Log log = LogFactory.getLog(FileUtils.class);

    public static String getBasePath() {
        try {
            String fileName[] = FileUtils.class.getResource("").toString().split("/");
            String file;
            String filePath = "";
            for (int i = 0; i < fileName.length - 6; i++) {
                file = fileName[i].replace("%20", " ");
                if (file != null) {
                    filePath = filePath + file + System.getProperty("file.separator");
                }
            }
            return filePath.substring(6);
        } catch (Exception e) {
            log.info("获取项目根路径失败", e);
            return null;
        }
    }

    public static void createDirectories(String filePath) {
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            return;
        }
        cleanDirectory(directory);

        if (!directory.delete()) {
            String message = "Unable to delete directory " + directory + ".";
            throw new IOException(message);
        }
    }

    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            String message = directory + " does not exist";
            throw new IllegalArgumentException(message);
        }

        if (!directory.isDirectory()) {
            String message = directory + " is not a directory";
            throw new IllegalArgumentException(message);
        }

        File[] files = directory.listFiles();
        if (files == null) {
            throw new IOException("Failed to list contents of " + directory);
        }

        IOException exception = null;
        for (File file : files) {
            try {
                forceDelete(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }

        if (null != exception) {
            throw exception;
        }
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent){
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }

    public static void main(String[] args) {
        System.out.print(getBasePath());
    }

}
