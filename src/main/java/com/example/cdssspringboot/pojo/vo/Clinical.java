package com.example.cdssspringboot.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clinical implements Serializable {
    private String title;
    private boolean checked;
    private int label;
    private String description;
    private int score;
    private String arthritis;
}