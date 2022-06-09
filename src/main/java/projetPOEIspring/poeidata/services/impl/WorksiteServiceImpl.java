package projetPOEIspring.poeidata.services.impl;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.models.Worksite;
import projetPOEIspring.poeidata.repositories.TechnicianRepository;
import projetPOEIspring.poeidata.repositories.WorksiteRepository;
import projetPOEIspring.poeidata.services.TechnicianService;
import projetPOEIspring.poeidata.services.WorksiteService;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorksiteServiceImpl implements WorksiteService {

    private final WorksiteRepository worksiteRepository;
    private final TechnicianRepository technicianRepository;

    public WorksiteServiceImpl(WorksiteRepository worksiteRepository, TechnicianRepository technicianRepository) {
        this.worksiteRepository = worksiteRepository;
        this.technicianRepository = technicianRepository;
    }

    @Override
    public List<Worksite> getAll() {
        return this.worksiteRepository.findAll(Sort.by("name").ascending());
    }

    @Override
    public Worksite getBydId(Integer id) {
        return this.worksiteRepository.findById(id)
                .orElseThrow(() -> new UnknownResourceException("Unknwon Worksite"));
    }

    @Override
    public Worksite create(Worksite worksite) {
        worksite.setId(null);
        worksite.setAdress(null);
        final Worksite createdWorkSite = this.worksiteRepository.save(worksite);
        new ArrayList<>(createdWorkSite.getTechnicians()).forEach(
                technician -> this.technicianRepository.findById(technician.getId()).ifPresent(
                        tech -> {
                            tech.getWorksites().add(createdWorkSite);
                            this.technicianRepository.saveAndFlush(tech);
                        }
                )
        );
        return this.worksiteRepository.save(createdWorkSite);
    }

    @Override
    public Worksite update(Worksite worksite) {
        Worksite worksiteToUpdate = this.getBydId(worksite.getId());
        worksiteToUpdate.setName(worksite.getName());
        worksiteToUpdate.setAdress(worksite.getAdress());
        worksiteToUpdate.setPrice(worksite.getPrice());
        worksiteToUpdate.setTechnicians(worksite.getTechnicians());
        return this.worksiteRepository.save(worksiteToUpdate);
    }

    @Override
    public void delete(Integer id) {
        Worksite worksiteToDelete = this.getBydId(id);
        this.worksiteRepository.delete(worksiteToDelete);
    }
}
