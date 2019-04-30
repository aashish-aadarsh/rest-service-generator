package com.devop.aashish.utility;

import com.devop.aashish.config.DirectoryConfig;
import com.devop.aashish.config.GeneratorConfig;
import com.devop.aashish.constant.ApplicationConstant;
import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JPackage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;

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
        } catch (Exception e) {
            e.printStackTrace();
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
