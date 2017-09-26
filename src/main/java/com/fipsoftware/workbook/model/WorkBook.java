package com.fipsoftware.workbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkBook {

    private String SocialSecurity;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private byte[] photo;
    private Collection<WorkHistory> workHistories;


}
