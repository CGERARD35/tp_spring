package projetPOEIspring.poeidata.services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projetPOEIspring.poeidata.exceptions.NameException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.models.*;
import projetPOEIspring.poeidata.repositories.TechnicianRepository;
import projetPOEIspring.poeidata.services.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@Qualifier("technicianService")
public class TechnicianServiceImpl implements TechnicianService {

    Logger log = LoggerFactory.getLogger(TechnicianServiceImpl.class);

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private WorksiteService worksiteService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private AdressService adressService;

    @Override
    public List<Technician> getAll() {
        return this.technicianRepository.findAll(Sort.by("lastname").ascending());
    }

    @Override
    public Technician getById(Integer id) {
        return technicianRepository.findById(id).orElseThrow(
                () -> new UnknownResourceException("No Technician found for the given ID."));
    }

    @Override
    public Technician createTechnician(Technician technician) {
        log.debug("Attempting to save in DB.");
        if (
                technician.getFirstname().length() > 255 || technician.getLastname().length() > 255
        ) {
            throw new NameException("Names cannot exceed 255 characters.");
        } else {
            return this.technicianRepository.save(technician);
        }
    }

    @Override
    public void deleteTechnician(Integer id) {
        Technician technicianToDelete = this.getById(id);
        this.technicianRepository.delete(technicianToDelete);
    }


    @Override
    public Technician updateTechnician(Technician technician) {
        log.debug("Attempting to update technician {}", technician.getId());
        Technician existingTechnician = this.getById(technician.getId());
        existingTechnician.setLastname(technician.getLastname());
        existingTechnician.setFirstname(technician.getFirstname());
        existingTechnician.setAdress(technician.getAdress());
        existingTechnician.setAge(technician.getAge());
        Manager manager = this.managerService.getById(technician.getManager().getId());
        existingTechnician.setManager(manager);
        Vehicle vehicle = this.vehicleService.getById(technician.getVehicle().getId());
        existingTechnician.setVehicle(vehicle);
        Adress adress = this.adressService.getById(technician.getAdress().getId());
        existingTechnician.setVehicle(vehicle);
        return this.technicianRepository.save(existingTechnician);
    }

}
