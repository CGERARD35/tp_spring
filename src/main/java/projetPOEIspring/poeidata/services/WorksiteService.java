package projetPOEIspring.poeidata.services;

import projetPOEIspring.poeidata.models.Worksite;

import java.util.List;

public interface WorksiteService {

    List<Worksite> getAll();
    Worksite getBydId(Integer id);
    Worksite create(Worksite worksite);
    Worksite update(Worksite worksite);
    void delete(Integer id);

}
