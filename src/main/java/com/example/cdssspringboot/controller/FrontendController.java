package com.example.cdssspringboot.controller;


import com.example.cdssspringboot.pojo.dto.*;
import com.example.cdssspringboot.pojo.entity.PatientData;
import com.example.cdssspringboot.pojo.vo.*;
import com.example.cdssspringboot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backend")
public class FrontendController {

    @Autowired
    private AlgorithmService algorithmService;

    @Autowired
    private ReadService readService;

    @Autowired
    private WriteService writeService;

    @Autowired
    private SingAndLogService singAndLogService;

    @Autowired
    private EditService editService;


    @Autowired
    private DeletService deletService;

    @GetMapping("/clinical/{docterid}")
    public Clinical[] getAllClinical(@PathVariable String docterid) {
        return readService.readFromClinical(docterid);
    }

    @GetMapping("/checkedx/{docterid}")
    public CheckedX[] getAllCheckedX(@PathVariable String docterid){

        return readService.readFromCheckedX(docterid);
    }


    @GetMapping("/patient/{docterid}")
    public Patient[] getAllPatient(@PathVariable String docterid){
        return readService.readFromPatient(docterid);
    }

    @PostMapping("/saveSelectInfo")
    public Rate[] saveSelectInfo(@RequestBody SelectInfo selectInfo) {
        System.out.println("patientid:"+selectInfo.getInfo().getPatientid());
        System.out.println("name:"+selectInfo.getInfo().getName());
        System.out.println("personid:"+selectInfo.getInfo().getPersonalid());
        System.out.println("description:"+selectInfo.getAlldescription());
        System.out.println("keypoint:"+selectInfo.getKeypoint());
        System.out.println("docterid:"+selectInfo.getInfo().getDocterid());
        return readService.readRate(writeService.writeReslut(selectInfo),selectInfo);
    }

    @PostMapping("/singup")
    public int DoctorSignUp(@RequestBody Docter docter){
        return singAndLogService.singupDocter(docter);
    }

    @PostMapping("/login")
    public int DocterLogIn(@RequestBody DocterLogin docterLogin){
        return singAndLogService.loginDocter(docterLogin);
    }

    @PostMapping("/clinial/edit")
    public int EditClinial(@RequestBody EditClinial editClinial){

        return editService.ClinialEdit(editClinial);
    }

    @PostMapping("/clinial/insert")
    public int InsertClinial(@RequestBody InsertClinial insertClinial){

        return writeService.InsertClinial(insertClinial);
    }

    @PostMapping("/clinial/delet")
    public int DeletClinial(@RequestBody DeletClinial deletClinial){

        return deletService.DeletClinial(deletClinial);
    }


    @PostMapping("/checkedx/edit")
    public int EditCheckedX(@RequestBody EditCheckedX editCheckedX){

        return editService.CheckedXEdit(editCheckedX);
    }

    @PostMapping("/checkedx/insert")
    public int InsertCheckedX(@RequestBody InsertCheckedX insertCheckedX){

        return writeService.InsertCheckedX(insertCheckedX);
    }

    @PostMapping("/checkedx/delet")
    public int DeletCheckedX(@RequestBody DeletCheckedX deletCheckedX){

        return deletService.DeletCheckedX(deletCheckedX);
    }


    @PostMapping("/stands/edit")
    public int EditStands(@RequestBody EditStands editStands){

        return editService.StandsEdit(editStands);
    }

    @GetMapping("/docter/results/{docterid}")
    public DocterResult[] getDocterClinical(@PathVariable String docterid) {
        return readService.readDocterResult(docterid);
    }

    @GetMapping("/results/details/{clinialid}")
    public Rate[] getClinicalDetail(@PathVariable int clinialid) {
        return readService.readReultsDetail(clinialid);
    }

    @GetMapping("/patient/results/{patientid}")
    public PatientResult[] getPatientClinical(@PathVariable String patientid) {
        return readService.readPatientResult(patientid);
    }


    @GetMapping("/stands/{docterid}")
    public Stands[] getStands(@PathVariable String docterid) {
        return readService.readStands(docterid);
    }

    @PostMapping("/oadataArray")
    public int saveOaAIData(@RequestBody AIResult aiResult) {
        System.out.println(aiResult);
        writeService.writeOaAIData(aiResult);
        return 1;
    }
    @PostMapping("/radataArray")
    public int saveRaAIData(@RequestBody AIResult aiResult) {
        System.out.println(aiResult);
        writeService.writeRaAIData(aiResult);
        return 1;
    }

}