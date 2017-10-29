package com.fipsoftware.workbook.Threads;

import com.fipsoftware.workbook.DBConnection.DBConnect;
import com.fipsoftware.workbook.model.WorkBook;
import com.fipsoftware.workbook.service.converter.WorkBookConverter;

public class ProcessFile implements Runnable {
    private WorkBookConverter workBookConverter;

    private DBConnect dbConnect;


    private String filepath;

    public ProcessFile(String filepath, WorkBookConverter workBookConverter, DBConnect dbConnect){
        this.filepath = filepath;
        this.workBookConverter = workBookConverter;
        this.dbConnect = dbConnect;
    }

    public void convertAndSave(){

        WorkBook workBook = null;
        if(this.filepath.toLowerCase().endsWith("xml"))
        {
            workBook = this.workBookConverter.readXML(this.filepath);
        }
        else if(this.filepath.toLowerCase().endsWith("json"))
        {
            workBook = this.workBookConverter.readJSON(this.filepath);
        }

        if(workBook == null)
        {
            return;
        }

        this.dbConnect.save(workBook);

    }

    @Override
    public void run() {
        convertAndSave();
    }
}
