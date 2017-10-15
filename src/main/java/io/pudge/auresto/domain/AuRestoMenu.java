package io.pudge.auresto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AuRestoMenu.
 */
@Entity
@Table(name = "au_resto_menu")
@Document(indexName = "aurestomenu")
public class AuRestoMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private AuRestoRestaurant auRestoRestaurant;

    @OneToMany(mappedBy = "auRestoMenu")
    @JsonIgnore
    private Set<AuRestoPhoto> photos = new HashSet<>();

    @OneToMany(mappedBy = "auRestoMenu")
    @JsonIgnore
    private Set<AuRestoFormula> formulas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public AuRestoMenu title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public AuRestoMenu date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public AuRestoRestaurant getAuRestoRestaurant() {
        return auRestoRestaurant;
    }

    public AuRestoMenu auRestoRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.auRestoRestaurant = auRestoRestaurant;
        return this;
    }

    public void setAuRestoRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.auRestoRestaurant = auRestoRestaurant;
    }

    public Set<AuRestoPhoto> getPhotos() {
        return photos;
    }

    public AuRestoMenu photos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
        return this;
    }

    public AuRestoMenu addPhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.add(auRestoPhoto);
        auRestoPhoto.setAuRestoMenu(this);
        return this;
    }

    public AuRestoMenu removePhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.remove(auRestoPhoto);
        auRestoPhoto.setAuRestoMenu(null);
        return this;
    }

    public void setPhotos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
    }

    public Set<AuRestoFormula> getFormulas() {
        return formulas;
    }

    public AuRestoMenu formulas(Set<AuRestoFormula> auRestoFormulas) {
        this.formulas = auRestoFormulas;
        return this;
    }

    public AuRestoMenu addFormula(AuRestoFormula auRestoFormula) {
        this.formulas.add(auRestoFormula);
        auRestoFormula.setAuRestoMenu(this);
        return this;
    }

    public AuRestoMenu removeFormula(AuRestoFormula auRestoFormula) {
        this.formulas.remove(auRestoFormula);
        auRestoFormula.setAuRestoMenu(null);
        return this;
    }

    public void setFormulas(Set<AuRestoFormula> auRestoFormulas) {
        this.formulas = auRestoFormulas;
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
        AuRestoMenu auRestoMenu = (AuRestoMenu) o;
        if (auRestoMenu.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoMenu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoMenu{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
