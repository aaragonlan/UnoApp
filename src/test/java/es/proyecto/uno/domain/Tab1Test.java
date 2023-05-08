package es.proyecto.uno.domain;

import static org.assertj.core.api.Assertions.assertThat;

import es.proyecto.uno.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Tab1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tab1.class);
        Tab1 tab11 = new Tab1();
        tab11.setId(1L);
        Tab1 tab12 = new Tab1();
        tab12.setId(tab11.getId());
        assertThat(tab11).isEqualTo(tab12);
        tab12.setId(2L);
        assertThat(tab11).isNotEqualTo(tab12);
        tab11.setId(null);
        assertThat(tab11).isNotEqualTo(tab12);
    }
}
