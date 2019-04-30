package com.devop.aashish.utility;

import com.devop.aashish.constant.ApplicationConstant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 30/04/2019
 *
 * <p>
 * This utility class is  used to get the name of various android component.
 * </p>
 */
public class ComponentParser {

    public static String parseComponentName(String input) {
        return input.substring(1, input.length() - 1);
    }


    /**
     * @param resourceName Name of resource
     * @return The xml name for a resource file.
     *
     * <eg>
     * if a resource file name is UserDemographicAddress,
     * the table name generated would be user_demographic_address
     * </eg>
     */
    public static String getPascalCaseName(String resourceName) {
        if (null == resourceName || resourceName.trim().isEmpty()) {
            return "";
        }
        String lowerCasedResource = String.valueOf(resourceName.charAt(0)).toLowerCase()
                + resourceName.substring(1);
        Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(lowerCasedResource);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "_" + m.group().toLowerCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }
}
