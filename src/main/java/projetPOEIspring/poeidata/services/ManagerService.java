package projetPOEIspring.poeidata.services;

import projetPOEIspring.poeidata.models.Manager;

import java.util.List;

public interface ManagerService {

    List<Manager> getAll();

    Manager getById(Integer id);

    Manager createManager(Manager manager);

    void deleteManager(Integer id);

    Manager updateManager (Manager manager);
}
