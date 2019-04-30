package com.devop.aashish.generator;

import com.devop.aashish.config.DirectoryConfig;
import com.devop.aashish.config.GeneratorConfig;
import com.devop.aashish.constant.PropertyFileConstant;
import com.devop.aashish.utility.EntityParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DomainGenerator {
    private static Logger logger = LoggerFactory.getLogger(DomainGenerator.class);


    public static void generateFiles() {
        generateDomain();
    }

    private static void generateDomain() {
        String inputDirectory = PropertyFileConstant.DIR_NAME_PROPERTY.
                concat(File.separator).concat(PropertyFileConstant.ENTITY_DIRECTORY);
        File entityDirectory = new File(inputDirectory);
        if (entityDirectory.exists() && entityDirectory.isDirectory()) {
            File[] files = entityDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    EntityParser.generateClass(file);
                }
            }
        } else {
            logger.warn("\nNo directory named entity present....");
        }


    }

}
