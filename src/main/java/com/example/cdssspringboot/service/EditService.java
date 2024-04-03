package com.example.cdssspringboot.service;

import com.example.cdssspringboot.mapper.EditMapper;
import com.example.cdssspringboot.pojo.dto.EditCheckedX;
import com.example.cdssspringboot.pojo.dto.EditClinial;
import com.example.cdssspringboot.pojo.dto.EditStands;
import com.example.cdssspringboot.pojo.entity.CheckedXData;
import com.example.cdssspringboot.pojo.entity.ClinicalData;
import com.example.cdssspringboot.pojo.entity.StandsData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EditService {
    @Autowired
    private EditMapper editMapper;

    public int ClinialEdit(EditClinial editClinial){
        ClinicalData clinicalData=new ClinicalData();
        clinicalData.setDescription(editClinial.getDescription());
        clinicalData.setTitle(editClinial.getTitle());
        clinicalData.setScore(editClinial.getScore());
        clinicalData.setLabel(editClinial.getLabel());
        clinicalData.setDocterid(editClinial.getDocterid());

        if(Objects.equals(editClinial.getArthritis(), "oa")){
            editMapper.updateClinialOa(clinicalData);
            return 1;
        }
        else if(Objects.equals(editClinial.getArthritis(), "ra")){
            editMapper.updateClinialRa(clinicalData);
            return 1;
        }
        return 0;
    }

    public int CheckedXEdit(EditCheckedX editCheckedX){
        CheckedXData checkedXData=new CheckedXData();
        checkedXData.setDescription(editCheckedX.getDescription());
        checkedXData.setDocterid(editCheckedX.getDocterid());
        checkedXData.setScore(editCheckedX.getScore());
        checkedXData.setLabel(editCheckedX.getLabel());

        if(Objects.equals(editCheckedX.getArthritis(), "oa")){
            editMapper.updateCheckedXOa(checkedXData);
            return 1;
        }
        else if(Objects.equals(editCheckedX.getArthritis(), "ra")){
            editMapper.updateCheckedXRa(checkedXData);
            return 1;
        }


        return 0;
    }

    public int StandsEdit(EditStands editStands){
        StandsData standsData=new StandsData();
        standsData.setAge(editStands.getAge());
        standsData.setSex(editStands.getSex());
        standsData.setArthritis(editStands.getArthritis());
        standsData.setCheck(editStands.getCheck());
        standsData.setClinical(editStands.getClinical());
        standsData.setDocterid(editStands.getDocterid());
        System.out.println(standsData.getAge());
        System.out.println(standsData.getSex());
        System.out.println(standsData.getClinical());
        System.out.println(standsData.getCheck());
        editMapper.updateStands(standsData);
        return 1;
    }
}
