package com.arrowsoft.pcftoqaautomation.batch.importpcf.util;

public class ImportPCFBatchConst {

    public static final String PCF_IMPORT_STEP = "PCFToDBBatchStep";
    public static final String TYPECODE_METADATA_IMPORT_STEP = "TypeCodeToDBBatchMetadataStep";
    public static final String TYPECODE_EXTENSION_IMPORT_STEP = "TypeCodeToDBBatchExtensionStep";

    public static final String PCF_PATH = "\\configuration\\config\\web\\pcf";
    public static final String TYPECODE_METADATA_PATH = "\\configuration\\config\\metadata\\typelist";
    public static final String TYPECODE_EXTENSION_PATH = "\\configuration\\config\\extensions\\typelist";

    public static final String[] PCF_EXTENSION_FILES = new String[]{"pcf"};
    public static final String[] TYPECODE_EXTENSION_FILES_META = new String[]{"tti"};
    public static final String[] TYPECODE_EXTENSION_FILES_EXT = new String[]{"tti", "ttx", "tix"};

    public static final String ELEMENT_TYPECODE_TYPE = "typecode";
    public static final String ELEMENT_TYPECODE_CODE = "code";

}
