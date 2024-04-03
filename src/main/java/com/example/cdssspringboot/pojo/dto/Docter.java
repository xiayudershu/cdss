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
public class Docter implements Serializable {
    private String name;
    private String docterid;
    private String personalid;
    private  String sex;
    private String password;

}
