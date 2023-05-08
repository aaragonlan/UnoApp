package es.proyecto.uno.web.rest;

import es.proyecto.uno.repository.Tab1Repository;
import es.proyecto.uno.service.Tab1Service;
import es.proyecto.uno.service.dto.Tab1DTO;
import es.proyecto.uno.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link es.proyecto.uno.domain.Tab1}.
 */
@RestController
@RequestMapping("/api")
public class Tab1Resource {

    private final Logger log = LoggerFactory.getLogger(Tab1Resource.class);

    private static final String ENTITY_NAME = "tab1";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Tab1Service tab1Service;

    private final Tab1Repository tab1Repository;

    public Tab1Resource(Tab1Service tab1Service, Tab1Repository tab1Repository) {
        this.tab1Service = tab1Service;
        this.tab1Repository = tab1Repository;
    }

    /**
     * {@code POST  /tab-1-s} : Create a new tab1.
     *
     * @param tab1DTO the tab1DTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tab1DTO, or with status {@code 400 (Bad Request)} if the tab1 has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tab-1-s")
    public ResponseEntity<Tab1DTO> createTab1(@RequestBody Tab1DTO tab1DTO) throws URISyntaxException {
        log.debug("REST request to save Tab1 : {}", tab1DTO);
        if (tab1DTO.getId() != null) {
            throw new BadRequestAlertException("A new tab1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tab1DTO result = tab1Service.save(tab1DTO);
        return ResponseEntity
            .created(new URI("/api/tab-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tab-1-s/:id} : Updates an existing tab1.
     *
     * @param id the id of the tab1DTO to save.
     * @param tab1DTO the tab1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tab1DTO,
     * or with status {@code 400 (Bad Request)} if the tab1DTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tab1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tab-1-s/{id}")
    public ResponseEntity<Tab1DTO> updateTab1(@PathVariable(value = "id", required = false) final Long id, @RequestBody Tab1DTO tab1DTO)
        throws URISyntaxException {
        log.debug("REST request to update Tab1 : {}, {}", id, tab1DTO);
        if (tab1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tab1DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tab1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Tab1DTO result = tab1Service.update(tab1DTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tab1DTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tab-1-s/:id} : Partial updates given fields of an existing tab1, field will ignore if it is null
     *
     * @param id the id of the tab1DTO to save.
     * @param tab1DTO the tab1DTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tab1DTO,
     * or with status {@code 400 (Bad Request)} if the tab1DTO is not valid,
     * or with status {@code 404 (Not Found)} if the tab1DTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tab1DTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tab-1-s/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tab1DTO> partialUpdateTab1(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tab1DTO tab1DTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tab1 partially : {}, {}", id, tab1DTO);
        if (tab1DTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tab1DTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tab1Repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tab1DTO> result = tab1Service.partialUpdate(tab1DTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tab1DTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tab-1-s} : get all the tab1s.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tab1s in body.
     */
    @GetMapping("/tab-1-s")
    public List<Tab1DTO> getAllTab1s() {
        log.debug("REST request to get all Tab1s");
        return tab1Service.findAll();
    }

    /**
     * {@code GET  /tab-1-s/:id} : get the "id" tab1.
     *
     * @param id the id of the tab1DTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tab1DTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tab-1-s/{id}")
    public ResponseEntity<Tab1DTO> getTab1(@PathVariable Long id) {
        log.debug("REST request to get Tab1 : {}", id);
        Optional<Tab1DTO> tab1DTO = tab1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(tab1DTO);
    }

    /**
     * {@code DELETE  /tab-1-s/:id} : delete the "id" tab1.
     *
     * @param id the id of the tab1DTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tab-1-s/{id}")
    public ResponseEntity<Void> deleteTab1(@PathVariable Long id) {
        log.debug("REST request to delete Tab1 : {}", id);
        tab1Service.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
