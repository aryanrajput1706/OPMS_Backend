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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RecruiterService {

    @Autowired
    private RecruiterRepo recruiterRepo;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PositionRepo positionRepo;

    private RecruiterDTO recruiterDTO;

    public List<EmployeeDTO> getAllEmployee() {
        List<Employee> allEmployeeList = employeeRepo.findAll();
        List<EmployeeDTO> allEmployeeDtoList = new ArrayList<>();
        for (Employee e : allEmployeeList) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmail(e.getEmail());
            employeeDTO.setEmpName(e.getEmpName());
            employeeDTO.setEmpId(e.getEmpId());
            employeeDTO.setStage(e.getStage());
            employeeDTO.setTech(e.getTech());
            employeeDTO.setExperience(e.getExperience());
            employeeDTO.setFeedback(e.getFeedback());
            allEmployeeDtoList.add(employeeDTO);
        }
        return allEmployeeDtoList;
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee newEmployee = new Employee();

        // Fetch related Position and Recruiter entities
        Position position = positionRepo.findById(employeeDTO.getPositionId()).orElse(null);
        Recruiter recruiter = recruiterRepo.findById(employeeDTO.getRecruiterId()).orElse(null);

        // Map fields from EmployeeDTO to Employee entity
        newEmployee.setEmpName(employeeDTO.getEmpName());
        newEmployee.setEmail(employeeDTO.getEmail());
        newEmployee.setTech(employeeDTO.getTech());
        newEmployee.setExperience(employeeDTO.getExperience());
        newEmployee.setStage(employeeDTO.getStage());
        newEmployee.setFeedback(employeeDTO.getFeedback());
        newEmployee.setPosition(position);
        newEmployee.setRecruiter(recruiter);

        // Save the Employee entity
        Employee savedEmployee = employeeRepo.save(newEmployee);

        // Map saved entity back to EmployeeDTO
        EmployeeDTO savedEmployeeDTO = new EmployeeDTO();
        savedEmployeeDTO.setEmpId(savedEmployee.getEmpId());
        savedEmployeeDTO.setEmpName(savedEmployee.getEmpName());
        savedEmployeeDTO.setEmail(savedEmployee.getEmail());
        savedEmployeeDTO.setTech(savedEmployee.getTech());
        savedEmployeeDTO.setExperience(savedEmployee.getExperience());
        savedEmployeeDTO.setStage(savedEmployee.getStage());
        savedEmployeeDTO.setFeedback(savedEmployee.getFeedback());

        if (savedEmployee.getPosition() != null) {
            savedEmployeeDTO.setPositionId(savedEmployee.getPosition().getPositionId());
        }
        if (savedEmployee.getRecruiter() != null) {
            savedEmployeeDTO.setRecruiterId(savedEmployee.getRecruiter().getRecruiterId());
            savedEmployeeDTO.setRecruiterName(savedEmployee.getRecruiter().getRecruiterName());
        }

        return savedEmployeeDTO;
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

    public List<EmployeeDTO> getEmployeesByRecruiterId(Long recruiterId) {
        List<Employee> employees = employeeRepo.findByRecruiterRecruiterId(recruiterId);

        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO();

            employeeDTO.setEmpId(employee.getEmpId());
            employeeDTO.setEmpName(employee.getEmpName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setTech(employee.getTech());
            employeeDTO.setExperience(employee.getExperience());
            employeeDTO.setStage(employee.getStage());
            employeeDTO.setFeedback(employee.getFeedback());

            if (employee.getRecruiter() != null) {
                employeeDTO.setRecruiterId(employee.getRecruiter().getRecruiterId());
                employeeDTO.setRecruiterName(employee.getRecruiter().getRecruiterName());
            }
            if (employee.getPosition() != null) {
                employeeDTO.setPositionId(employee.getPosition().getPositionId());
            }

            employeeDTOs.add(employeeDTO);
        }

        return employeeDTOs;
    }

}
