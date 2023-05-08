package es.proyecto.uno.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link es.proyecto.uno.domain.Tab1} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tab1DTO implements Serializable {

    private Long id;

    private String col1;

    private String col2;

    private String col3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tab1DTO)) {
            return false;
        }

        Tab1DTO tab1DTO = (Tab1DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tab1DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tab1DTO{" +
            "id=" + getId() +
            ", col1='" + getCol1() + "'" +
            ", col2='" + getCol2() + "'" +
            ", col3='" + getCol3() + "'" +
            "}";
    }
}
