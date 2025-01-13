package com.example.OPMS_2.Service;

import com.example.OPMS_2.DTO.ClientDTO;
import com.example.OPMS_2.DTO.EmployeeDTO;
import com.example.OPMS_2.DTO.PositionDTO;
import com.example.OPMS_2.DTO.RecruiterDTO;
import com.example.OPMS_2.Entity.Client;
import com.example.OPMS_2.Entity.Employee;
import com.example.OPMS_2.Entity.Position;
import com.example.OPMS_2.Entity.Recruiter;
import com.example.OPMS_2.Repository.ClientRepo;
import com.example.OPMS_2.Repository.EmployeeRepo;
import com.example.OPMS_2.Repository.PositionRepo;
import com.example.OPMS_2.Repository.RecruiterRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RecruiterService {
    @Autowired
    private RecruiterRepo recruiterRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PositionRepo positionRepo;
    @Autowired
    private RecruiterDTO recruiterDTO;

    public List<Recruiter> getAllRecruiter() {
        return recruiterRepo.findAll();
    }

    public List<Employee> getAllEmployee() {
        return employeeRepo.findAll();
    }

    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Position position = positionRepo.findById(employeeDTO.getPositionId()).orElse(null);
        Recruiter recruiter = recruiterRepo.findById(employeeDTO.getRecruiterId()).orElse(null);
        Employee emp = new Employee();
        emp.setEmpName(employeeDTO.getEmpName());
        emp.setTech(employeeDTO.getTech());
        emp.setExperience(employeeDTO.getExperience());
        emp.setPosition(position);
        emp.setRecruiter(recruiter);
        return employeeRepo.save(emp);
        //update , delete employee


    }


    public Recruiter addRecruiter(RecruiterDTO recruiterDTO) {
        Recruiter recruiter = new Recruiter();
        recruiter.setRecruiterName(recruiterDTO.getRecruiterName());
        recruiter.setEmailId(recruiterDTO.getEmailId());
        recruiter.setContactNo(recruiterDTO.getContactNo());
        return recruiterRepo.save(recruiter);

    }


    public Recruiter updateRecruiter(Long recruiterId, RecruiterDTO recruiterDTO) {
        Recruiter recruiter = recruiterRepo.findById(recruiterDTO.getRecruiterId()).orElse(null);

        recruiter.setRecruiterName(recruiterDTO.getRecruiterName());
        recruiter.setContactNo(recruiterDTO.getContactNo());
        recruiter.setEmailId(recruiterDTO.getEmailId());
        return recruiterRepo.save(recruiter);

    }

    public boolean deleteRecruiter(Long recruiterId) {
        if(recruiterRepo.existsById(recruiterId))
        {
            recruiterRepo.deleteById(recruiterId);
            return true;
        }

            return false;
    }

    public Employee updateEmployee(Long empId, EmployeeDTO employeeDTO) {
        Employee employee=employeeRepo.findById(employeeDTO.getEmpId()).orElse(null);
        employee.setEmpName(employeeDTO.getEmpName());
        employee.setStage(employeeDTO.getStage());
        employee.setTech(employeeDTO.getTech());
        employee.setExperience(employeeDTO.getExperience());

        return employeeRepo.save(employee);
    }

    public boolean deleteEmployee(Long empId) {
        if(employeeRepo.existsById(empId)) {
            employeeRepo.deleteById(empId);
            return true;
        }
        return false;
    }
}
