package com.devop.aashish.utility;

import com.devop.aashish.config.DirectoryConfig;
import com.devop.aashish.config.GeneratorConfig;
import com.devop.aashish.constant.ApplicationConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.*;
import java.util.*;

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
                return true;
            }

            @Override
            public boolean isIncludeSetters() {
                return true;
            }

            @Override
            public boolean isIncludeAdditionalProperties() {
                return false;
            }

            @Override
            public AnnotationStyle getAnnotationStyle() {
                return AnnotationStyle.NONE;
            }

        };

        try {
            SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new CustomAnnotator(),
                    new SchemaStore()), new SchemaGenerator());
            mapper.generate(codeModel, className, packageName, source.toURI().toURL());
            codeModel.build(outputFileDirectory);
            if (Boolean.valueOf(GeneratorConfig.GENERATE_SUB_RESOURCE))
                addSubDomain(codeModel, className, source);
            updateAllEntity(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateAllEntity(String className) {
        String fileLocation = DirectoryConfig.PACKAGE_DIRECTORY +
                File.separator + PathUtil.getPathFromPackageName(ApplicationConstant.PACKAGE_DOMAIN + "." + className.toLowerCase());
        File entityDirectory = new File(fileLocation);
        if (entityDirectory.exists() && entityDirectory.isDirectory()) {
            try {
                Arrays.asList(Objects.requireNonNull(entityDirectory.listFiles())).forEach(file -> {
                    try {
                        if (file.isFile()) {
                            String superClassName = "BaseEntity";
                            if (file.getName().toLowerCase().replaceAll(".java", "").equalsIgnoreCase(className)) {
                                superClassName = "BaseTransactionalEntity";
                            }
                            FileReader fr = new FileReader(file);
                            StringBuilder totalStr = new StringBuilder();
                            BufferedReader reader = new BufferedReader(fr);
                            String line = reader.readLine();
                            updateWithSuperClass(superClassName, totalStr, reader, line);
                            FileWriter fw = new FileWriter(file);
                            fw.write(totalStr.toString());
                            fw.close();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void updateWithSuperClass(String superClassName, StringBuilder totalStr, BufferedReader reader, String line) throws IOException {
        while (line != null) {
            if (line.startsWith("package " + GeneratorConfig.PACKAGE_ID)) {
                totalStr.append(line).append(System.lineSeparator());
                totalStr.append("import ").append(GeneratorConfig.PACKAGE_ID).
                        append(".").append(ApplicationConstant.PACKAGE_DOMAIN)
                        .append(".*;").append(System.lineSeparator());
            } else if (line.startsWith("public class")) {
                line = line.substring(0, line.length() - 1) + "extends " + superClassName + "{ ";
                totalStr.append(line).append(System.lineSeparator());
            } else {
                totalStr.append(line).append(System.lineSeparator());
            }
            line = reader.readLine();
        }
    }

    private static void addSubDomain(JCodeModel codeModel, String parentDomainEntity, File source)
            throws IOException, ParseException {

        Set<String> arraySets = new HashSet<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.
                parse(new FileReader(source));
        jsonObject.forEach((key, value) -> {
            if (value instanceof JSONArray) {
                arraySets.add(key.toString().toLowerCase());
            }
        });

        List<String> subDomainEntity = new ArrayList<>();
        codeModel.packages().forEachRemaining(jPackage -> jPackage.classes().forEachRemaining(jDefinedClass -> {
            if (!jDefinedClass.name().equalsIgnoreCase(parentDomainEntity)) {
                if (arraySets.contains(jDefinedClass.name().toLowerCase())) {
                    subDomainEntity.add(jDefinedClass.name());
                } else if (arraySets.contains(jDefinedClass.name().toLowerCase() + "s")) {
                    subDomainEntity.add(jDefinedClass.name() + "<<!!>>" + jDefinedClass.name() + "s");
                } else if (arraySets.contains(jDefinedClass.name().toLowerCase() + "list")) {
                    subDomainEntity.add(jDefinedClass.name() + "<<!!>>" + jDefinedClass.name() + "List");

                }

            }
        }));

        GeneratorConfig.SUB_DOMAIN_SET.put(parentDomainEntity, subDomainEntity);
    }

    private static class CustomAnnotator extends AbstractAnnotator {

        @Override
        public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
            String className = clazz.name().toLowerCase();
            String parentContainerName = ((JPackage) clazz.parentContainer()).name().toLowerCase();
            //clazz.annotate(Getter.class);
            //clazz.annotate(Setter.class);
            clazz.annotate(Builder.class);
            clazz.annotate(ToString.class);
            clazz.annotate(NoArgsConstructor.class);
            clazz.annotate(AllArgsConstructor.class);


            if (parentContainerName.endsWith(className)) {
                clazz.annotate(Document.class).param("collection", AttributeHelper.getDocumentName(clazz.name()));
                clazz.annotate(JsonInclude.class).param("value", JsonInclude.Include.NON_NULL);
            }

        }
    }
}
