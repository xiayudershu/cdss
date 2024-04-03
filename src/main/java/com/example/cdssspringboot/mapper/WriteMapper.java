package com.example.cdssspringboot.mapper;

import com.example.cdssspringboot.pojo.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface WriteMapper {
    @Insert("insert into clinial_result (clinialid,patientid,docterid,oarate,rarate,date)" +
            "values"+
            "(#{clinialid},#{patientid},#{docterid},#{oarate},#{rarate},#{date})")
    void writeResult(ResultData resultData);

    @Insert("insert into clinial_evidence (clinialid,title, description,arthritis,score)"+
             "values"+
             "(#{clinialid},#{title},#{description},#{arthritis},#{score})")
    void writeEvidence(EvidenceData evidenceData);


    @Insert("insert into docter (name,docterid,personalid,sex,password)" +
            "values"+
            "(#{name},#{docterid},#{personalid},#{sex},#{password})")
    void writeDocter(String name,String docterid,String personalid,String sex,String password);


    @Insert("insert into clinial_oa (title, label,description,docterid,score)"+
            "values"+
            "(#{title}, #{label},#{description},#{docterid},#{score})")
    void insertClinialOa(ClinicalData clinicalData);

    @Insert("insert into clinial_ra (title, label,description,docterid,score)"+
            "values"+
            "(#{title}, #{label},#{description},#{docterid},#{score})")
    void insertClinialRa(ClinicalData clinicalData);

    @Insert("insert into checked_x_oa (label,description,docterid,score)"+
            "values"+
            "(#{label},#{description},#{docterid},#{score})")
    void insertCheckedXOa(CheckedXData checkedXData);

    @Insert("insert into checked_x_ra (label,description,docterid,score)"+
            "values"+
            "(#{label},#{description},#{docterid},#{score})")
    void insertCheckedXRa(CheckedXData checkedXData);

    @Insert("insert into checked_x_oa_ai (hand,degree,possible,docterid,patientid)"+
            "values"+
            "(#{hand},#{degree},#{possible},#{docterid},#{patientid})")
    void insertAIResultOa(AIResultData aiResultData);

    @Insert("insert into checked_x_ra_ai (hand,degree,possible,docterid,patientid)"+
            "values"+
            "(#{hand},#{degree},#{possible},#{docterid},#{patientid})")
    void insertAIResultRa(AIResultData aiResultData);
}
