package com.example.OPMS_2.Controller;

import com.example.OPMS_2.DTO.EmployeeDTO;
import com.example.OPMS_2.DTO.PositionDTO;
import com.example.OPMS_2.DTO.RecruiterDTO;
import com.example.OPMS_2.DTO.StageDTO;
import com.example.OPMS_2.Entity.Employee;
import com.example.OPMS_2.Entity.Position;
import com.example.OPMS_2.Entity.Stage;
import com.example.OPMS_2.Repository.EmployeeRepo;
import com.example.OPMS_2.Repository.PositionRepo;
import com.example.OPMS_2.Repository.StageRepo;
import com.example.OPMS_2.Service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.OPMS_2.Entity.Recruiter;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/recruiter")
public class RecruiterController {

    @Autowired
    RecruiterService recruiterService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PositionRepo positionRepo;

    @GetMapping("/email/{emailId}")
    public ResponseEntity<RecruiterDTO> getRecruiterByEmail(@PathVariable String emailId) {
        Recruiter recruiter = recruiterService.findByEmail(emailId);
        if (recruiter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        RecruiterDTO recruiterDTO = new RecruiterDTO();
        recruiterDTO.setRecruiterId(recruiter.getRecruiterId());
        recruiterDTO.setRecruiterName(recruiter.getRecruiterName());
        recruiterDTO.setEmailId(recruiter.getEmailId());

        return ResponseEntity.ok(recruiterDTO);
    }


    @GetMapping("/{recruiterId}/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByRecruiterId(@PathVariable Long recruiterId) {
        List<EmployeeDTO> employees = recruiterService.getEmployeesByRecruiterId(recruiterId);

        // Populate client name for each employee
        for (EmployeeDTO employeeDTO : employees) {
            setClientNameForEmployee(employeeDTO);
        }

        return ResponseEntity.ok(employees);
    }


    @PostMapping("/{recruiterId}/employee")
    public EmployeeDTO addEmployeeByRecruiterId(@RequestBody EmployeeDTO employeeDTO){
        return recruiterService.addEmployeeByRecruiterId(employeeDTO);
    }

    @PutMapping("/employee/update")
    public EmployeeDTO updateEmployeeByRecruiter(@RequestBody EmployeeDTO employeeDTO){
        return recruiterService.updateEmployeeByRecruiter(employeeDTO);
    }

    @DeleteMapping("/employee/{empId}")
    public void deleteEmployeeByRecruiter(@PathVariable Long empId){
         recruiterService.deleteEmployeeByRecruiter(empId);
    }


    @GetMapping("/stage/{empId}")
    public List<StageDTO> getStagesByEmpId(@PathVariable Long empId){
         return recruiterService.getStagesByEmpId(empId);
    }

    @PostMapping("/stage")
    public StageDTO addStageDetail(@RequestBody StageDTO stageDTO){
        return recruiterService.addStageDetail(stageDTO);
    }

    @PutMapping("/stage")
    public StageDTO updateStageDetail(@RequestBody StageDTO stageDTO){
        return recruiterService.updateStageDetail(stageDTO);
    }

    @DeleteMapping("/stage/{stageId}")
    public void deleteStageDetail(@PathVariable Long stageId){
         recruiterService.deleteStageDetail(stageId);
    }

    private void setClientNameForEmployee(EmployeeDTO employeeDTO) {
        if (employeeDTO.getPositionId() != null) {
            Position position = positionRepo.findById(employeeDTO.getPositionId()).orElse(null);
            if (position != null && position.getClient() != null) {
                employeeDTO.setClientName(position.getClient().getClientName());
            }
        }
    }
}
