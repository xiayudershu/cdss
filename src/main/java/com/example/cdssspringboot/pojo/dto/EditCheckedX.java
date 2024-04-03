package com.example.cdssspringboot.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditCheckedX implements Serializable {
    private int label;
    private String description;
    private int score;
    private String arthritis;
    private String docterid;

}
