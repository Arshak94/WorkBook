package com.fipsoftware.workbook.repository;

import com.fipsoftware.workbook.model.WorkBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface WorkBookRepository extends JpaRepository<WorkBook, Integer> {
}
