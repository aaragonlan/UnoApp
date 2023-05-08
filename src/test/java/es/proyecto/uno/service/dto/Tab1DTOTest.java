package es.proyecto.uno.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import es.proyecto.uno.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Tab1DTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tab1DTO.class);
        Tab1DTO tab1DTO1 = new Tab1DTO();
        tab1DTO1.setId(1L);
        Tab1DTO tab1DTO2 = new Tab1DTO();
        assertThat(tab1DTO1).isNotEqualTo(tab1DTO2);
        tab1DTO2.setId(tab1DTO1.getId());
        assertThat(tab1DTO1).isEqualTo(tab1DTO2);
        tab1DTO2.setId(2L);
        assertThat(tab1DTO1).isNotEqualTo(tab1DTO2);
        tab1DTO1.setId(null);
        assertThat(tab1DTO1).isNotEqualTo(tab1DTO2);
    }
}
