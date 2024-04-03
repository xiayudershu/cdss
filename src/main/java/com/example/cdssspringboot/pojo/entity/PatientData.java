package com.example.cdssspringboot.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientData implements Serializable {
    private String personalid;
    private String patientid;
    private String name;
    private int age;
    private String sex;
    private String date;
    private String history;
    private String docterid;
}
