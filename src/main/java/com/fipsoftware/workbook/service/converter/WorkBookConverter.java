package com.fipsoftware.workbook.service.converter;

import com.fipsoftware.workbook.model.WorkBook;


public interface WorkBookConverter {

    /**
     * Reads XML file and converts to WorkBook
     * @param filePath
     * @return
     */
    WorkBook readXML(String filePath);

    /**
     * Reads JSON file and converts to WorkBook
     * @param filePath
     * @return
     */
    WorkBook readJSON(String filePath);

    /**
     * Write given workbook to XML file
     * @param workBook
     * @param filePath
     */
    void writeXML(WorkBook workBook, String filePath);


    /**
     * Write given Workbook in JSON file
     * @param workBook
     * @param filePath
     */
    void writeJSON(WorkBook workBook, String filePath);

}
