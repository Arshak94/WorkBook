package com.fipsoftware.workbook.converter;

import com.fipsoftware.workbook.model.WorkBook;
import com.fipsoftware.workbook.validator.WorkBookValidator;
import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WorkBookConverter {
    public static String convertWorkbookToXML(WorkBook workBook){
        XStream xStream = new XStream();
        String xml = xStream.toXML(workBook);
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            fw = new FileWriter("/Users/arshaktovmasyan/IdeaProjects/Workbook/test.xml");
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
        return xml;
    }
    public static WorkBook convertXMLToWorkbook(String xml){
        XStream xStream = new XStream();
        WorkBook workBook = (WorkBook)xStream.fromXML(xml);
        return workBook;
    }

    public List<WorkBook> getBiriqXml(String filepath){
        List<WorkBook> workBooks = new ArrayList<>();
        WorkBookValidator workBookValidator = new WorkBookValidator();
        String xml = new String();
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        File folder = new File(filepath);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++){
            String filename = listOfFiles[i].getName();
            if (filename.endsWith(".XML")||filename.endsWith(".xml")){
                try {
                        if (workBookValidator.validate(filepath+"/"+filename, "/Users/arshaktovmasyan/IdeaProjects/Workbook/workbook.xsd")) {
                            log.info("Valid xml");
                            fileReader = new FileReader(filepath + "/" + filename);
                            bufferedReader = new BufferedReader(fileReader);
                            String sCurrentLine;

                            while ((sCurrentLine = bufferedReader.readLine()) != null) {
                                xml += sCurrentLine;
                            }
                           workBooks.add(convertXMLToWorkbook(xml));
                        } else {
                            log.info("xml is not valid");
                        }
                } catch (IOException e) {

                    e.printStackTrace();

                } finally {

                    try {

                        if (bufferedReader != null)
                            bufferedReader.close();

                        if (fileReader != null)
                            fileReader.close();

                    } catch (IOException ex) {

                        ex.printStackTrace();

                    }

                }
            }
        }

        return workBooks;

    }
}
