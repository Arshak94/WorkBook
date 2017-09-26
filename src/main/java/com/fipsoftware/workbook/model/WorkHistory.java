package com.fipsoftware.workbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkHistory {
    private String companyName;
    private String position;
    private BigDecimal salary;
    private Date workFrom;
    private Date workUntil;

}
