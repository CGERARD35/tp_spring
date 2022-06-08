package projetPOEIspring.poeidata.services;

import projetPOEIspring.poeidata.models.Adress;

import java.util.List;

public interface AdressService {

    List<Adress> getAll();
    Adress getById(Integer id);
    Adress create(Adress adress);
    Adress update(Adress adress);
    void delete(Integer id);


}
