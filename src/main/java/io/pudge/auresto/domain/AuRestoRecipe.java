package io.pudge.auresto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AuRestoRecipe.
 */
@Entity
@Table(name = "au_resto_recipe")
@Document(indexName = "aurestorecipe")
public class AuRestoRecipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private AuRestoFormula auRestoFormula;

    @OneToMany(mappedBy = "auRestoRecipe")
    @JsonIgnore
    private Set<AuRestoPhoto> photos = new HashSet<>();

    @ManyToOne
    private AuRestoRecipeType type;

    @ManyToOne
    private AuRestoOrder auRestoOrder;

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

    public AuRestoRecipe name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AuRestoRecipe description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public AuRestoRecipe comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getPrice() {
        return price;
    }

    public AuRestoRecipe price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AuRestoFormula getAuRestoFormula() {
        return auRestoFormula;
    }

    public AuRestoRecipe auRestoFormula(AuRestoFormula auRestoFormula) {
        this.auRestoFormula = auRestoFormula;
        return this;
    }

    public void setAuRestoFormula(AuRestoFormula auRestoFormula) {
        this.auRestoFormula = auRestoFormula;
    }

    public Set<AuRestoPhoto> getPhotos() {
        return photos;
    }

    public AuRestoRecipe photos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
        return this;
    }

    public AuRestoRecipe addPhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.add(auRestoPhoto);
        auRestoPhoto.setAuRestoRecipe(this);
        return this;
    }

    public AuRestoRecipe removePhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.remove(auRestoPhoto);
        auRestoPhoto.setAuRestoRecipe(null);
        return this;
    }

    public void setPhotos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
    }

    public AuRestoRecipeType getType() {
        return type;
    }

    public AuRestoRecipe type(AuRestoRecipeType auRestoRecipeType) {
        this.type = auRestoRecipeType;
        return this;
    }

    public void setType(AuRestoRecipeType auRestoRecipeType) {
        this.type = auRestoRecipeType;
    }

    public AuRestoOrder getAuRestoOrder() {
        return auRestoOrder;
    }

    public AuRestoRecipe auRestoOrder(AuRestoOrder auRestoOrder) {
        this.auRestoOrder = auRestoOrder;
        return this;
    }

    public void setAuRestoOrder(AuRestoOrder auRestoOrder) {
        this.auRestoOrder = auRestoOrder;
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
        AuRestoRecipe auRestoRecipe = (AuRestoRecipe) o;
        if (auRestoRecipe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoRecipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoRecipe{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", comment='" + getComment() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
