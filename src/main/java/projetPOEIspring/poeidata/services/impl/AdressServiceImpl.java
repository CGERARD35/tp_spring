package projetPOEIspring.poeidata.services.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
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
                .orElseThrow(()-> new RuntimeException());
    }

    @Override
    public Adress create(Adress adress) {
        return null;
    }

    @Override
    public Adress update(Adress adress) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
