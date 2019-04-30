package com.devop.aashish.utility;

import com.devop.aashish.config.GeneratorConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 1/4/2019
 * <p>
 * This is a utility class which is used to perform file related operation.
 * </p>
 */

public class FileHelper {

    @SuppressWarnings("UnusedReturnValue")
    /**
     *  Create directory at given path
     */
    public static boolean createDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        return dir.exists() || dir.mkdirs();
    }

    /**
     * @param directories create directories of given paths
     */
    public static void createDirectories(String... directories) {
        for (String directory : directories) {
            createDirectory(directory);
        }
    }

    /**
     * @param directories create directories of given paths
     */
    public static void createDirectories(List<String> directories) {
        for (String directory : directories) {
            createDirectory(directory);
        }
    }

    /**
     * @param targetDirectory       where files has to be copied
     * @param resourceDirectoryName source directory of files
     * @throws IOException if any system error occurred while copying resource
     */
    public static void copyResources(String targetDirectory, String resourceDirectoryName) throws IOException {
        ResourceReader resourceReader = new ResourceReader();
        resourceReader.getStaticResourceDirectory(targetDirectory, resourceDirectoryName);
    }

    public static void searchAndReplace(String packageDirectory) {
        String search = "com.devop.aashish.java.myapplication";
        String replace = GeneratorConfig.PACKAGE_ID;
        File entityDirectory = new File(packageDirectory);
        if (entityDirectory.exists() && entityDirectory.isDirectory()) {
            try {
                Files.walk(Paths.get(packageDirectory))
                        .filter(Files::isRegularFile).forEach(path -> {
                    try {
                        FileReader fr = new FileReader(path.toFile());
                        String s;
                        StringBuilder totalStr = new StringBuilder();
                        BufferedReader reader = new BufferedReader(fr);
                        String line = reader.readLine();
                        while (line != null) {
                            totalStr.append(line).append(System.lineSeparator());
                            line = reader.readLine();
                        }
                        FileWriter fw = new FileWriter(path.toFile());
                        String updated = totalStr.toString().replaceAll(search, replace);
                        fw.write(updated);
                        fw.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
