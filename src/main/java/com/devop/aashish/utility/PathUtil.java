package com.devop.aashish.utility;


import com.devop.aashish.constant.ApplicationConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 30/04/2019
 * <p>
 * This utility class is  used to get the various path directory structure of java app
 */

public class PathUtil {

    public static String subDirectory(String prevPath, String newDirectoryName) {
        return prevPath + File.separator + newDirectoryName;
    }

    public static String getPathFromPackageName(String packageName) {
        return packageName.replace(".", File.separator);
    }

}

