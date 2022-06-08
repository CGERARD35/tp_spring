package projetPOEIspring.poeidata.services.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.models.Adress;
import projetPOEIspring.poeidata.repositories.AdressRepository;
import projetPOEIspring.poeidata.services.AdressService;

import java.util.List;

@Service
public class AdressServiceImpl implements AdressService {

    private final AdressRepository adressRepository;

    public AdressServiceImpl(AdressRepository adressRepository) {
        this.adressRepository = adressRepository;
    }

    @Override
    public List<Adress> getAll() {
        return this.adressRepository.findAll(Sort.by("city"));
    }

    @Override
    public Adress getById(Integer id) {
        return this.adressRepository.findById(id)
                .orElseThrow(UnknownResourceException::new);
    }

    @Override
    public Adress create(Adress adress) {
        adress.setId(null);
        return this.adressRepository.save(adress);
    }

    @Override
    public Adress update(Adress adress) {
        Adress adressToUpdate = this.getById(adress.getId());
        adressToUpdate.setId(adress.getId());
        adressToUpdate.setCity(adress.getCity());
        adressToUpdate.setStreet(adress.getStreet());
        adressToUpdate.setNumber(adress.getNumber());
        return adressToUpdate;
    }

    @Override
    public void delete(Integer id) {
        Adress adressToUpdate = this.getById(id);
        this.adressRepository.delete(adressToUpdate);
    }
}
