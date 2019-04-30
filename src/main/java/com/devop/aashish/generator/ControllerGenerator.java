package com.devop.aashish.generator;

import com.devop.aashish.config.DirectoryConfig;
import com.devop.aashish.config.GeneratorConfig;
import com.devop.aashish.config.VelocityConfig;
import com.devop.aashish.constant.ApplicationConstant;
import com.devop.aashish.constant.TemplateFileConstant;
import com.devop.aashish.utility.AttributeHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ControllerGenerator {

    public static void generateFiles() {
        generateController();
    }


    private static void generateController() {
        String templateFileLocation = TemplateFileConstant.CONTROLLER_FILE_LOCATION;
        GeneratorConfig.DOMAIN_SET.forEach(domain -> {
            String generatedFileName = AttributeHelper.getControllerName(domain) + ApplicationConstant.EXTENSION_JAVA;
            String generatedFileDirectory = DirectoryConfig.PACKAGE_DIRECTORY + File.separator +
                    ApplicationConstant.PACKAGE_CONTROLLER + File.separator + generatedFileName;
            Map<String, String> paramMap = getValues(domain);
            new VelocityConfig().initWriting(paramMap, generatedFileDirectory, templateFileLocation);
        });
    }


    private static Map<String, String> getValues(String domain) {
        Map<String, String> param = new HashMap<>();
        param.put(TemplateFileConstant.KEY_PACKAGE_ID, GeneratorConfig.PACKAGE_ID);
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_SINGULAR, AttributeHelper.getSingularResource(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_PLURAL, AttributeHelper.getPluralResource(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_SMALL_CASE, AttributeHelper.getResourceSmallCase(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_ALL_SMALL_CASE, AttributeHelper.getResourceAllSmallCase(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_API, AttributeHelper.getResourceNameAPI(domain));
        return param;
    }
}
