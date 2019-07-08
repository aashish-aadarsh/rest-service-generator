package com.devop.aashish.constant;

/**
 * @author : Aashish Aadarsh
 * Follow Me:  "https://github.com/aashish-aadarsh"
 * Created Date: 30/04/2019
 * <p>
 * This class is used to store the name of template file, and static classes.
 *
 * <B> !! Do Not Alter the values in this class. !! </B>
 */
public class TemplateFileConstant {

    public static final String KEY_SPRING_BOOT_VERSION = "SpringBootVersion";
    public static final String KEY_GROUP_ID = "GroupId";
    public static final String KEY_ARTIFACT_ID = "ArtifactId";
    public static final String KEY_APP_VERSION = "AppVersion";
    public static final String KEY_APPLICATION_NAME = "ApplicationName";
    public static final String KEY_JAVA_VERSION = "JavaVersion";
    public static final String KEY_PACKAGE_ID = "PackageId";
    public static final String KEY_DATABASE_NAME = "DatabaseName";

    public static final String KEY_RESOURCE_NAME_SINGULAR = "ResourceSingular";
    public static final String KEY_RESOURCE_NAME_PLURAL = "ResourcePlural";
    public static final String KEY_RESOURCE_NAME_SMALL_CASE = "ResourceSmallCase";
    public static final String KEY_RESOURCE_NAME_ALL_SMALL_CASE = "ResourceAllSmallCase";
    public static final String KEY_RESOURCE_NAME_API = "ResourceAPI";

    public static final String KEY_RESOURCE_SUB_DOMAIN_LOOP = "SubDomainList";
    public static final String KEY_ATTRIBUTE_HELPER = "AttributeHelper";
    public static final String KEY_ENABLE_HARD_DELETE = "EnableHardDelete";


    public static final String POM_FILE_LOCATION = "template/core/pom.vm";
    public static final String POM_FILE_NAME = "pom.xml";

    public static final String APPLICATION_FILE_LOCATION = "template/core/application_class.vm";

    public static final String APPLICATION_PROPERTY_FILE_LOCATION = "template/core/resource.vm";
    public static final String APPLICATION_PROPERTY_FILE_NAME = "application.yml";

    public static final String CONTROLLER_FILE_LOCATION = "template/component/controller.vm";
    public static final String SERVICE_FILE_LOCATION = "template/component/service.vm";
    public static final String SERVICE_IMPL_FILE_LOCATION = "template/component/service_impl.vm";
    public static final String REPO_FILE_LOCATION = "template/component/repo.vm";
}
