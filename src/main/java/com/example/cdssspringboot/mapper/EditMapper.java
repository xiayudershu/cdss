package com.example.cdssspringboot.mapper;

import com.example.cdssspringboot.pojo.entity.CheckedXData;
import com.example.cdssspringboot.pojo.entity.ClinicalData;
import com.example.cdssspringboot.pojo.entity.StandsData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EditMapper {

    @Update("UPDATE clinial_result SET oarate = #{oarate}, rarate = #{rarate} WHERE clinialid = #{clinialid}")
    void writeRate(double oarate,double rarate, int clinialid);
    @Update("update clinial_oa set title=#{title},description=#{description},score=#{score} where label=#{label} and docterid=#{docterid}")
    void updateClinialOa(ClinicalData clinicalData);

    @Update("update clinial_ra set title=#{title},description=#{description},score=#{score} where label=#{label} and docterid=#{docterid}")
    void updateClinialRa(ClinicalData clinicalData);

    @Update("update checked_x_oa set description=#{description},score=#{score} where label=#{label} and docterid=#{docterid}")
    void updateCheckedXOa(CheckedXData checkedXData);

    @Update("update checked_x_ra set description=#{description},score=#{score} where label=#{label} and docterid=#{docterid}")
    void updateCheckedXRa(CheckedXData checkedXData);

    @Update("update stands set sex=#{sex}, age=#{age}, `check`=#{check}, clinical=#{clinical} where arthritis=#{arthritis} and docterid=#{docterid}")
    void updateStands(StandsData standsData);

}
