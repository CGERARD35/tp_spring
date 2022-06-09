package projetPOEIspring.poeidata.services;

import projetPOEIspring.poeidata.models.Technician;

import java.util.List;

public interface TechnicianService {

    List<Technician> getAll();

    Technician getById(Integer id);

    Technician createTechnician(Technician technician);

    void deleteTechnician(Integer id);

    Technician updateTechnician (Technician technician);

}
