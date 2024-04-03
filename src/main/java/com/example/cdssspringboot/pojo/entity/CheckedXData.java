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
public class CheckedXData implements Serializable{
    private int label;
    private String description;
    private int score;
    private String docterid;
}
