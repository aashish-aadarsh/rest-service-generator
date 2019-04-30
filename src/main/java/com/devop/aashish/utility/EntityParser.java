package com.devop.aashish.utility;

import com.devop.aashish.config.DirectoryConfig;
import com.devop.aashish.config.GeneratorConfig;
import com.devop.aashish.constant.ApplicationConstant;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JPackage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.function.BiConsumer;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class EntityParser {

    public static void generateClass(File source) {
        String className = source.getName().replaceAll(".json", "");
        GeneratorConfig.DOMAIN_SET.add(className);
        String packageName = GeneratorConfig.PACKAGE_ID + "." +
                ApplicationConstant.PACKAGE_DOMAIN + "." + className.toLowerCase();
        String packageDirectory = DirectoryConfig.JAVA_DIRECTORY;
        File outputFileDirectory = new File(packageDirectory);
        outputFileDirectory.mkdirs();
        JCodeModel codeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return false;
            }

            public SourceType getSourceType() {
                return SourceType.JSON;
            }

            @Override
            public boolean isIncludeHashcodeAndEquals() {
                return false;
            }

            @Override
            public boolean isIncludeToString() {
                return false;
            }

            @Override
            public boolean isIncludeGetters() {
                return false;
            }

            @Override
            public boolean isIncludeSetters() {
                return false;
            }

            @Override
            public boolean isIncludeAdditionalProperties() {
                return false;
            }


        };
        try {
            SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new CustomAnnotator(),
                    new SchemaStore()), new SchemaGenerator());
            mapper.generate(codeModel, className, packageName, source.toURI().toURL());
            codeModel.build(outputFileDirectory);
            updateEntity(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateEntity(String fileName) {

        String fileLocation = DirectoryConfig.PACKAGE_DIRECTORY +
                File.separator + PathUtil.getPathFromPackageName(ApplicationConstant.PACKAGE_DOMAIN + "." + fileName.toLowerCase()) +
                File.separator + fileName + ApplicationConstant.EXTENSION_JAVA;
        File file = new File(fileLocation);
        try {
            FileReader fr = new FileReader(file);
            String s;
            StringBuilder totalStr = new StringBuilder();
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("package " + GeneratorConfig.PACKAGE_ID)) {
                    totalStr.append(line).append(System.lineSeparator());
                    totalStr.append("import ").append(GeneratorConfig.PACKAGE_ID).
                            append(".").append(ApplicationConstant.PACKAGE_DOMAIN)
                            .append(".").append("BaseEntity;").append(System.lineSeparator());
                } else if (line.startsWith("public class")) {
                    line = line.substring(0, line.length() - 1) + "extends BaseEntity { ";
                    totalStr.append(line).append(System.lineSeparator());
                }else {
                    totalStr.append(line).append(System.lineSeparator());
                }
                line = reader.readLine();
            }
            FileWriter fw = new FileWriter(file);
            fw.write(totalStr.toString());
            fw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static class CustomAnnotator extends AbstractAnnotator {

        @Override
        public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
            String className = clazz.name().toLowerCase();
            String parentContainerName = ((JPackage) clazz.parentContainer()).name().toLowerCase();
            clazz.annotate(Getter.class);
            clazz.annotate(Setter.class);
            clazz.annotate(Builder.class);
            clazz.annotate(ToString.class);
            if (parentContainerName.endsWith(className)) {
                clazz.annotate(Document.class).param("collection", AttributeHelper.getDocumentName(clazz.name()));
            }

        }
    }
}
