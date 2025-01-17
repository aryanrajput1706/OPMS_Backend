package com.example.OPMS_2.Controller;

import com.example.OPMS_2.DTO.EmployeeDTO;
import com.example.OPMS_2.DTO.PositionDTO;
import com.example.OPMS_2.DTO.RecruiterDTO;
import com.example.OPMS_2.Entity.Employee;
import com.example.OPMS_2.Entity.Position;
import com.example.OPMS_2.Repository.EmployeeRepo;
import com.example.OPMS_2.Repository.PositionRepo;
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

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployee() {
        List<EmployeeDTO> employeeDTOs = recruiterService.getAllEmployee();

        // Populate client name for each employee
        for (EmployeeDTO employeeDTO : employeeDTOs) {
            setClientNameForEmployee(employeeDTO);
        }

        return employeeDTOs;
    }

    @PostMapping("/{recruiterId}/employees")
    public ResponseEntity<EmployeeDTO> addNewPosition(@PathVariable Long recruiterId, @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO employee = recruiterService.addEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @PutMapping("/employee/{empid}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long empId, @RequestBody EmployeeDTO employeeDTO) {
        Employee employee = recruiterService.updateEmployee(empId, employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/employee/{empid}")
    public ResponseEntity<Employee> deleteMapping(@PathVariable Long empId) {
        boolean isDeleted = recruiterService.deleteEmployee(empId);
        return ResponseEntity.noContent().build();
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
