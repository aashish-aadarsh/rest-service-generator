package com.devop.aashish.utility;

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
     * @return The documentName name for a resource file.
     *
     * <eg>
     * if a resource file name is UserDemographicAddress,
     * the table name generated would be user_demographic_address
     * </eg>
     */
    static String getDocumentName(String resourceName) {
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

    /**
     * @param resourceName Name of resource
     * @return The controller name for a resource file.
     *
     *
     * if a resource file name is UserDemographicAddress,
     * the controller name generated would be UserDemographicAddressController
     *
     */
    public static String getControllerName(String resourceName) {
        if (null == resourceName || resourceName.trim().isEmpty()) {
            return "Controller";
        }
        return String.valueOf(resourceName.charAt(0)).toUpperCase() + resourceName.substring(1) + "Controller";
    }

    public static String getSingularResource(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "Domain";
        }
        return String.valueOf(domain.charAt(0)).toUpperCase() + domain.substring(1);
    }

    public static String getPluralResource(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "Domain";
        }
        if (domain.endsWith("y")) {
            return String.valueOf(domain.charAt(0)).toUpperCase() + domain.substring(1, domain.length() - 1) + "ies";
        }
        return String.valueOf(domain.charAt(0)).toUpperCase() + domain.substring(1);
    }

    public static String getResourceSmallCase(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "domain";
        }
        return String.valueOf(domain.charAt(0)).toLowerCase() + domain.substring(1);
    }

    public static String getResourceAllSmallCase(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "domain";
        }
        return domain.toLowerCase();
    }

    public static String getResourceNameAPI(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "Domain";
        }
        if (domain.endsWith("y")) {
            return String.valueOf(domain.charAt(0)).toLowerCase() + domain.substring(1, domain.length() - 1) + "ies";
        }
        return String.valueOf(domain.charAt(0)).toLowerCase() + domain.substring(1) + "s";
    }

    public static String getServiceName(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "Service";
        }
        return String.valueOf(domain.charAt(0)).toUpperCase() + domain.substring(1) + "Service";
    }

    public static String getServiceImplName(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "ServiceImpl";
        }
        return String.valueOf(domain.charAt(0)).toUpperCase() + domain.substring(1) + "ServiceImpl";
    }

    public static String getRepositoryName(String domain) {
        if (null == domain || domain.trim().isEmpty()) {
            return "Repository";
        }
        return String.valueOf(domain.charAt(0)).toUpperCase() + domain.substring(1) + "Repository";
    }


    public static String getResourceNameSubDomain(String subDomain) {
        if (null == subDomain || subDomain.trim().isEmpty()) {
            return "SubDomain";
        }
        return String.valueOf(subDomain.charAt(0)).toUpperCase() + subDomain.substring(1);
    }

    public static String getResourceNameSubDomainSingular(String subDomain) {
        if (null == subDomain || subDomain.trim().isEmpty()) {
            return "subDomain";
        }
        return String.valueOf(subDomain.charAt(0)).toLowerCase() + subDomain.substring(1);
    }

    public static String getSubDomainName(String subDomain) {
        if (null == subDomain || subDomain.trim().isEmpty()) {
            return "subDomain";
        }
        if (subDomain.split("<<!!>>").length == 2) {
            return subDomain.split("<<!!>>")[0];
        }
        return subDomain;
    }

    public static String getResourceNameSubDomainGetter(String subDomain) {
        if (null == subDomain || subDomain.trim().isEmpty()) {
            return "subDomain";
        }
        if (subDomain.split("<<!!>>").length == 2) {
            String key = subDomain.split("<<!!>>")[1];
            return getResourceNameSubDomain(key);
        }
        return getResourceNameSubDomain(subDomain);
    }


}
