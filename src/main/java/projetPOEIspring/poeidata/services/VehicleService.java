package projetPOEIspring.poeidata.services;

import projetPOEIspring.poeidata.models.Vehicle;

import java.util.List;
public interface VehicleService {

    List<Vehicle> getAll();

    Vehicle getById(Integer id);

    Vehicle createVehicle(Vehicle vehicle);

    void deleteVehicle(Integer id);

    Vehicle updateVehicle(Vehicle vehicle);
}
