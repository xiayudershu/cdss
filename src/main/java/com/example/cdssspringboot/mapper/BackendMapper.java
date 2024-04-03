package com.example.cdssspringboot.mapper;

import com.example.cdssspringboot.pojo.dto.SelectInfo;
import com.example.cdssspringboot.pojo.entity.Stands;
import com.example.cdssspringboot.pojo.vo.CheckedX;
import com.example.cdssspringboot.pojo.vo.Clinical;
import com.example.cdssspringboot.pojo.vo.Patient;
import com.example.cdssspringboot.pojo.vo.Rate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class BackendMapper {
    public Clinical[] readJsonFromClinical() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ClassPathResource resource = new ClassPathResource("static/knowledge/clinical.json");
            InputStream inputStream = resource.getInputStream();
            Clinical[] clinicalArray = objectMapper.readValue(inputStream, Clinical[].class);
            System.out.println("JSON文件读取成功！");
            return clinicalArray;
        } catch (IOException e) {
            System.out.println("JSON文件读取失败：" + e.getMessage());
            return null;
        }
    }

    public CheckedX[] readJsonFromCheckedX(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("static/knowledge/xchecked.json");
            InputStream inputStream = resource.getInputStream();
            CheckedX[] CheckedXArray = objectMapper.readValue(inputStream, CheckedX[].class);
            System.out.println("JSON文件读取成功！");
            return CheckedXArray;
        } catch (IOException e) {
            System.out.println("JSON文件读取失败：" + e.getMessage());
            return null;
        }
    }

    public Patient[] readJsonFromPatient(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("static/patientsInfo/patient.json");
            InputStream inputStream = resource.getInputStream();
            Patient[] PatientArray = objectMapper.readValue(inputStream, Patient[].class);
            System.out.println("JSON文件读取成功！");
            return PatientArray;
        } catch (IOException e) {
            System.out.println("JSON文件读取失败：" + e.getMessage());
            return null;
        }
    }
    public SelectInfo readJsonFromSelectInfo(String id){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String workingDir = System.getProperty("user.dir");
            String filePath = workingDir + "/src/main/resources/static/patientsLog/" + id + ".json";
            File file = new File(filePath);
            SelectInfo selectInfo = objectMapper.readValue(file, SelectInfo.class);
            System.out.println("JSON文件读取成功！");
            return selectInfo;
        } catch (IOException e) {
            System.out.println("JSON文件读取失败：" + e.getMessage());
            return null;
        }
    }
    public Stands[] readJsonFromStands(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("static/knowledge/stands.json");
            InputStream inputStream = resource.getInputStream();
            Stands[] StandsArray = objectMapper.readValue(inputStream, Stands[].class);
            System.out.println("JSON文件读取成功！");
            return StandsArray;
        } catch (IOException e) {
            System.out.println("JSON文件读取失败：" + e.getMessage());
            return null;
        }
    }
    public void writeJsonToFile(SelectInfo selectInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        String workingDir = System.getProperty("user.dir");
        System.out.println("当前工作目录：" + workingDir);
        String fileName = "/src/main/resources/static/patientsLog/" + selectInfo.getInfo().getPatientid() + ".json";
        String filePath = workingDir + fileName;
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            objectMapper.writeValue(fileWriter, selectInfo);
            System.out.println("JSON文件写入成功！");
        } catch (IOException e) {
            System.out.println("JSON文件写入失败：" + e.getMessage());
        }
    }
    public void writeJsonToFileFinial(Rate[] rates, String id) {
        ObjectMapper objectMapper = new ObjectMapper();
        String workingDir = System.getProperty("user.dir");
        System.out.println("当前工作目录：" + workingDir);
        String fileName = "/src/main/resources/static/patientsLog/" + id+"-results"+ ".json";
        String filePath = workingDir + fileName;
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            objectMapper.writeValue(fileWriter, rates);
            System.out.println("JSON文件写入成功！");
        } catch (IOException e) {
            System.out.println("JSON文件写入失败：" + e.getMessage());
        }
    }

}