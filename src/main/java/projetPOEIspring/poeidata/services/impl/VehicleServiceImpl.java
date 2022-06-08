package projetPOEIspring.poeidata.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import projetPOEIspring.poeidata.repositories.VehicleRepository;
import projetPOEIspring.poeidata.models.Vehicle;
import projetPOEIspring.poeidata.services.VehicleService;

import java.util.List;

public class VehicleServiceImpl implements VehicleService {

    Logger log = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getAll() {
        return null;
    }

    @Override
    public Vehicle getById(Integer id) {
        return null;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        return null;
    }

    @Override
    public void deleteVehicle(Integer id) {

    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle) {
        return null;
    }
}
