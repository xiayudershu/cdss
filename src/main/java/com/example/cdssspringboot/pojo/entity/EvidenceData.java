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
public class EvidenceData implements Serializable {
    private int clinialid;
    private String title;
    private String description;
    private String arthritis;
    private double score;
}
