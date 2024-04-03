package com.example.cdssspringboot.service;

import com.example.cdssspringboot.mapper.DeletMapper;
import com.example.cdssspringboot.pojo.dto.DeletCheckedX;
import com.example.cdssspringboot.pojo.dto.DeletClinial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DeletService {
    @Autowired
    private DeletMapper deletMapper;

    public int DeletClinial(DeletClinial deletClinial){
        if(Objects.equals(deletClinial.getArthritis(), "oa")){
            deletMapper.deleteClinialOa(deletClinial.getLabel(),deletClinial.getDocterid());
            return 1;
        }
        else if(Objects.equals(deletClinial.getArthritis(), "ra")){
            deletMapper.deleteClinialRa(deletClinial.getLabel(),deletClinial.getDocterid());
            return 1;
        }
        return 0;
    }

    public int DeletCheckedX(DeletCheckedX deletCheckedX){
        if(Objects.equals(deletCheckedX.getArthritis(), "oa")){
            deletMapper.deleteCheckedXOa(deletCheckedX.getLabel(), deletCheckedX.getDocterid());
            return 1;
        }
        else if(Objects.equals(deletCheckedX.getArthritis(), "ra")){
            deletMapper.deleteCheckedXRa(deletCheckedX.getLabel(), deletCheckedX.getDocterid());
            return 1;
        }
        return 0;
    }


}
