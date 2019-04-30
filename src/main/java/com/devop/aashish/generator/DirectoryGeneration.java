package com.devop.aashish.generator;

import com.devop.aashish.constant.PropertyFileConstant;
import com.devop.aashish.parser.DirectoryConfig;
import com.devop.aashish.utility.FileHelper;

import java.io.IOException;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 30//2019
 *
 * <p>
 * Creates package skeleton
 * </p>
 */
public class DirectoryGeneration {


    public static void createDirectorySkeleton() throws IOException {
        FileHelper.createDirectories(
                DirectoryConfig.APP_DIRECTORY,
                DirectoryConfig.SRC_DIRECTORY,
                DirectoryConfig.MAIN_DIRECTORY,
                DirectoryConfig.TEST_DIRECTORY,
                DirectoryConfig.JAVA_DIRECTORY,
                DirectoryConfig.RESOURCES_DIRECTORY,
                DirectoryConfig.PACKAGE_DIRECTORY);

        FileHelper.copyResources(DirectoryConfig.PACKAGE_DIRECTORY, PropertyFileConstant.STATIC_RESOURCE);

        FileHelper.searchAndReplace(DirectoryConfig.PACKAGE_DIRECTORY);
    }

}
