package com.devop.aashish.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  https://github.com/aashish-aadarsh
 * Created Date: 1/4/2019
 * <p>
 * This is a utility class which is used to read property from files stored in system.
 * </p>
 */


public class PropertyFileUtil {

    private Properties prop = new Properties();
    private InputStream input;

    /**
     * @param inputFilePath The file path location of the folder
     * @param fileName      The file name for which the properties has to be loaded in memory
     *                      <p>
     *                      If folder path does not trail with file separator, a file separator is appended
     *                      </p>
     */
    public PropertyFileUtil(String inputFilePath, String fileName) {
        Logger logger = LoggerFactory.getLogger(PropertyFileUtil.class.getName());
        try {
            if (null != inputFilePath && !inputFilePath.isEmpty()) {
                if (!inputFilePath.endsWith(File.separator))
                    input = new FileInputStream(inputFilePath.concat(File.separator).concat(fileName));
                else
                    input = new FileInputStream(inputFilePath.concat(fileName));
                prop.load(input);
            } else {
                throw new IllegalArgumentException("InputFilePath does not have required property file");
            }
        } catch (IOException e) {
            logger.error("Unable to get PropertyFile {}", inputFilePath, e);
            throw new IllegalArgumentException("InputFilePath does not have required property file");
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("Error occurred while closing file", e);
                }
            }
        }
    }

    /**
     * This method is used to get the value corresponding to a key  of property file.
     *
     * @param key The key defined in property file
     * @return The value corresponding to the input key
     */
    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    /**
     * This method returns all the properties loaded in memorys
     *
     * @return All property
     */
    public Properties getProp() {
        return prop;
    }
}
