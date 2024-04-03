package com.example.cdssspringboot.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultData implements Serializable {
    private int clinialid;
    private String patientid;
    private String docterid;
    private double oarate;
    private double rarate;
    private LocalDate date;
}
