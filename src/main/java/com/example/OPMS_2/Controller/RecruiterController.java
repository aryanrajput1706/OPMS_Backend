package com.example.OPMS_2.Controller;

import com.example.OPMS_2.DTO.EmployeeDTO;
import com.example.OPMS_2.DTO.PositionDTO;
import com.example.OPMS_2.DTO.RecruiterDTO;
import com.example.OPMS_2.Entity.Employee;
import com.example.OPMS_2.Service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.OPMS_2.Entity.Recruiter;
import java.util.List;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
     @Autowired
    RecruiterService recruiterService;
     @GetMapping
     public List<Employee> getAllEmployee()
     {
       return recruiterService.getAllEmployee();
     }
    @PostMapping
    public ResponseEntity<Employee> addNewPosition(@RequestBody EmployeeDTO employeeDTO)
    {
        Employee savedEmployee=recruiterService.saveEmployee(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);

    }
    @PutMapping
    public  ResponseEntity<Employee>updateEmployee(@PathVariable Long empId,@RequestBody EmployeeDTO employeeDTO)
    {
        Employee employee=recruiterService.updateEmployee(empId,employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping
    public ResponseEntity<Employee> deleteMapping(@PathVariable Long empId)
    {
        boolean isDeleted=recruiterService.deleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/view-all")
    public List<Recruiter> getAllRecruiter(){
        return recruiterService.getAllRecruiter();
    }
    @PostMapping
    public ResponseEntity<Recruiter> addRecruiter(@RequestBody RecruiterDTO recruiterDTO)
    {
       Recruiter Newrecruiter=recruiterService.addRecruiter(recruiterDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body(Newrecruiter);
    }

    @PutMapping
    public ResponseEntity<Recruiter> updateRecruiter(@PathVariable Long recruiterId,@RequestBody RecruiterDTO recruiterDTO){
         Recruiter recruiter=recruiterService.updateRecruiter(recruiterId,recruiterDTO);
         return ResponseEntity.ok(recruiter);
    }
    @DeleteMapping
    public ResponseEntity<Recruiter> deleteRecruiter(@PathVariable Long recruiterId){
         boolean isDeleted= recruiterService.deleteRecruiter(recruiterId);
         return ResponseEntity.noContent().build(); //Return 204 No Content if deleted successfully
    }

}
