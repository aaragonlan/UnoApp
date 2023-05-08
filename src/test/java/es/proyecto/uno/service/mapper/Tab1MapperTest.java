package es.proyecto.uno.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Tab1MapperTest {

    private Tab1Mapper tab1Mapper;

    @BeforeEach
    public void setUp() {
        tab1Mapper = new Tab1MapperImpl();
    }
}
