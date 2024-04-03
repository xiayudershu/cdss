package com.example.cdssspringboot.service;

import com.example.cdssspringboot.mapper.DeletMapper;
import com.example.cdssspringboot.mapper.EditMapper;
import com.example.cdssspringboot.mapper.ReadMapper;
import com.example.cdssspringboot.mapper.WriteMapper;
import com.example.cdssspringboot.pojo.dto.AIResult;
import com.example.cdssspringboot.pojo.dto.InsertCheckedX;
import com.example.cdssspringboot.pojo.dto.InsertClinial;
import com.example.cdssspringboot.pojo.dto.SelectInfo;
import com.example.cdssspringboot.pojo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class WriteService {
    @Autowired
    private WriteMapper writeMapper;
    @Autowired
    private ReadMapper readMapper;
    @Autowired
    private EditMapper editMapper;

    @Autowired
    private DeletMapper deletMapper;


    public void writeOaAIData(AIResult aiResult){

        deletMapper.deleteOaAI(aiResult.getPatientid(),aiResult.getDocterid());

         for(int i=0;i<aiResult.getAidata().length;i++){
             AIResultData aiResultData=new AIResultData();
             aiResultData.setPatientid(aiResult.getPatientid());
             aiResultData.setDocterid(aiResult.getDocterid());
             aiResultData.setHand(aiResult.getAidata()[i].getHand());
             aiResultData.setDegree(aiResult.getAidata()[i].getDegree());
             aiResultData.setPossible(aiResult.getAidata()[i].getPossible());
             writeMapper.insertAIResultOa(aiResultData);
         }
    }
    public void writeRaAIData(AIResult aiResult){
        deletMapper.deleteRaAI(aiResult.getPatientid(),aiResult.getDocterid());
        for(int i=0;i<aiResult.getAidata().length;i++){
            AIResultData aiResultData=new AIResultData();
            aiResultData.setPatientid(aiResult.getPatientid());
            aiResultData.setDocterid(aiResult.getDocterid());
            aiResultData.setHand(aiResult.getAidata()[i].getHand());
            aiResultData.setDegree(aiResult.getAidata()[i].getDegree());
            aiResultData.setPossible(aiResult.getAidata()[i].getPossible());
            writeMapper.insertAIResultRa(aiResultData);
        }
    }


    public int InsertClinial(InsertClinial insertClinial){
        ClinicalData clinicalData=new ClinicalData();
        clinicalData.setDocterid(insertClinial.getDocterid());
        clinicalData.setTitle(insertClinial.getTitle());
        clinicalData.setScore(insertClinial.getScore());
        clinicalData.setDescription(insertClinial.getDescription());
        if(Objects.equals(insertClinial.getArthritis(), "oa")){
            clinicalData.setLabel(readMapper.getCiniallabelOa(insertClinial.getDocterid())+1);
            writeMapper.insertClinialOa(clinicalData);
            return 1;
        }
        else if(Objects.equals(insertClinial.getArthritis(), "ra")){
            clinicalData.setLabel(readMapper.getCiniallabelRa(insertClinial.getDocterid())+1);
            writeMapper.insertClinialRa(clinicalData);
            return 1;
        }

        return 0;
    }

    public int InsertCheckedX(InsertCheckedX insertCheckedX){
        CheckedXData checkedXData=new CheckedXData();
        checkedXData.setScore(insertCheckedX.getScore());
        checkedXData.setDocterid(insertCheckedX.getDocterid());
        checkedXData.setDescription(insertCheckedX.getDescription());
        if(Objects.equals(insertCheckedX.getArthritis(), "oa")){
            checkedXData.setLabel(readMapper.getCheckedXlabelOa(insertCheckedX.getDocterid())+1);
            writeMapper.insertCheckedXOa(checkedXData);
            return 1;
        }
        else if(Objects.equals(insertCheckedX.getArthritis(), "ra")){
            checkedXData.setLabel(readMapper.getCheckedXlabelRa(insertCheckedX.getDocterid())+1);
            writeMapper.insertCheckedXRa(checkedXData);
            return 1;
        }


        return 0;
    }


    public int writeReslut(SelectInfo selectInfo){
        ResultData resultData=new ResultData();
        resultData.setClinialid(readMapper.getCinialid()+1);
        int id=readMapper.getCinialid()+1;
        resultData.setPatientid(selectInfo.getInfo().getPatientid());
        resultData.setDocterid(selectInfo.getInfo().getDocterid());
        resultData.setOarate(0.00);
        resultData.setRarate(0.00);
        resultData.setDate(LocalDate.now());
        writeMapper.writeResult(resultData);
        double oascore=writeEvidenceOA(selectInfo,id,selectInfo.getInfo().getDocterid());
        double rascore=writeEvidenceRA(selectInfo,id,selectInfo.getInfo().getDocterid());
        System.out.println(rascore);
        System.out.println(oascore);
        editMapper.writeRate(oascore,rascore,id);
        return id;

    }

    public double writeEvidenceOA(SelectInfo selectInfo,int clinialid,String docterid){
        StandsData oastands=readMapper.readFromStands("oa",docterid);
        List<String> keyPoints = selectInfo.getKeypoint();
        List<String> descriptions=selectInfo.getAlldescription();
        ClinicalData[] clinicalDataOa = readMapper.readFromClinialOa(docterid);
        CheckedXData[] checkedXDataOa = readMapper.readFromCheckedXOa(docterid);
        double oascore=0.00;

        if(Integer.parseInt(selectInfo.getAlldescription().get(1))>=40){
            String title="年龄："+selectInfo.getAlldescription().get(1);
            String description="OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）";
            EvidenceData evidenceData=new EvidenceData();
            evidenceData.setClinialid(clinialid);
            evidenceData.setTitle(title);
            evidenceData.setDescription(description);
            evidenceData.setArthritis("oa");
            evidenceData.setScore(oastands.getAge());
            oascore+=oastands.getAge();
            writeMapper.writeEvidence(evidenceData);

        }
        if(selectInfo.getAlldescription().get(0).equals("女")){
            String title="性别:"+selectInfo.getAlldescription().get(0);
            String description="OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）";
            EvidenceData evidenceData=new EvidenceData();
            evidenceData.setClinialid(clinialid);
            evidenceData.setTitle(title);
            evidenceData.setDescription(description);
            evidenceData.setArthritis("oa");
            evidenceData.setScore(oastands.getSex());
            oascore+=oastands.getSex();
            writeMapper.writeEvidence(evidenceData);
        }

        for (String description:descriptions) {
            for (int i = 0; i < clinicalDataOa.length; i++) {
                if (description.equals(clinicalDataOa[i].getTitle())) {
                    //System.out.println(description);
                    EvidenceData evidenceData=new EvidenceData();
                    evidenceData.setClinialid(clinialid);
                    evidenceData.setTitle(clinicalDataOa[i].getTitle());
                    evidenceData.setDescription(clinicalDataOa[i].getDescription());
                    evidenceData.setArthritis("oa");
                    evidenceData.setScore(clinicalDataOa[i].getScore()*oastands.getClinical()*0.01);
                    oascore+=evidenceData.getScore();
                    writeMapper.writeEvidence(evidenceData);
                }
            }
        }
        if(keyPoints.isEmpty()){
//            double severavg=readMapper.readAIOaSeverAverage(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid(),2);
//            double mildavg=readMapper.readAIOaMildAverage(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid(),1);
//            double normalavg=readMapper.readAIOaNormalAverage(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid(),0);
              double severavg=0.00;
              double mildavg=0.00;
              int severnum=0;
              int mildnum=0;

            AIResultData[] aiResultData= readMapper.readAIResultOa(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid());
            for(int i=0;i< aiResultData.length;i++){
                EvidenceData evidenceData=new EvidenceData();
                evidenceData.setClinialid(clinialid);
                String handstring="";
                if(Objects.equals(aiResultData[i].getHand(), "left")){
                    handstring="左手";
                }
                if(Objects.equals(aiResultData[i].getHand(), "right")){
                    handstring="右手";
                }
                evidenceData.setTitle(handstring+"严重程度:"+aiResultData[i].getDegree()+"置信度："+aiResultData[i].getPossible());
                evidenceData.setDescription("严重程度:"+aiResultData[i].getDegree()+"置信度："+aiResultData[i].getPossible()+"手："+handstring);
                evidenceData.setArthritis("oa");
                evidenceData.setScore(oastands.getCheck()*aiResultData[i].getPossible());
                writeMapper.writeEvidence(evidenceData);
                if(aiResultData[i].getDegree()==2){
                    severavg+=aiResultData[i].getPossible();
                    severnum++;
                }
                if(aiResultData[i].getDegree()==1){
                    mildavg+=aiResultData[i].getPossible();
                    mildnum++;
                }

            }
            if(severnum!=0){
                oascore+=(severavg/severnum)*1*oastands.getCheck();
            }
            if(mildnum!=0){
                oascore+=(mildavg/mildnum)*0.5*oastands.getCheck();
            }
            //System.out.println("oa得分："+oascore);
        }
        else{
            for (String keyPoint : keyPoints) {
                for (int i = 0; i < checkedXDataOa.length; i++) {
                    if (keyPoint.equals(checkedXDataOa[i].getDescription())) {
                        EvidenceData evidenceData=new EvidenceData();
                        evidenceData.setClinialid(clinialid);
                        evidenceData.setTitle(checkedXDataOa[i].getDescription());
                        evidenceData.setDescription(checkedXDataOa[i].getDescription());
                        evidenceData.setArthritis("oa");
                        evidenceData.setScore(checkedXDataOa[i].getScore()*oastands.getCheck()*0.01);
                        oascore+=evidenceData.getScore();
                        writeMapper.writeEvidence(evidenceData);
                    }
                }
            }
        }


       return oascore;
    }

    public double writeEvidenceRA(SelectInfo selectInfo,int clinialid,String docterid){
        StandsData rastands=readMapper.readFromStands("ra",docterid);
        List<String> keyPoints = selectInfo.getKeypoint();
        List<String> descriptions=selectInfo.getAlldescription();
        ClinicalData[] clinicalDataRa = readMapper.readFromClinialRa(docterid);
        CheckedXData[] checkedXDataRa = readMapper.readFromCheckedXRa(docterid);
        double rascore=0.00;
        //System.out.println("ra得分："+rascore);
        if(Integer.parseInt(selectInfo.getAlldescription().get(1))>=40&&Integer.parseInt(selectInfo.getAlldescription().get(1))<=60){
            String title="年龄："+selectInfo.getAlldescription().get(1);
            String description="类风湿关节炎可发生于任何年龄，高发年龄为40～60岁";
            EvidenceData evidenceData=new EvidenceData();
            evidenceData.setClinialid(clinialid);
            evidenceData.setTitle(title);
            evidenceData.setDescription(description);
            evidenceData.setArthritis("ra");
            evidenceData.setScore(rastands.getAge());
            rascore+=evidenceData.getScore();
            writeMapper.writeEvidence(evidenceData);

        }
        //System.out.println("ra得分："+rascore);
        if(selectInfo.getAlldescription().get(0).equals("女")){
            String title="性别:"+selectInfo.getAlldescription().get(0);
            String description="类风湿关节炎女性发病率更高，发病率为男性的2～3倍";
            EvidenceData evidenceData=new EvidenceData();
            evidenceData.setClinialid(clinialid);
            evidenceData.setTitle(title);
            evidenceData.setDescription(description);
            evidenceData.setArthritis("ra");
            evidenceData.setScore(rastands.getSex());
            rascore+=evidenceData.getScore();
            writeMapper.writeEvidence(evidenceData);
        }
        //System.out.println("ra得分："+rascore);
        for (String description:descriptions) {
            for (int i = 0; i < clinicalDataRa.length; i++) {
                if (description.equals(clinicalDataRa[i].getTitle())) {
                    EvidenceData evidenceData=new EvidenceData();
                    evidenceData.setClinialid(clinialid);
                    evidenceData.setTitle(clinicalDataRa[i].getTitle());
                    evidenceData.setDescription(clinicalDataRa[i].getDescription());
                    evidenceData.setArthritis("ra");
                    evidenceData.setScore(clinicalDataRa[i].getScore()*rastands.getClinical()*0.01);
                    rascore+=evidenceData.getScore();
                    writeMapper.writeEvidence(evidenceData);
                }
            }
        }

        //System.out.println("ra得分："+rascore);
        if(keyPoints.isEmpty()){
//            double severavg=readMapper.readAIRaSeverAverage(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid(),2);
//            double mildavg=readMapper.readAIRaMildAverage(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid(),1);
//            double normalavg=readMapper.readAIRaNormalAverage(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid(),0);
              double severavg=0.00;
              double mildavg=0.00;
              int severnum=0;
              int mildnum=0;

            AIResultData[] aiResultData= readMapper.readAIResultRa(selectInfo.getInfo().getPatientid(),selectInfo.getInfo().getDocterid());
            for(int i=0;i< aiResultData.length;i++){
                EvidenceData evidenceData=new EvidenceData();
                evidenceData.setClinialid(clinialid);
                String handstring="";
                if(Objects.equals(aiResultData[i].getHand(), "left")){
                    handstring="左手";
                }
                if(Objects.equals(aiResultData[i].getHand(), "right")){
                    handstring="右手";
                }
                evidenceData.setTitle(handstring+"严重程度:"+aiResultData[i].getDegree()+"置信度："+aiResultData[i].getPossible());
                evidenceData.setDescription("严重程度:"+aiResultData[i].getDegree()+"置信度："+aiResultData[i].getPossible()+"手："+handstring);
                evidenceData.setArthritis("ra");
                evidenceData.setScore(rastands.getCheck()*aiResultData[i].getPossible());


                writeMapper.writeEvidence(evidenceData);

                if(aiResultData[i].getDegree()==2){
                    severavg+=aiResultData[i].getPossible();
                    severnum++;
                }
                if(aiResultData[i].getDegree()==1){
                    mildavg+=aiResultData[i].getPossible();
                    mildnum++;
                }

            }
            if(severnum!=0){
                rascore+=(severavg/severnum)*1*rastands.getCheck();
            }
            if(mildnum!=0){
                rascore+=(mildavg/mildnum)*0.5*rastands.getCheck();
            }

            //System.out.println("ra得分："+rascore);
        }else{

        for (String keyPoint : keyPoints) {
            for (int i = 0; i < checkedXDataRa.length; i++) {
                if (keyPoint.equals(checkedXDataRa[i].getDescription())) {
                    EvidenceData evidenceData=new EvidenceData();
                    evidenceData.setClinialid(clinialid);
                    evidenceData.setTitle(checkedXDataRa[i].getDescription());
                    evidenceData.setDescription(checkedXDataRa[i].getDescription());
                    evidenceData.setArthritis("ra");
                    evidenceData.setScore(checkedXDataRa[i].getScore()*rastands.getCheck()*0.01);
                    rascore+=evidenceData.getScore();
                    writeMapper.writeEvidence(evidenceData);
                }
            }
        }
        }

       return rascore;
    }
}
