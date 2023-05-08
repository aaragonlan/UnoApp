package es.proyecto.uno.service;

import es.proyecto.uno.domain.Tab1;
import es.proyecto.uno.repository.Tab1Repository;
import es.proyecto.uno.service.dto.Tab1DTO;
import es.proyecto.uno.service.mapper.Tab1Mapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tab1}.
 */
@Service
@Transactional
public class Tab1Service {

    private final Logger log = LoggerFactory.getLogger(Tab1Service.class);

    private final Tab1Repository tab1Repository;

    private final Tab1Mapper tab1Mapper;

    public Tab1Service(Tab1Repository tab1Repository, Tab1Mapper tab1Mapper) {
        this.tab1Repository = tab1Repository;
        this.tab1Mapper = tab1Mapper;
    }

    /**
     * Save a tab1.
     *
     * @param tab1DTO the entity to save.
     * @return the persisted entity.
     */
    public Tab1DTO save(Tab1DTO tab1DTO) {
        log.debug("Request to save Tab1 : {}", tab1DTO);
        Tab1 tab1 = tab1Mapper.toEntity(tab1DTO);
        tab1 = tab1Repository.save(tab1);
        return tab1Mapper.toDto(tab1);
    }

    /**
     * Update a tab1.
     *
     * @param tab1DTO the entity to save.
     * @return the persisted entity.
     */
    public Tab1DTO update(Tab1DTO tab1DTO) {
        log.debug("Request to update Tab1 : {}", tab1DTO);
        Tab1 tab1 = tab1Mapper.toEntity(tab1DTO);
        tab1 = tab1Repository.save(tab1);
        return tab1Mapper.toDto(tab1);
    }

    /**
     * Partially update a tab1.
     *
     * @param tab1DTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Tab1DTO> partialUpdate(Tab1DTO tab1DTO) {
        log.debug("Request to partially update Tab1 : {}", tab1DTO);

        return tab1Repository
            .findById(tab1DTO.getId())
            .map(existingTab1 -> {
                tab1Mapper.partialUpdate(existingTab1, tab1DTO);

                return existingTab1;
            })
            .map(tab1Repository::save)
            .map(tab1Mapper::toDto);
    }

    /**
     * Get all the tab1s.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Tab1DTO> findAll() {
        log.debug("Request to get all Tab1s");
        return tab1Repository.findAll().stream().map(tab1Mapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tab1 by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tab1DTO> findOne(Long id) {
        log.debug("Request to get Tab1 : {}", id);
        return tab1Repository.findById(id).map(tab1Mapper::toDto);
    }

    /**
     * Delete the tab1 by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tab1 : {}", id);
        tab1Repository.deleteById(id);
    }
}
