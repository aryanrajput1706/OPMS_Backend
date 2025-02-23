package com.example.OPMS_2.Controller;

import com.example.OPMS_2.DTO.EmployeeDTO;
import com.example.OPMS_2.DTO.RecruiterDTO;
import com.example.OPMS_2.Entity.Employee;
import com.example.OPMS_2.Service.RecruiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.OPMS_2.Entity.Recruiter;
import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    @Autowired
    RecruiterService recruiterService;
    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployee()
    {
        return recruiterService.getAllEmployee();
    }
    @PostMapping("/employee")
    public ResponseEntity<Employee> addNewPosition(@RequestBody EmployeeDTO employeeDTO)
    {
       Employee employee= recruiterService.saveEmployee(employeeDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(employee);

    }
    @PutMapping("/employee/{empId}")
    public  ResponseEntity<Employee>updateEmployee(@PathVariable Long empId,@RequestBody EmployeeDTO employeeDTO)
    {
        Employee employee=recruiterService.updateEmployee(empId,employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/employee/{empId}")
    public ResponseEntity<Employee> deleteMapping(@PathVariable Long empId)
    {
        boolean isDeleted=recruiterService.deleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/view-all")
    public List<RecruiterDTO> getAllRecruiter(){
        return recruiterService.getAllRecruiter();
    }
    @PostMapping
    public ResponseEntity<Recruiter> addRecruiter(@RequestBody RecruiterDTO recruiterDTO)
    {
        Recruiter Newrecruiter=recruiterService.addRecruiter(recruiterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Newrecruiter);
    }

    @PutMapping("/{recruiterId}")
    public ResponseEntity<Recruiter> updateRecruiter(@PathVariable Long recruiterId,@RequestBody RecruiterDTO recruiterDTO){
        Recruiter recruiter=recruiterService.updateRecruiter(recruiterId,recruiterDTO);
        return ResponseEntity.ok(recruiter);
    }
    @DeleteMapping("/{recruiterId}")
    public ResponseEntity<Recruiter> deleteRecruiter(@PathVariable Long recruiterId){
        boolean isDeleted= recruiterService.deleteRecruiter(recruiterId);
        return ResponseEntity.noContent().build(); //Return 204 No Content if deleted successfully
    }
    @GetMapping("/employee/selected")
    public List<EmployeeDTO> selectedEmployee()
    {
        return recruiterService.selectedEmployee();
    }
    @GetMapping("/employee/bench")
    public List<EmployeeDTO> employeeOnBench()
    {
        return recruiterService.employeeOnBench();
    }
 //bech employees
   //stage name == not scheduled
}
