package com.example.OPMS_2.Controller;

import com.example.OPMS_2.DTO.ClientDTO;
import com.example.OPMS_2.DTO.EmployeeDTO;
import com.example.OPMS_2.DTO.RecruiterDTO;
import com.example.OPMS_2.DTO.PositionDTO;
import com.example.OPMS_2.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //Position

    @GetMapping("/position")
    public List<PositionDTO> getPosition(){
        return adminService.getPosition();
    }

    @PostMapping("/add-position")
    public PositionDTO addPosition(@RequestBody PositionDTO positionDTO){
        PositionDTO newPositionDTO = adminService.addPosition(positionDTO);
        return newPositionDTO;
    }

    @PutMapping("/position")
    public PositionDTO updatePosition(@RequestBody PositionDTO positionDTO){
        PositionDTO updatedPosition = adminService.updatePosition(positionDTO);
        return updatedPosition;
    }

    @DeleteMapping("/position/{id}")
    public String deletePosition(@PathVariable Long id){
        try{
            adminService.deletePosition(id);
            return "Position Deleted Successfully";
        } catch (Exception e) {
            return "Error in Deletion";
        }
    }

    @GetMapping("/position/count")
    public Long requirementCount(){
        return adminService.requirementCount();
    }

    @GetMapping("/position/filled")
    public Long getFilledPosition(){
        return adminService.getFilledPosition();
    }

    @GetMapping("/position/remaining")
    public Long getRemainingPosition(){
        return adminService.requirementCount() - adminService.getFilledPosition();
    }

    //Employees

    @GetMapping("/employee")
    public List<EmployeeDTO> getEmployee(){
        return adminService.getEmployee();
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id){
        return adminService.getEmployeeById(id);
    }

    //Clients

    @GetMapping("/client-count")
    public Long getTotalClient(){
        return adminService.getCountOfClient();
    }

    @GetMapping("/client")
    public List<ClientDTO> getClient(){
        return adminService.getClient();
    }

    @GetMapping("/client/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return adminService.getClientById(id);
    }

    @PostMapping("/add-client")
    public ClientDTO addClient(@RequestBody ClientDTO clientDTO){
        return adminService.addClient(clientDTO);
    }

    @PutMapping("/update-client")
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO){
        return adminService.updateClient(clientDTO);
    }

    @DeleteMapping("/client/{id}")
    public String deleteClient(@PathVariable Long id){
        try{
            adminService.deleteClient(id);
            return "Position Deleted Successfully";
        } catch (Exception e) {
            return "Error in Deletion";
        }
    }

    //RecruitersAPI

    @GetMapping("/recruiter")
    public List<RecruiterDTO> getRecruiter(){
        return adminService.getRecruiter();
    }

    @GetMapping("/recruiter/{id}")
    public RecruiterDTO getRecruiterById(@PathVariable Long id){
        return adminService.getRecruiterById(id);
    }

    @PostMapping("/add-recruiter")
    public RecruiterDTO addRecruiter(@RequestBody RecruiterDTO recruiterDTO){
        return adminService.addRecruiter(recruiterDTO);
    }

    @PutMapping("/update-recruiter")
    public RecruiterDTO updateRecruiter(@RequestBody RecruiterDTO recruiterDTO){
        return adminService.updateRecruiter(recruiterDTO);
    }

    @DeleteMapping("/recruiter/{id}")
    public String deleteRecruiter(@PathVariable Long id){
        try{
            adminService.deleteRecruiter(id);
            return "Position Deleted Successfully";
        } catch (Exception e) {
            return "Error in Deletion";
        }
    }

}
