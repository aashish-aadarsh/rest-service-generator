package com.devop.aashish.utility;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 1/4/2019
 * <p>
 * This utility class is  used to get the various attributes while generating entity from json file
 */


public class AttributeHelper {

    /**
     * @param resourceName Name of resource
     * @return The entity name from resource file name
     */
    static String getEntityName(String resourceName) {
        if (null == resourceName || resourceName.trim().isEmpty()) {
            return "";
        }
        return String.valueOf(resourceName.charAt(0)).toUpperCase() + resourceName.substring(1);
    }

    /**
     * @param resourceName Name of resource
     * @return The parentId name in case of a json has list attributes and the mapping table needs
     * reference to parent table Id field name
     */
    static String getParentIdName(String resourceName) {
        if (null == resourceName || resourceName.trim().isEmpty()) {
            return "";
        }
        return String.valueOf(resourceName.charAt(0)).toLowerCase() + resourceName.substring(1)
                + "Id";
    }

    /**
     * @param resourceName Name of resource
     * @return The prefix to be appended in case a json has nested object class and the column generated
     * for nested class has to be start with parent resource name.
     */
    static String getPrefixName(String resourceName) {
        if (null == resourceName || resourceName.trim().isEmpty()) {
            return "";
        }
        return StringUtils.lowerCase(resourceName.trim()) + "_";
    }

    /**
     * @param resourceName Name of resource
     * @return The table name for a resource file.
     *
     * <eg>
     * if a resource file name is UserDemographicAddress,
     * the table name generated would be user_demographic_address
     * </eg>
     */
    static String getTableName(String resourceName) {
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
