package com.example.OPMS_2.Service;

import com.example.OPMS_2.DTO.ClientDTO;
import com.example.OPMS_2.DTO.EmployeeDTO;
import com.example.OPMS_2.DTO.PositionDTO;
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
public class AdminService {

    @Autowired
    PositionRepo positionRepo;

    @Autowired
    RecruiterRepo recruiterRepo;

    @Autowired
    ClientRepo clientRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    public List<PositionDTO> getPosition() {
        List<Position> positionList = positionRepo.findAll();
        List<PositionDTO> positionDTOList = new ArrayList<>();

        for(Position position : positionList){
            PositionDTO positionDTO = new PositionDTO();

            positionDTO.setPositionId(position.getPositionId());
            positionDTO.setTech(position.getTech());
            positionDTO.setExperience(position.getExperience());
            positionDTO.setCount(position.getCount());
            positionDTO.setFilled(position.getFilled());
            positionDTO.setEndDate(position.getEndDate());
            positionDTO.setStartDate(position.getStartDate());
            positionDTO.setCost(position.getCost());
            positionDTO.setClientId(position.getClient().getClientId());

            List<Recruiter> recruiterList = position.getRecruiters().stream().toList();
            List<Long> recruiterIdList = new ArrayList<>();

            for(Recruiter recruiter : recruiterList){
                recruiterIdList.add(recruiter.getRecruiterId());
            }

            positionDTO.setRecruiters(recruiterIdList);
            positionDTOList.add(positionDTO);
        }

        return positionDTOList;
    }


    public PositionDTO addPosition(PositionDTO positionDTO) {
        Position newPosition = new Position();
        List<Recruiter> recruiters = new ArrayList<>();
        Client client = clientRepo.findById(positionDTO.getClientId()).orElse(null);

        newPosition.setClient(client);
        newPosition.setTech(positionDTO.getTech());
        newPosition.setExperience(positionDTO.getExperience());
        newPosition.setCost(positionDTO.getCost());
        newPosition.setCount(positionDTO.getCount());
        newPosition.setFilled(positionDTO.getFilled());
        newPosition.setStartDate(positionDTO.getStartDate());
        newPosition.setEndDate(positionDTO.getEndDate());

        for(Long id : positionDTO.getRecruiters()){
            Recruiter assignedRecruiter = recruiterRepo.findById(id).orElse(null);
            recruiters.add(assignedRecruiter);
        }

        newPosition.setRecruiters(recruiters);
        positionRepo.save(newPosition);
        return positionDTO;
    }

    public PositionDTO updatePosition(PositionDTO positionDTO) {
        Position existingPosition = positionRepo.findById(positionDTO.getPositionId()).orElse(new Position());
        List<Recruiter> recruiters = new ArrayList<>();
        Client client = clientRepo.findById(positionDTO.getClientId()).orElse(null);

        existingPosition.setClient(client);
        existingPosition.setTech(positionDTO.getTech());
        existingPosition.setExperience(positionDTO.getExperience());
        existingPosition.setCount(positionDTO.getCount());
        existingPosition.setFilled(positionDTO.getFilled());
        existingPosition.setStartDate(positionDTO.getStartDate());
        existingPosition.setEndDate(positionDTO.getEndDate());

        for(Long id : positionDTO.getRecruiters()){
            Recruiter assignedRecruiter = recruiterRepo.findById(id).orElse(null);
            recruiters.add(assignedRecruiter);
        }

        existingPosition.setRecruiters(recruiters);

        positionRepo.save(existingPosition);

        return positionDTO;
    }

    public void deletePosition(Long id) {
        Position position = positionRepo.findById(id).orElseThrow(()->new RuntimeException("Position Not found"));
        positionRepo.delete(position);
    }

    public Long requirementCount() {
        Long sum = 0L;
        List<Position> posList = positionRepo.findAll();

        for(Position position : posList){
            sum += position.getCount();
        }
        return sum;
    }

    public Long getFilledPosition() {
        Long filled = 0L;
        List<Position> posList = positionRepo.findAll();

        for(Position position : posList){
            filled += position.getFilled();
        }
        return filled;
    }

    public Long getCountOfClient() {
        return clientRepo.findAll().stream().count();
    }


    public List<EmployeeDTO> getEmployee() {
        List<Employee> employeeList = new ArrayList<Employee>();
        List<EmployeeDTO> employeeResponseList = new ArrayList<EmployeeDTO>();
        employeeList.addAll(employeeRepo.findAll());

        for(Employee employee : employeeList){
            EmployeeDTO employeeDTO = new EmployeeDTO();

            employeeDTO.setEmpName(employee.getEmpName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setEmpId(employee.getEmpId());
            employeeDTO.setExperience(employee.getExperience());
            employeeDTO.setTech(employee.getTech());
            employeeDTO.setFeedback(employee.getFeedback());
            employeeDTO.setStage(employee.getStage());
            employeeDTO.setRecruiterId(employee.getRecruiter().getRecruiterId());
            employeeDTO.setPositionId(employee.getPosition().getPositionId());

            employeeResponseList.add(employeeDTO);
        }

        return employeeResponseList;
    }

    public List<ClientDTO> getClient() {
        List<Client> clientList = new ArrayList<Client>();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        clientList.addAll(clientRepo.findAll());

        for(Client client : clientList){
            ClientDTO clientDTO = new ClientDTO();
            List<Long> positions = new ArrayList<>();

            clientDTO.setClientId(client.getClientId());
            clientDTO.setClientEmail(client.getClientEmail());
            clientDTO.setClientPhone(client.getClientPhone());
            clientDTO.setClientName(client.getClientName());
            clientDTO.setClientAddress(client.getClientAddress());

            for(Position position : client.getPositions()){
                positions.add(position.getPositionId());
            }

            clientDTO.setPositions(positions);


            clientDTOList.add(clientDTO);
        }

        return clientDTOList;
    }
}
