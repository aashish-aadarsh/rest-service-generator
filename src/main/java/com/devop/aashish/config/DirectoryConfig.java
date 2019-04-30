package com.devop.aashish.config;

import com.devop.aashish.constant.ApplicationConstant;
import com.devop.aashish.utility.PathUtil;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 30/04/2019
 *
 * <p>
 * This utility class is  used to get the name of various android directory path.
 * </p>
 */
public class DirectoryConfig {

    public static String APP_DIRECTORY;
    public static String SRC_DIRECTORY;
    public static String MAIN_DIRECTORY;
    public static String TEST_DIRECTORY;
    public static String JAVA_DIRECTORY;
    public static String RESOURCES_DIRECTORY;

    public static String PACKAGE_DIRECTORY;

    /**
     * Initialize Directory Structure
     */
    static void initInfo() {

        APP_DIRECTORY = PathUtil.subDirectory(GeneratorConfig.APPLICATION_OUTPUT_DIRECTORY,
                GeneratorConfig.APPLICATION_NAME);

        SRC_DIRECTORY = PathUtil.subDirectory(APP_DIRECTORY,
                ApplicationConstant.DIR_SRC);

        MAIN_DIRECTORY = PathUtil.subDirectory(SRC_DIRECTORY,
                ApplicationConstant.DIR_MAIN);

        TEST_DIRECTORY = PathUtil.subDirectory(SRC_DIRECTORY,
                ApplicationConstant.DIR_TEST);

        JAVA_DIRECTORY = PathUtil.subDirectory(MAIN_DIRECTORY,
                ApplicationConstant.DIR_JAVA);

        RESOURCES_DIRECTORY = PathUtil.subDirectory(MAIN_DIRECTORY,
                ApplicationConstant.DIR_RESOURCES);

        PACKAGE_DIRECTORY = PathUtil.subDirectory(JAVA_DIRECTORY,
                PathUtil.getPathFromPackageName(GeneratorConfig.GROUP_ID + "." + GeneratorConfig.ARTIFACT_ID.toLowerCase()));

    }


}
