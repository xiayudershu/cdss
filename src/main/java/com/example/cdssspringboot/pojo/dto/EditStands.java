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
public class EditStands implements Serializable {
    private String arthritis;
    private int age;
    private int sex;
    private int check;
    private int clinical;
    private String docterid;
}
