package com.fipsoftware.workbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workbook")
public class WorkBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "social_security")
    private String SocialSecurity;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @Column(name = "date_of_birth")
    @NotNull
    private Date dateOfBirth;

    @Lob
    @Column(name = "photo", nullable=false, columnDefinition="mediumblob")
    private byte[] photo;

    @Transient
    private Collection<WorkHistory> workHistory;
}
