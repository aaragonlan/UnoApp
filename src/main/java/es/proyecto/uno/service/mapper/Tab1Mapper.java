package es.proyecto.uno.service.mapper;

import es.proyecto.uno.domain.Tab1;
import es.proyecto.uno.service.dto.Tab1DTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tab1} and its DTO {@link Tab1DTO}.
 */
@Mapper(componentModel = "spring")
public interface Tab1Mapper extends EntityMapper<Tab1DTO, Tab1> {}
