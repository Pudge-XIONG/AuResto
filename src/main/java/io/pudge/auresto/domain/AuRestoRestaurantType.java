package io.pudge.auresto.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuRestoRestaurantType.
 */
@Entity
@Table(name = "au_resto_restaurant_type")
@Document(indexName = "aurestorestauranttype")
public class AuRestoRestaurantType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private AuRestoRestaurant auRestoRestaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public AuRestoRestaurantType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public AuRestoRestaurantType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuRestoRestaurant getAuRestoRestaurant() {
        return auRestoRestaurant;
    }

    public AuRestoRestaurantType auRestoRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.auRestoRestaurant = auRestoRestaurant;
        return this;
    }

    public void setAuRestoRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.auRestoRestaurant = auRestoRestaurant;
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
        AuRestoRestaurantType auRestoRestaurantType = (AuRestoRestaurantType) o;
        if (auRestoRestaurantType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoRestaurantType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoRestaurantType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
