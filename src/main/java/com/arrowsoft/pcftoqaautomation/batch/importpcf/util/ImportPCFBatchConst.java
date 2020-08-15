package com.arrowsoft.pcftoqaautomation.batch.importpcf.util;

import java.io.File;

public class ImportPCFBatchConst {

    private static final String ROOT_CONFIG_PATH = File.separator + "configuration" + File.separator + "config" + File.separator;

    public static final String PCF_IMPORT_STEP = "PCFToDBBatchStep";
    public static final String TYPECODE_METADATA_IMPORT_STEP = "TypeCodeToDBBatchMetadataStep";
    public static final String TYPECODE_EXTENSION_IMPORT_STEP = "TypeCodeToDBBatchExtensionStep";
    public static final String GENERATE_ENUMS_STEP = "GenerateEnumsStep";

    public static final String PCF_PATH = ROOT_CONFIG_PATH + "web" + File.separator + "pcf";
    public static final String TYPECODE_METADATA_PATH = ROOT_CONFIG_PATH + "metadata" + File.separator + "typelist";
    public static final String TYPECODE_EXTENSION_PATH = ROOT_CONFIG_PATH + "extensions" + File.separator + "typelist";

    public static final String[] PCF_EXTENSION_FILES = new String[]{"pcf"};
    public static final String[] TYPECODE_EXTENSION_FILES_META = new String[]{"tti"};
    public static final String[] TYPECODE_EXTENSION_FILES_EXT = new String[]{"tti", "ttx", "tix"};

    public static final String ELEMENT_TYPECODE_TYPE = "typecode";
    public static final String ELEMENT_TYPECODE_CODE = "code";

}
