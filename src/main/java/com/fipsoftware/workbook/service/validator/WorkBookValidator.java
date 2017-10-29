package com.fipsoftware.workbook.service.validator;

public interface WorkBookValidator {
    /**
     * Validates XML against XSD
     * @param xmlFile
     * @param schemaFile
     * @return
     */
    boolean validateXML(String xmlFile, String schemaFile);
}
