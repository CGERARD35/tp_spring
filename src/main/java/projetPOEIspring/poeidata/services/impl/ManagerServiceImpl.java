package projetPOEIspring.poeidata.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projetPOEIspring.poeidata.exceptions.NotAllowedToDeleteManagerException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.models.Manager;
import projetPOEIspring.poeidata.models.Technician;
import projetPOEIspring.poeidata.repositories.ManagerRepository;
import projetPOEIspring.poeidata.services.ManagerService;
import projetPOEIspring.poeidata.services.TechnicianService;

import java.util.List;

@Service
@Qualifier("managerService")
public class ManagerServiceImpl implements ManagerService {

    Logger log = LoggerFactory.getLogger(ManagerServiceImpl.class);

    @Autowired
    private ManagerRepository managerRepository;

    private TechnicianService technicianService;

    @Override
    public List<Manager> getAll() {
        return this.managerRepository.findAll(Sort.by("Lastname").ascending());
    }

    @Override
    public Manager getById(Integer id) {
        return this.managerRepository.findById(id).orElseThrow(
                () -> new UnknownResourceException("No Manager found for the given ID"));
    }

    @Override
    public Manager createManager(Manager manager) {
        log.debug("Attempting to save in DB...");
        return this.managerRepository.save(manager);
    }

    @Override
    public void deleteManager(Integer id) {
        Manager managerToDelete = this.getById(id);
        if (this.canDeleteManager(managerToDelete)) {
            this.managerRepository.delete(managerToDelete);
        }else {
            throw new NotAllowedToDeleteManagerException("The given manager still has technicians.");
        }
    }

    private boolean canDeleteManager(Manager manager) {
        return (null == manager.getTechnicians() || manager.getTechnicians().isEmpty());
    }
    @Override
    public Manager updateManager(Manager manager) {
        log.debug("Attempting to update manager {}", manager.getId());
        Manager existingManager = this.getById(manager.getId());
        existingManager.setLastname(manager.getLastname());
        existingManager.setFirstname(manager.getFirstname());
        existingManager.setPhone(manager.getPhone());
        existingManager.setMobilePhone(manager.getMobilePhone());
        return this.managerRepository.save(existingManager);
    }
}
