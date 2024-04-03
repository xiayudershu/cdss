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
public class AIResultData implements Serializable {


    private String docterid;
    private String patientid;
    private Integer degree;
    private float possible;
    private String hand;

}
