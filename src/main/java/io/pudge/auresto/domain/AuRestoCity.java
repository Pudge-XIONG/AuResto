package io.pudge.auresto.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuRestoCity.
 */
@Entity
@Table(name = "au_resto_city")
@Document(indexName = "aurestocity")
public class AuRestoCity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "post_code")
    private String postCode;

    @ManyToOne
    private AuRestoProvince province;

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

    public AuRestoCity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostCode() {
        return postCode;
    }

    public AuRestoCity postCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public AuRestoProvince getProvince() {
        return province;
    }

    public AuRestoCity province(AuRestoProvince auRestoProvince) {
        this.province = auRestoProvince;
        return this;
    }

    public void setProvince(AuRestoProvince auRestoProvince) {
        this.province = auRestoProvince;
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
        AuRestoCity auRestoCity = (AuRestoCity) o;
        if (auRestoCity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoCity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoCity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", postCode='" + getPostCode() + "'" +
            "}";
    }
}
