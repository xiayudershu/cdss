package com.example.cdssspringboot.service;

import com.example.cdssspringboot.mapper.ReadMapper;
import com.example.cdssspringboot.mapper.WriteMapper;
import com.example.cdssspringboot.pojo.dto.Docter;
import com.example.cdssspringboot.pojo.dto.DocterLogin;
import com.example.cdssspringboot.pojo.entity.DocterData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SingAndLogService {
    @Autowired
    private ReadMapper readMapper;

    @Autowired
    private WriteMapper writeMapper;

    public int singupDocter(Docter docter){
        DocterData docterData=new DocterData();
        docterData.setSex(docter.getSex());
        docterData.setDocterid(docter.getDocterid());
        docterData.setPersonalid(docter.getPersonalid());
        docterData.setName(docter.getName());
        docterData.setDocterid(docter.getDocterid());
        docterData.setPassword(docter.getPassword());

        if(readMapper.ifDocterid(docter.getDocterid())!=null){
            return 0;
        }
        else {
            writeMapper.writeDocter(docterData.getName(),
                    docterData.getDocterid(),
                    docterData.getPersonalid(),
                    docterData.getSex(),
                    docterData.getPassword());
            return 1;
        }

    }

    public int loginDocter(DocterLogin docterLogin){
        DocterData docterData=readMapper.ifDocterid(docterLogin.getDocterid());
        if(docterData==null){
            return -1;
        }
        else{
            if(Objects.equals(docterData.getPassword(), docterLogin.getPassword())){
                if(Objects.equals(docterData.getName(), docterLogin.getName())){
                    return 1;
                }
                return -2;
            }
            return 0;
        }
    }
}
