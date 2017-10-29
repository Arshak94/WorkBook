package com.fipsoftware.workbook.service.converter;

import com.fipsoftware.workbook.model.WorkBook;
import com.fipsoftware.workbook.service.validator.WorkBookValidator;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.*;
import static com.fipsoftware.workbook.utils.Constants.FOLDER_PATH;
import static com.fipsoftware.workbook.utils.Constants.WORKBOOK_XSD_PATH;


@Slf4j
@Service
public class WorkBookConverterImpl implements WorkBookConverter {

    /**
     * Workbook validator
     */
    private WorkBookValidator workBookValidator;

    @Autowired
    public WorkBookConverterImpl(WorkBookValidator workBookValidator) {
        this.workBookValidator = workBookValidator;
    }


    /**
     * Writes given workbook object to file by given path
     * @param workBook
     * @param fileName
     */
    public void writeXML(WorkBook workBook, String fileName) {

        XStream xStream = new XStream();

        String xml = xStream.toXML(workBook);

        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(FOLDER_PATH + fileName + ".xml");
            bw = new BufferedWriter(fw);
            bw.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    /**
     * Read XML from file and convert to Workbook
     * @param filepath
     * @return
     */
    public WorkBook readXML(String filepath){
        BufferedReader bufferedReader = null;

        FileReader fileReader = null;

        String xml = new String();

        File file = new File(filepath);

        try {
            if(file.getName().toLowerCase().endsWith(".xml")){
                if (workBookValidator.validateXML(filepath, WORKBOOK_XSD_PATH)) {
                    log.info("valid Xml");
                    fileReader = new FileReader(filepath);
                    bufferedReader = new BufferedReader(fileReader);
                    String currentXMLLine;
                    while ((currentXMLLine = bufferedReader.readLine()) != null) {
                        xml += currentXMLLine;
                    }

                    XStream xStream = new XStream();
                    return (WorkBook) xStream.fromXML(xml);
                }else {
                    log.info("Xml is not valid");
                }


            }
        } catch (Exception e){
            System.err.print(e.getMessage());
        }
        return null;
    }

    /**
     * Write workbook into given file
     * @param workBook
     * @param fileName
     */
    public void writeJSON(WorkBook workBook, String fileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(FOLDER_PATH+fileName+".json"), workBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read File into WorkBook
     * @param filepath
     * @return
     */
    public WorkBook readJSON(String filepath) {
        ObjectMapper objectMapper = new ObjectMapper();
        WorkBook workBook = null;

        try{
            workBook = objectMapper.readValue(new File(filepath), WorkBook.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workBook;
    }

    private byte[] convertPhotoToByteArray(String ImagePath) {
        File file = new File(ImagePath);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                //Writes to this byte array output stream
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            System.err.print(ex);
        }

        byte[] bytes = bos.toByteArray();

        return bytes;
    }


}