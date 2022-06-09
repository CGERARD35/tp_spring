package projetPOEIspring.poeidata.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projetPOEIspring.poeidata.exceptions.CantCreateException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.repositories.VehicleRepository;
import projetPOEIspring.poeidata.models.Vehicle;
import projetPOEIspring.poeidata.services.VehicleService;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getAll() {
        return this.vehicleRepository.findAll(Sort.by("id").ascending());
    }

    @Override
    public Vehicle getById(Integer id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("No Vehicle found for the given ID"));
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        if (vehicle.getYearOfConstruction().length() > 5) {
            throw new CantCreateException("Year of construction cannot exceed 5 characters");
        } else if (vehicle.getPlateNumber().length() > 30) {
            throw new CantCreateException("Plate number cannot exceed 5 characters");
        } else {
            return this.vehicleRepository.save(vehicle);
        }
    }

    @Override
    public void deleteVehicle(Integer id) {
        Vehicle vehicleToDelete = this.getById(id);

        this.vehicleRepository.delete(vehicleToDelete);
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        Vehicle vehicleToUpdate = this.getById(vehicle.getId());
        vehicleToUpdate.setBrand(vehicle.getBrand());
        vehicleToUpdate.setPlateNumber(vehicle.getPlateNumber());
        vehicleToUpdate.setYearOfConstruction(vehicle.getYearOfConstruction());
        return this.vehicleRepository.save(vehicleToUpdate);
    }
}
