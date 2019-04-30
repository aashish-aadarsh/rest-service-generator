package com.devop.aashish.generator;

import com.devop.aashish.constant.ApplicationConstant;
import com.devop.aashish.constant.TemplateFileConstant;
import com.devop.aashish.config.DirectoryConfig;
import com.devop.aashish.config.GeneratorConfig;
import com.devop.aashish.config.VelocityConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CoreGenerator {

    public static void generateFiles() {
        generatePOMFile();
        generateMainClass();
        generatePropertyFile();
    }


    private static void generatePOMFile() {
        String templateFileLocation = TemplateFileConstant.POM_FILE_LOCATION;
        String generatedFileName = TemplateFileConstant.POM_FILE_NAME;
        String generatedFileDirectory = DirectoryConfig.MAIN_DIRECTORY + File.separator + generatedFileName;
        Map<String, String> paramMap = getValues();
        new VelocityConfig().initWriting(paramMap, generatedFileDirectory, templateFileLocation);

    }

    private static void generateMainClass() {
        String templateFileLocation = TemplateFileConstant.APPLICATION_FILE_LOCATION;
        String generatedFileName = GeneratorConfig.APPLICATION_NAME + ApplicationConstant.EXTENSION_JAVA;
        String generatedFileDirectory = DirectoryConfig.PACKAGE_DIRECTORY + File.separator + generatedFileName;
        Map<String, String> paramMap = getValues();
        new VelocityConfig().initWriting(paramMap, generatedFileDirectory, templateFileLocation);
    }

    private static void generatePropertyFile() {
        String templateFileLocation = TemplateFileConstant.APPLICATION_PROPERTY_FILE_LOCATION;
        String generatedFileName = TemplateFileConstant.APPLICATION_PROPERTY_FILE_NAME;
        String generatedFileDirectory = DirectoryConfig.RESOURCES_DIRECTORY + File.separator + generatedFileName;
        Map<String, String> paramMap = getValues();
        new VelocityConfig().initWriting(paramMap, generatedFileDirectory, templateFileLocation);
    }

    private static Map<String, String> getValues() {
        Map<String, String> param = new HashMap<>();
        param.put(TemplateFileConstant.KEY_SPRING_BOOT_VERSION, GeneratorConfig.SPRING_BOOT_VERSION);
        param.put(TemplateFileConstant.KEY_GROUP_ID, GeneratorConfig.GROUP_ID);
        param.put(TemplateFileConstant.KEY_ARTIFACT_ID, GeneratorConfig.ARTIFACT_ID);
        param.put(TemplateFileConstant.KEY_APP_VERSION, GeneratorConfig.APP_VERSION);
        param.put(TemplateFileConstant.KEY_APPLICATION_NAME, GeneratorConfig.APPLICATION_NAME);
        param.put(TemplateFileConstant.KEY_JAVA_VERSION, GeneratorConfig.JAVA_VERSION);
        param.put(TemplateFileConstant.KEY_PACKAGE_ID, GeneratorConfig.PACKAGE_ID);
        param.put(TemplateFileConstant.KEY_DATABASE_NAME, GeneratorConfig.DATABASE_NAME);
        return param;
    }
}
