package com.devop.aashish.utility;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 1/4/2019
 * <p>
 * This utility class is  used to get the static resources from jar file which has to be copied to
 * generated directory
 */


class ResourceReader {

    /**
     * @param targetDirectory    The target location where file has to be copied
     * @param inputPathDirectory The source location from where file has to be copied
     * @throws IOException if there is some system runtime exception while copying static resource
     *                     <p>
     *                     System identifies if the method invocation is from jar file or from IDE and afterwards it
     *                     process the file copy operation.
     *                     </p>
     */
    @SuppressWarnings({"ResultOfMethodCallIgnored"})
    void getStaticResourceDirectory(String targetDirectory, String inputPathDirectory) throws IOException {
        File jarFile;
        try {
            jarFile = new File((getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).getPath());
        } catch (URISyntaxException e) {
            throw new IOException("Unable to get path from jar", e);
        }
        if (jarFile.isFile()) {
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                final JarEntry jarEntry = entries.nextElement();
                final String name = jarEntry.getName();
                if (name.startsWith(inputPathDirectory + "/") && !jarEntry.isDirectory()) {
                    File dest = new File(targetDirectory + "/" + jarEntry.getName().substring(inputPathDirectory.length() + 1));
                    File parent = dest.getParentFile();
                    if (parent != null) {
                        parent.mkdirs();
                    }

                    try (FileOutputStream out = new FileOutputStream(dest); InputStream in = jar.getInputStream(jarEntry)) {
                        byte[] buffer = new byte[8 * 1024];
                        int s;
                        while ((s = in.read(buffer)) > 0) {
                            out.write(buffer, 0, s);
                        }
                    } catch (IOException e) {
                        throw new IOException("Could not copy asset from jar file", e);
                    }
                }
            }
            jar.close();
        } else {
            File outputFile = new File(getPathOfResource(inputPathDirectory));
            FileUtils.copyDirectory(outputFile, new File(targetDirectory));
        }
    }

    /**
     * @param directoryName of resource
     * @return the complete path of resource of class loader /src/main/resources
     * <p>
     * This return the path from target directory, if app is invoked from IDE
     */
    private String getPathOfResource(String directoryName) {
        String completeFilePath = "";
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(directoryName);
        if (url != null) {
            completeFilePath = url.getPath();
        }
        return completeFilePath;

    }

}
