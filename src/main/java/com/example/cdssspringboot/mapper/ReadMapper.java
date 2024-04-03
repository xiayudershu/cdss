package com.example.cdssspringboot.mapper;

import com.example.cdssspringboot.pojo.entity.*;
import com.example.cdssspringboot.pojo.vo.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReadMapper {
    @Select("select * from patient where docterid=#{docterid}")
    PatientData[] readFromPatient(String docterid);

    @Select("select * from clinial_oa where docterid=#{docterid}")
    ClinicalData[] readFromClinialOa(String docterid);

    @Select("select * from clinial_ra where docterid=#{docterid}")
    ClinicalData[] readFromClinialRa(String docterid);

    @Select("select * from checked_x_oa where docterid=#{docterid}")
    CheckedXData[] readFromCheckedXOa(String docterid);

    @Select("select * from checked_x_ra where docterid=#{docterid}")
    CheckedXData[] readFromCheckedXRa(String docterid);

    @Select("select * from stands where arthritis=#{arthritis} and docterid=#{docterid}")
    StandsData readFromStands(String arthritis,String docterid);

    @Select("select * from clinial_evidence where clinialid=#{clinialid} and arthritis=#{arthritis}")
    EvidenceData[] readFromEvidence(int clinialid,String arthritis);

    @Select("select * from clinial_result where clinialid=#{clinialid}")
    ResultData readFromResult(int clinialid);

    @Select("SELECT MAX(clinialid) AS clinialid " +
            "FROM clinial_result")
    int getCinialid();

    @Select("select * from docter where docterid=#{docterid}")
    DocterData ifDocterid(String docterid);

    @Select("SELECT MAX(label) AS label " +
            "FROM clinial_oa " +
            "where docterid=#{docterid}")
    int getCiniallabelOa(String docterid);

    @Select("SELECT MAX(label) AS label " +
            "FROM clinial_ra " +
            "where docterid=#{docterid}")
    int getCiniallabelRa(String docterid);

    @Select("SELECT MAX(label) AS label " +
            "FROM checked_x_oa " +
            "where docterid=#{docterid}")
    int getCheckedXlabelOa(String docterid);

    @Select("SELECT MAX(label) AS label " +
            "FROM checked_x_ra " +
            "where docterid=#{docterid}")
    int getCheckedXlabelRa(String docterid);

    @Select("select * from clinial_result where docterid=#{docterid}")
    ResultData[] readDocterResult(String docterid);

    @Select("select * from clinial_result where patientid=#{patientid}")
    ResultData[] readPatientResult(String patientid);

    @Select("select name from patient where patientid=#{patientid} and docterid=#{docterid}")
    String findPatientName(String patientid,String docterid);

    @Select("select name from docter where docterid=#{docterid}")
    String findDocterName(String docterid);

    @Select("select * from checked_x_oa_ai where patientid=#{patientid} and docterid=#{docterid}")
    AIResultData[] readAIResultOa(String patientid,String docterid);

    @Select("select * from checked_x_ra_ai where patientid=#{patientid} and docterid=#{docterid}")
    AIResultData[] readAIResultRa(String patientid,String docterid);


    @Select("SELECT AVG(possible) AS possible FROM checked_x_oa_ai WHERE patientid = #{patientid} AND docterid = #{docterid} AND degree = #{degree}")
    Double readAIOaSeverAverage(@Param("patientid") String patientid, @Param("docterid") String docterid, @Param("degree") Integer degree);

    @Select("SELECT AVG(possible) AS possible FROM checked_x_oa_ai WHERE patientid = #{patientid} AND docterid = #{docterid} AND degree = #{degree}")
    Double readAIOaMildAverage(@Param("patientid") String patientid, @Param("docterid") String docterid, @Param("degree") Integer degree);

    @Select("SELECT AVG(possible) AS possible FROM checked_x_oa_ai WHERE patientid = #{patientid} AND docterid = #{docterid} AND degree = #{degree}")
    Double readAIOaNormalAverage(@Param("patientid") String patientid, @Param("docterid") String docterid, @Param("degree") Integer degree);



    @Select("SELECT AVG(possible) AS possible FROM checked_x_ra_ai WHERE patientid = #{patientid} AND docterid = #{docterid} AND degree = #{degree}")
    Double readAIRaSeverAverage(@Param("patientid") String patientid, @Param("docterid") String docterid, @Param("degree") Integer degree);

    @Select("SELECT AVG(possible) AS possible FROM checked_x_ra_ai WHERE patientid = #{patientid} AND docterid = #{docterid} AND degree = #{degree}")
    Double readAIRaMildAverage(@Param("patientid") String patientid, @Param("docterid") String docterid, @Param("degree") Integer degree);

    @Select("SELECT AVG(possible) AS possible FROM checked_x_ra_ai WHERE patientid = #{patientid} AND docterid = #{docterid} AND degree = #{degree}")
    Double readAIRaNormalAverage(@Param("patientid") String patientid, @Param("docterid") String docterid, @Param("degree") Integer degree);
}
