package com.example.OPMS_2.Service;

import com.example.OPMS_2.DTO.*;
import com.example.OPMS_2.Entity.*;
import com.example.OPMS_2.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private StageRepo stageRepo;

    public Recruiter findByEmail(String emailId) {
        return recruiterRepo.findByEmailId(emailId)
                .orElseThrow(() -> new EntityNotFoundException("Recruiter not found with email: " + emailId));
    }

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

    public List<StageDTO> getStagesByEmpId(Long empId) {
        List<Stage>allStage = stageRepo.findByEmployeeEmpId(empId);
        List<StageDTO>allStageDTO = new ArrayList<>();
        for(Stage stg : allStage){
            StageDTO sdto = new StageDTO();
            sdto.setEmpId(stg.getEmployee().getEmpId());
            sdto.setStageId(stg.getStageId());
            sdto.setStageName(stg.getStageName());
            sdto.setFeedback(stg.getFeedback());
            sdto.setStatus(stg.getStatus());
            allStageDTO.add(sdto);
        }
        return allStageDTO;
    }

    public StageDTO addStageDetail(StageDTO stageDTO) {
        Employee employee = employeeRepo.findById(stageDTO.getEmpId()).orElse(null);
        Stage stage = new Stage();
        stage.setEmployee(employee);
        stage.setStageName(stageDTO.getStageName());
        stage.setFeedback(stageDTO.getFeedback());
        stage.setStatus(stageDTO.getStatus());
        stage.setStageId(stageDTO.getStageId());
        stageRepo.save(stage);
        return stageDTO;
    }

    public StageDTO updateStageDetail(StageDTO stageDTO){
        Stage existingStage = stageRepo.findById(stageDTO.getStageId()).orElse(new Stage());
        existingStage.setStageName(stageDTO.getStageName());
        existingStage.setFeedback(stageDTO.getFeedback());
        existingStage.setStatus(stageDTO.getStatus());

        stageRepo.save(existingStage);
        return stageDTO;
    }

    public void deleteStageDetail(Long stageId){
        Stage existingStage = stageRepo.findById(stageId).orElse(null);
        if(existingStage!=null){
            stageRepo.delete(existingStage);
        }
    }

    public EmployeeDTO addEmployeeByRecruiterId(EmployeeDTO employeeDTO) {
        Recruiter recruiter = recruiterRepo.findById(employeeDTO.getRecruiterId()).orElse(null);
        Position position = positionRepo.findById(employeeDTO.getPositionId()).orElse(null);

        Employee employee = new Employee();
        if(recruiter!=null){
            employee.setRecruiter(recruiter);
        }
        if(position!=null){
            employee.setPosition(position);
        }
        employee.setEmail(employeeDTO.getEmail());
        employee.setTech(employeeDTO.getTech());
        employee.setEmpId(employeeDTO.getEmpId());
        employee.setEmpName(employeeDTO.getEmpName());
        employee.setExperience(employeeDTO.getExperience());

        employeeRepo.save(employee);
        return employeeDTO;
    }

    public EmployeeDTO updateEmployeeByRecruiter(EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepo.findById(employeeDTO.getEmpId()).orElse(new Employee());
        Position position = positionRepo.findById(employeeDTO.getPositionId()).orElse(null);
        if(position!=null){
            existingEmployee.setPosition(position);
        }
        existingEmployee.setExperience(employeeDTO.getExperience());
        existingEmployee.setEmpName(employeeDTO.getEmpName());
        existingEmployee.setTech(employeeDTO.getTech());
        existingEmployee.setEmail(employeeDTO.getEmail());
         employeeRepo.save(existingEmployee);
        return employeeDTO;

    }

    public void deleteEmployeeByRecruiter(Long empId){
        Employee existingEmployee = employeeRepo.findById(empId).orElse(null);
        if(existingEmployee!=null){
            employeeRepo.delete(existingEmployee);

        }

    }
}
