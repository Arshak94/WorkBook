package com.fipsoftware.workbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "work_history")
public class WorkHistory {
    @Id
    private int id;

    @Column(name = "work_book_id")
    private int workBookId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "job_position")
    private String position;

    @Column(name = "salary")
    @Min(1)
    private BigDecimal salary;

    @Column(name = "work_from")
    @NotNull
    private Date workFrom;

    @Column(name = "work_until")
    private Date workUntil;

}
