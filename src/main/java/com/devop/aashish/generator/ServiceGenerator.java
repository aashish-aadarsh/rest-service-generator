package com.devop.aashish.generator;

import com.devop.aashish.config.DirectoryConfig;
import com.devop.aashish.config.GeneratorConfig;
import com.devop.aashish.config.VelocityConfig;
import com.devop.aashish.constant.ApplicationConstant;
import com.devop.aashish.constant.TemplateFileConstant;
import com.devop.aashish.utility.AttributeHelper;
import com.devop.aashish.utility.PathUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ServiceGenerator {

    public static void generateFiles() {

        generateService();
        generateServiceImpl();
    }


    private static void generateService() {
        String templateFileLocation = TemplateFileConstant.SERVICE_FILE_LOCATION;
        GeneratorConfig.DOMAIN_SET.forEach(domain -> {
            String generatedFileName = AttributeHelper.getServiceName(domain) + ApplicationConstant.EXTENSION_JAVA;
            String generatedFileDirectory = DirectoryConfig.PACKAGE_DIRECTORY + File.separator +
                    PathUtil.getPathFromPackageName(ApplicationConstant.PACKAGE_SERVICE) + File.separator + generatedFileName;
            new VelocityConfig().initWriting(getValues(domain), generatedFileDirectory, templateFileLocation);
        });
    }

    private static void generateServiceImpl() {
        String templateFileLocation = TemplateFileConstant.SERVICE_IMPL_FILE_LOCATION;
        GeneratorConfig.DOMAIN_SET.forEach(domain -> {
            String generatedFileName = AttributeHelper.getServiceImplName(domain) + ApplicationConstant.EXTENSION_JAVA;
            String generatedFileDirectory = DirectoryConfig.PACKAGE_DIRECTORY + File.separator +
                    PathUtil.getPathFromPackageName(ApplicationConstant.PACKAGE_SERVICE_IMPL) + File.separator + generatedFileName;
            new VelocityConfig().initWriting(getValues(domain), generatedFileDirectory, templateFileLocation);
        });
    }


    private static Map<String, Object> getValues(String domain) {
        Map<String, Object> param = new HashMap<>();
        param.put(TemplateFileConstant.KEY_PACKAGE_ID, GeneratorConfig.PACKAGE_ID);
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_SINGULAR, AttributeHelper.getSingularResource(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_PLURAL, AttributeHelper.getPluralResource(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_SMALL_CASE, AttributeHelper.getResourceSmallCase(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_ALL_SMALL_CASE, AttributeHelper.getResourceAllSmallCase(domain));
        param.put(TemplateFileConstant.KEY_RESOURCE_NAME_API, AttributeHelper.getResourceNameAPI(domain));

        param.put(TemplateFileConstant.KEY_RESOURCE_SUB_DOMAIN_LOOP, GeneratorConfig.SUB_DOMAIN_SET.get(domain));
        param.put(TemplateFileConstant.KEY_ATTRIBUTE_HELPER, new AttributeHelper());
        param.put(TemplateFileConstant.KEY_ENABLE_HARD_DELETE, Boolean.valueOf(GeneratorConfig.ENABLE_HARD_DELETE));

        return param;
    }
}
