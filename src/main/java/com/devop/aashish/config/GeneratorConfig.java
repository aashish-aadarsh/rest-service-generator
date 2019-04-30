package com.devop.aashish.config;

import com.devop.aashish.constant.PropertyFileConstant;
import com.devop.aashish.utility.PropertyFileUtil;

import java.util.HashSet;
import java.util.Set;


/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 30/04/2019
 *
 * <p>
 * This utility class is  used to initialize property file reading, parsing the key from file.
 * </p>
 */
public class GeneratorConfig {

    public static String APPLICATION_NAME;
    public static String GROUP_ID;
    public static String ARTIFACT_ID;
    public static String PACKAGE_ID;
    public static String DATABASE_NAME;
    public static String APP_VERSION;
    public static String SPRING_BOOT_VERSION;
    public static String JAVA_VERSION;
    public static Set<String> DOMAIN_SET = new HashSet<>();
    static String APPLICATION_OUTPUT_DIRECTORY;
    private static PropertyFileUtil propertyFileUtil;

    public GeneratorConfig(String generatorFilePath) {
        propertyFileUtil = new PropertyFileUtil(generatorFilePath, PropertyFileConstant.GENERATOR_PROPERTIES);
    }


    /**
     * Initialize the property reading
     */
    public void initInfo() {
        APPLICATION_NAME = propertyFileUtil.getProperty(PropertyFileConstant.APPLICATION_NAME);
        APPLICATION_OUTPUT_DIRECTORY = propertyFileUtil.getProperty(PropertyFileConstant.APPLICATION_OUTPUT_DIRECTORY);

        GROUP_ID = propertyFileUtil.getProperty(PropertyFileConstant.GROUP_ID);
        ARTIFACT_ID = propertyFileUtil.getProperty(PropertyFileConstant.ARTIFACT_ID);
        DATABASE_NAME = propertyFileUtil.getProperty(PropertyFileConstant.DATABASE_NAME);
        APP_VERSION = propertyFileUtil.getProperty(PropertyFileConstant.APP_VERSION);
        SPRING_BOOT_VERSION = propertyFileUtil.getProperty(PropertyFileConstant.SPRING_BOOT_VERSION);
        JAVA_VERSION = propertyFileUtil.getProperty(PropertyFileConstant.JAVA_VERSION);

        PACKAGE_ID = GROUP_ID + "." + ARTIFACT_ID;
        DirectoryConfig.initInfo();

    }
}
