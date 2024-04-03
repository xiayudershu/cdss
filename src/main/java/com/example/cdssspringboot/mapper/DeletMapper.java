package com.example.cdssspringboot.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeletMapper {

    @Delete("delete from clinial_oa where label=#{label} and docterid=#{docterid}")
    void deleteClinialOa(int label, String docterid);

    @Delete("delete from clinial_ra where label=#{label} and docterid=#{docterid}")
    void deleteClinialRa(int label, String docterid);

    @Delete("delete from checked_x_oa where label=#{label} and docterid=#{docterid}")
    void deleteCheckedXOa(int label, String docterid);

    @Delete("delete from checked_x_ra where label=#{label} and docterid=#{docterid}")
    void deleteCheckedXRa(int label, String docterid);

    @Delete("delete from checked_x_oa_ai where patientid=#{patientid} and docterid=#{docterid}")
    void deleteOaAI(String patientid, String docterid);

    @Delete("delete from checked_x_ra_ai where patientid=#{patientid} and docterid=#{docterid}")
    void deleteRaAI(String patientid, String docterid);
}
