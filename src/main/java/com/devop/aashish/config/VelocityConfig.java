package com.devop.aashish.config;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 30/04/2019
 *
 * <p>
 * This utility class is  used to init velocity template
 * </p>
 */
public class VelocityConfig {
    private Logger logger = LoggerFactory.getLogger(VelocityConfig.class.getName());


    public void initWriting(Map<String, Object> paramMap, String generatedFileDirectory, String templateFileLocation) {
        initInfo();
        VelocityContext velocityContext = getVelocityContext(paramMap);
        writeFile(generatedFileDirectory, templateFileLocation, velocityContext);
    }


    /**
     * Initialize velocity config
     */
    private void initInfo() {
        Velocity.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        Velocity.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init();
    }

    /**
     * @param paramMap for velocity context
     * @return velocity context for provided Map
     */
    private VelocityContext getVelocityContext(Map<String, Object> paramMap) {
        VelocityContext context = new VelocityContext();
        paramMap.forEach(context::put);
        return context;
    }



    /**
     *
     * @param outputFileLocation where file is to be generated
     * @param templateFilePath the location of template file
     * @param context of velocity config
     */
    private void writeFile(String outputFileLocation, String templateFilePath, VelocityContext context) {
        logger.debug("Writing file...{},{},{}", outputFileLocation, templateFilePath, context.internalGetKeys());
        try {
            Writer writer = new FileWriter(new File(outputFileLocation));
            Velocity.mergeTemplate(templateFilePath, "UTF-8", context, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error("Unable to write file {},{},{}", outputFileLocation, templateFilePath, context.internalGetKeys());
            e.printStackTrace();
        }

    }
}
