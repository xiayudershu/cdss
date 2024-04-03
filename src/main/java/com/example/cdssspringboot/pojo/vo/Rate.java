package com.example.cdssspringboot.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rate implements Serializable {
    private String patientid;
    private String arthritis;
    private double rate;
    private List<Evidence> evidences;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Evidence implements Serializable{
        private String title;
        private String description;
    }



}
