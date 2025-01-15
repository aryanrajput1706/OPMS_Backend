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

    public List<RecruiterDTO> getAllRecruiter() {
        List<Recruiter>allRecruiterList=recruiterRepo.findAll();
        List<RecruiterDTO>allRecruiterDtoList=new ArrayList<>();
        for(Recruiter r:allRecruiterList)
        {
            RecruiterDTO recruiterDTO1=new RecruiterDTO();
            recruiterDTO1.setRecruiterId(r.getRecruiterId());
            recruiterDTO1.setRecruiterName(r.getRecruiterName());
            recruiterDTO1.setContactNo(r.getContactNo());
            recruiterDTO1.setEmailId(r.getEmailId());
            allRecruiterDtoList.add(recruiterDTO1);
        }
        return allRecruiterDtoList;
    }

    public List<EmployeeDTO> getAllEmployee() {
        List<Employee>allEmployeeList=employeeRepo.findAll();
        List<EmployeeDTO>allEmployeeDtoList=new ArrayList<>();
        for(Employee e:allEmployeeList)
        {
            EmployeeDTO employeeDTO=new EmployeeDTO();
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

    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Position position = positionRepo.findById(employeeDTO.getPositionId()).orElse(null);
        Recruiter recruiter = recruiterRepo.findById(employeeDTO.getRecruiterId()).orElse(null);
        Employee emp = new Employee();
        emp.setEmpName(employeeDTO.getEmpName());
        emp.setTech(employeeDTO.getTech());
        emp.setExperience(employeeDTO.getExperience());
        emp.setEmail(employeeDTO.getEmail());
        emp.setStage(employeeDTO.getStage());
        emp.setFeedback(employeeDTO.getFeedback());
        if(position!=null)
        emp.setPosition(position);
        if(recruiter!=null)
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


    public List<EmployeeDTO> selectedEmployee() {
        List<Employee>selectedEmployeeList=employeeRepo.findByStage("selected");
        List<EmployeeDTO>selectedEmployeeDto=new ArrayList<>();

        for(Employee e:selectedEmployeeList)
        {
                EmployeeDTO employeeDTO=new EmployeeDTO();
                employeeDTO.setEmpId(e.getEmpId());
                employeeDTO.setEmail(e.getEmail());
                employeeDTO.setTech(e.getTech());
                employeeDTO.setStage(e.getStage());
                employeeDTO.setFeedback(e.getFeedback());
                employeeDTO.setEmpName(e.getEmpName());
                employeeDTO.setExperience(e.getExperience());
                selectedEmployeeDto.add(employeeDTO);
        }
        return selectedEmployeeDto;
    }

    public List<EmployeeDTO> employeeOnBench() {
        List<Employee>benchEmployeeList=employeeRepo.findByStage("not-scheduled");
        List<EmployeeDTO>benchEmployeeDto=new ArrayList<>();

        for(Employee e:benchEmployeeList)
        {
            EmployeeDTO employeeDTO=new EmployeeDTO();
            employeeDTO.setEmpId(e.getEmpId());
            employeeDTO.setEmail(e.getEmail());
            employeeDTO.setTech(e.getTech());
            employeeDTO.setStage(e.getStage());
            employeeDTO.setEmpName(e.getEmpName());
            employeeDTO.setExperience(e.getExperience());
            benchEmployeeDto.add(employeeDTO);
        }
        return benchEmployeeDto;
    }
}
