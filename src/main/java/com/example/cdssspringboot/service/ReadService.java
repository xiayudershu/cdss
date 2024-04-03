package com.example.cdssspringboot.service;

import com.example.cdssspringboot.mapper.ReadMapper;
import com.example.cdssspringboot.pojo.dto.SelectInfo;
import com.example.cdssspringboot.pojo.entity.*;
import com.example.cdssspringboot.pojo.vo.*;
import com.example.cdssspringboot.pojo.vo.Stands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReadService {
    @Autowired
    private ReadMapper readMapper;





    public Stands[] readStands(String docterid){
        Stands[] stands=new Stands[2];

        StandsData oastands=readMapper.readFromStands("oa",docterid);
        stands[0]=new Stands();
        stands[0].setAge(oastands.getAge());
        stands[0].setSex(oastands.getSex());
        stands[0].setCheck(oastands.getCheck());
        stands[0].setClinical(oastands.getClinical());
        stands[0].setArthritis(oastands.getArthritis());

        StandsData rastands=readMapper.readFromStands("ra",docterid);
        stands[1]=new Stands();
        stands[1].setAge(rastands.getAge());
        stands[1].setSex(rastands.getSex());
        stands[1].setCheck(rastands.getCheck());
        stands[1].setClinical(rastands.getClinical());
        stands[1].setArthritis(rastands.getArthritis());


        return stands;
    }
    public Rate[] readReultsDetail(int clinialid){
        Rate[] rates=new Rate[2];
        EvidenceData[] evidenceDataOa=readMapper.readFromEvidence(clinialid,"oa");
        EvidenceData[] evidenceDataRa=readMapper.readFromEvidence(clinialid,"ra");
        ResultData resultData=readMapper.readFromResult(clinialid);

        Rate oarate=new Rate();
        Rate rarate=new Rate();
        oarate.setRate(resultData.getOarate());
        rarate.setRate(resultData.getRarate());

        oarate.setPatientid(resultData.getPatientid());
        rarate.setPatientid(resultData.getPatientid());

        List oaevidences=new ArrayList<>();
        List raevidences=new ArrayList<>();
        for(int i=0;i<evidenceDataOa.length;i++){
            Rate.Evidence evidence=new Rate.Evidence();
            evidence.setTitle(evidenceDataOa[i].getTitle());
            evidence.setDescription(evidenceDataOa[i].getDescription());
            oaevidences.add(evidence);
        }
        for(int i=0;i<evidenceDataRa.length;i++){
            Rate.Evidence evidence=new Rate.Evidence();
            evidence.setTitle(evidenceDataRa[i].getTitle());
            evidence.setDescription(evidenceDataRa[i].getDescription());
            raevidences.add(evidence);
        }
        oarate.setEvidences(oaevidences);
        rarate.setEvidences(raevidences);
        rates[0]=oarate;
        rates[1]=rarate;

        return rates;
    }








    public DocterResult[] readDocterResult(String docterid){
         ResultData[] resultData=readMapper.readDocterResult(docterid);
         DocterResult[] docterResults=new DocterResult[resultData.length];
         for(int i=0;i<resultData.length;i++){
             docterResults[i]=new DocterResult();
             docterResults[i].setClinialid(resultData[i].getClinialid());
             docterResults[i].setOarate(resultData[i].getOarate());
             docterResults[i].setRarate(resultData[i].getRarate());
             docterResults[i].setDate(resultData[i].getDate());
             docterResults[i].setName(readMapper.findPatientName(resultData[i].getPatientid(),docterid));
         }
         return docterResults;
    }
    public PatientResult[] readPatientResult(String patientid){
        ResultData[] resultData=readMapper.readPatientResult(patientid);
        PatientResult[] patientResults=new PatientResult[resultData.length];

        for(int i=0;i<resultData.length;i++){
            patientResults[i]=new PatientResult();
            patientResults[i].setRarate(resultData[i].getRarate());
            patientResults[i].setOarate(resultData[i].getOarate());
            patientResults[i].setClinialid(resultData[i].getClinialid());
            patientResults[i].setDate(resultData[i].getDate());
            patientResults[i].setName(readMapper.findDocterName(resultData[i].getDocterid()));
        }
        return patientResults;
    }


    public Patient[] readFromPatient(String docterid){
        PatientData[] patientData=readMapper.readFromPatient(docterid);
        Patient[] patients=new Patient[patientData.length];
        for(int i=0;i<patientData.length;i++){
            patients[i]=new Patient();
            patients[i].setAge(patientData[i].getAge());
            patients[i].setDate(patientData[i].getDate());
            patients[i].setName(patientData[i].getName());
            patients[i].setSex(patientData[i].getSex());
            patients[i].setHistory(patientData[i].getHistory());
            patients[i].setPatientid(patientData[i].getPatientid());
            patients[i].setPersonalid(patientData[i].getPersonalid());
        }
        return patients;
    }
    public Clinical[] readFromClinical(String docterid){
        ClinicalData[] oaData = readMapper.readFromClinialOa(docterid);
        ClinicalData[] raData = readMapper.readFromClinialRa(docterid);
        ClinicalData[] clinicalData = new ClinicalData[oaData.length + raData.length];
        System.arraycopy(oaData, 0, clinicalData, 0, oaData.length);
        System.arraycopy(raData, 0,clinicalData, oaData.length, raData.length);

        Clinical[] clinicals=new Clinical[clinicalData.length];
        System.out.println(clinicalData[0].getTitle());
        System.out.println(clinicals.length);
        for (int i=0;i<oaData.length;i++){
            clinicals[i] = new Clinical();
            clinicals[i].setChecked(false);
            clinicals[i].setLabel(clinicalData[i].getLabel());
            clinicals[i].setDescription(clinicalData[i].getDescription());
            clinicals[i].setTitle(clinicalData[i].getTitle());
            clinicals[i].setScore(clinicalData[i].getScore());
            clinicals[i].setArthritis("oa");
        }
        for (int i=oaData.length;i<raData.length+oaData.length;i++){
            clinicals[i] = new Clinical();
            clinicals[i].setChecked(false);
            clinicals[i].setLabel(clinicalData[i].getLabel());
            clinicals[i].setDescription(clinicalData[i].getDescription());
            clinicals[i].setTitle(clinicalData[i].getTitle());
            clinicals[i].setScore(clinicalData[i].getScore());
            clinicals[i].setArthritis("ra");
        }
        return clinicals ;
    }
    public CheckedX[] readFromCheckedX(String docterid){
        CheckedXData[] oaData = readMapper.readFromCheckedXOa(docterid);
        CheckedXData[] raData = readMapper.readFromCheckedXRa(docterid);
        CheckedXData[] checkedXData = new CheckedXData[oaData.length + raData.length];
        System.arraycopy(oaData, 0, checkedXData, 0, oaData.length);
        System.arraycopy(raData, 0,checkedXData, oaData.length, raData.length);

        CheckedX[] checkedX=new CheckedX[checkedXData.length];
        for (int i=0;i< oaData.length;i++){
            checkedX[i] = new CheckedX();
            checkedX[i].setLabel(checkedXData[i].getLabel());
            checkedX[i].setDescription(checkedXData[i].getDescription());
            checkedX[i].setScore(checkedXData[i].getScore());
            checkedX[i].setArthritis("oa");
        }
        for(int i= oaData.length;i< oaData.length+ raData.length;i++){
            checkedX[i] = new CheckedX();
            checkedX[i].setLabel(checkedXData[i].getLabel());
            checkedX[i].setDescription(checkedXData[i].getDescription());
            checkedX[i].setScore(checkedXData[i].getScore());
            checkedX[i].setArthritis("ra");
        }
        return checkedX ;
    }

    public Rate[] readRate(int clinialid, SelectInfo selectInfo){
        Rate[] rates=new Rate[2];
        EvidenceData[] evidenceDataOa=readMapper.readFromEvidence(clinialid,"oa");
        EvidenceData[] evidenceDataRa=readMapper.readFromEvidence(clinialid,"ra");
        ResultData resultData=readMapper.readFromResult(clinialid);

        Rate oarate=new Rate();
        Rate rarate=new Rate();
        oarate.setRate(resultData.getOarate());
        rarate.setRate(resultData.getRarate());

        oarate.setPatientid(selectInfo.getInfo().getPatientid());
        rarate.setPatientid(selectInfo.getInfo().getPatientid());

        List oaevidences=new ArrayList<>();
        List raevidences=new ArrayList<>();
        for(int i=0;i<evidenceDataOa.length;i++){
            Rate.Evidence evidence=new Rate.Evidence();
            evidence.setTitle(evidenceDataOa[i].getTitle());
            evidence.setDescription(evidenceDataOa[i].getDescription());
            oaevidences.add(evidence);
        }
        for(int i=0;i<evidenceDataRa.length;i++){
            Rate.Evidence evidence=new Rate.Evidence();
            evidence.setTitle(evidenceDataRa[i].getTitle());
            evidence.setDescription(evidenceDataRa[i].getDescription());
            raevidences.add(evidence);
        }
        oarate.setEvidences(oaevidences);
        rarate.setEvidences(raevidences);
        rates[0]=oarate;
        rates[1]=rarate;

        return rates;
    }
}
