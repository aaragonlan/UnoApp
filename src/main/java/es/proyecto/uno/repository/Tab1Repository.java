package es.proyecto.uno.repository;

import es.proyecto.uno.domain.Tab1;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tab1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Tab1Repository extends JpaRepository<Tab1, Long> {}
