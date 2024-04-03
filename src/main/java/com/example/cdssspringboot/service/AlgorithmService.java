package com.example.cdssspringboot.service;

import com.example.cdssspringboot.mapper.BackendMapper;
import com.example.cdssspringboot.mapper.ReadMapper;
import com.example.cdssspringboot.pojo.dto.SelectInfo;
import com.example.cdssspringboot.pojo.entity.Stands;
import com.example.cdssspringboot.pojo.vo.CheckedX;
import com.example.cdssspringboot.pojo.vo.Clinical;
import com.example.cdssspringboot.pojo.vo.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class AlgorithmService {

      @Autowired
      private BackendMapper jsonFileReader;

      @Autowired
      private ReadMapper readMapper;



      public CheckedX[] readJsonFromCheckedX(){
            return jsonFileReader.readJsonFromCheckedX();
      }


      public void writeJsonToFile(SelectInfo selectInfo){
            jsonFileReader.writeJsonToFile(selectInfo);
      }
      public void writeJsonToFileFinial(Rate[] rates, String id){
            jsonFileReader.writeJsonToFileFinial(rates,id);
      }
      public Rate getOARate(String id){
            Rate OARate=new Rate();
            int rate=0;
            Stands[] standsArray=jsonFileReader.readJsonFromStands();
            SelectInfo selectInfo=jsonFileReader.readJsonFromSelectInfo(id);
            CheckedX[] checkedxArray= jsonFileReader.readJsonFromCheckedX();  //0-11
            Clinical[] clinicalArray = jsonFileReader.readJsonFromClinical();
            List<String> keyPoints = selectInfo.getKeypoint();
            List<String> descriptions=selectInfo.getAlldescription();
            System.out.println(selectInfo.getAlldescription());
            System.out.println(selectInfo.getKeypoint());

            List evidences=new ArrayList<>();
            if(Integer.parseInt(selectInfo.getAlldescription().get(1))>=40){
                  //System.out.println(222);
                  rate+=standsArray[0].getAge();
                  String text="年龄："+selectInfo.getAlldescription().get(1);
                  String description="OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）";
                  Rate.Evidence evidence=new Rate.Evidence();
                  evidence.setTitle(text);
                  evidence.setDescription(description);
                  evidences.add(evidence);
                  OARate.setEvidences(evidences);
            }
            if(selectInfo.getAlldescription().get(0).equals("女")){
                  //System.out.println(111);
                  rate+=standsArray[0].getSex();
                  String text="性别:"+selectInfo.getAlldescription().get(0);
                  String description="OA高危人群：年龄在40岁及以上、女性、肥胖或超重、有创伤史（推荐强度：强推荐，证据等级：B）";
                  Rate.Evidence evidence=new Rate.Evidence();
                  evidence.setTitle(text);
                  evidence.setDescription(description);
                  evidences.add(evidence);
                  OARate.setEvidences(evidences);
            }
            //关键点检测结果
            for (String keyPoint : keyPoints) {
                  //System.out.println("关键点检测1："+keyPoint);
                  for (int i = 0; i < 11; i++) {
                        //System.out.println("关键点检测2："+checkedxArray[i].getDes());
                        if (keyPoint.equals(checkedxArray[i].getDescription())) {
                              rate += (int) (standsArray[0].getCheck() / 12);;
                              Rate.Evidence evidence=new Rate.Evidence();
                              evidence.setTitle(keyPoint);
                              evidence.setDescription(keyPoint);
                              evidences.add(evidence);
                              OARate.setEvidences(evidences);
                        }
                  }
            }
            //症状检测
            for(String description:descriptions){
               for(int i=0;i<clinicalArray.length;i++){
                   if(description.equals(clinicalArray[i].getTitle())){
                         rate += (int) (standsArray[0].getClinical()/ clinicalArray.length);;
                         Rate.Evidence evidence=new Rate.Evidence();
                         evidence.setTitle(clinicalArray[i].getTitle());
                         evidence.setDescription(clinicalArray[i].getDescription());
                         evidences.add(evidence);
                         OARate.setEvidences(evidences);
                   }
               }
            }

            OARate.setRate(rate);
            OARate.setArthritis("骨关节炎");
            OARate.setPatientid(id);

            return OARate;

      }
      public Rate getRARate(String id){
            Rate RARate=new Rate();
            int rate=0;
            Stands[] standsArray=jsonFileReader.readJsonFromStands();
            SelectInfo selectInfo=jsonFileReader.readJsonFromSelectInfo(id);
            CheckedX[] checkedxArray= jsonFileReader.readJsonFromCheckedX();  //0-11
            Clinical[] clinicalArray = jsonFileReader.readJsonFromClinical();
            List<String> keyPoints = selectInfo.getKeypoint();
            List<String> descriptions=selectInfo.getAlldescription();
            System.out.println(selectInfo.getAlldescription());
            System.out.println(selectInfo.getKeypoint());

            List evidences=new ArrayList<>();
            if(Integer.parseInt(selectInfo.getAlldescription().get(1))>=40&&Integer.parseInt(selectInfo.getAlldescription().get(1))<=60){
                  //System.out.println(222);
                  rate+=standsArray[1].getAge();
                  String text="年龄："+selectInfo.getAlldescription().get(1);
                  String description="类风湿关节炎可发生于任何年龄，高发年龄为40～60岁";
                  Rate.Evidence evidence=new Rate.Evidence();
                  evidence.setTitle(text);
                  evidence.setDescription(description);
                  evidences.add(evidence);
                  RARate.setEvidences(evidences);
            }
            if(selectInfo.getAlldescription().get(0).equals("女")){
                  //System.out.println(111);
                  rate+=standsArray[1].getSex();
                  String text="性别:"+selectInfo.getAlldescription().get(0);
                  String description="类风湿关节炎女性发病率更高，发病率为男性的2～3倍";
                  Rate.Evidence evidence=new Rate.Evidence();
                  evidence.setTitle(text);
                  evidence.setDescription(description);
                  evidences.add(evidence);
                  RARate.setEvidences(evidences);
            }
            //关键点检测结果
            for (String keyPoint : keyPoints) {
                  //System.out.println("关键点检测1："+keyPoint);
                  for (int i = 12; i < 26; i++) {
                        //System.out.println("关键点检测2："+checkedxArray[i].getDes());
                        if (keyPoint.equals(checkedxArray[i].getDescription())) {
                              rate += (int) (standsArray[1].getCheck() / 14);;
                              Rate.Evidence evidence=new Rate.Evidence();
                              evidence.setTitle(keyPoint);
                              evidence.setDescription(keyPoint);
                              evidences.add(evidence);
                              RARate.setEvidences(evidences);
                        }
                  }
            }
            //症状检测
            for(String description:descriptions){
                  ////////////////////////////////////////////////
                  for(int i=5;i<clinicalArray.length;i++){
                        if(description.equals(clinicalArray[i].getTitle())){
                              rate += (int) (standsArray[1].getClinical()/ clinicalArray.length);;
                              Rate.Evidence evidence=new Rate.Evidence();
                              evidence.setTitle(clinicalArray[i].getTitle());
                              evidence.setDescription(clinicalArray[i].getDescription());
                              evidences.add(evidence);
                              RARate.setEvidences(evidences);
                        }
                  }
            }

            RARate.setRate(rate);
            RARate.setArthritis("类风湿关节炎");
            RARate.setPatientid(id);

            return RARate;

      }

}
