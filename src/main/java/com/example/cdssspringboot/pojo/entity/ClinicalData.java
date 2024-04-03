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
public class ClinicalData implements Serializable {
    private String title;
    private int label;
    private String description;
    private int score;
    private String docterid;
}
