package io.pudge.auresto.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuRestoProvince.
 */
@Entity
@Table(name = "au_resto_province")
@Document(indexName = "aurestoprovince")
public class AuRestoProvince implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToOne
    private AuRestoCountry country;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AuRestoProvince name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public AuRestoProvince code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AuRestoCountry getCountry() {
        return country;
    }

    public AuRestoProvince country(AuRestoCountry auRestoCountry) {
        this.country = auRestoCountry;
        return this;
    }

    public void setCountry(AuRestoCountry auRestoCountry) {
        this.country = auRestoCountry;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuRestoProvince auRestoProvince = (AuRestoProvince) o;
        if (auRestoProvince.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoProvince.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoProvince{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
