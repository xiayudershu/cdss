package com.example.cdssspringboot.pojo.dto;

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
public class AIResult implements Serializable {
    private String docterid;
    private String patientid;
    private AIdata[] aidata;
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AIdata implements Serializable {
        private Integer degree;
        private float possible;
        private String hand;

    }
}
