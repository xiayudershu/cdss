package com.example.cdssspringboot.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocterResult implements Serializable {
    private int clinialid;
    private String name;
    private double oarate;
    private double rarate;
    private LocalDate date;

}
