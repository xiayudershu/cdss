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
public class SelectInfo implements Serializable {

    private Info info;
    private List<String> alldescription;
    private List<String> keypoint;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info implements Serializable{
        private String name;
        private String patientid;
        private String personalid;
        private String docterid;
    }


}
