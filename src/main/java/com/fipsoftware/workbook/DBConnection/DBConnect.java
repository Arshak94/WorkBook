package com.fipsoftware.workbook.DBConnection;

import com.fipsoftware.workbook.model.WorkBook;

import java.util.List;

public interface DBConnect {
    public void save(WorkBook workBook);
    public List<WorkBook> findAll();
}
