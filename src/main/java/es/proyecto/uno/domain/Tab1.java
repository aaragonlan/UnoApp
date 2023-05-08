package es.proyecto.uno.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tab1.
 */
@Entity
@Table(name = "tab_1")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tab1 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "col_1")
    private String col1;

    @Column(name = "col_2")
    private String col2;

    @Column(name = "col_3")
    private String col3;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tab1 id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCol1() {
        return this.col1;
    }

    public Tab1 col1(String col1) {
        this.setCol1(col1);
        return this;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return this.col2;
    }

    public Tab1 col2(String col2) {
        this.setCol2(col2);
        return this;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return this.col3;
    }

    public Tab1 col3(String col3) {
        this.setCol3(col3);
        return this;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tab1)) {
            return false;
        }
        return id != null && id.equals(((Tab1) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tab1{" +
            "id=" + getId() +
            ", col1='" + getCol1() + "'" +
            ", col2='" + getCol2() + "'" +
            ", col3='" + getCol3() + "'" +
            "}";
    }
}
