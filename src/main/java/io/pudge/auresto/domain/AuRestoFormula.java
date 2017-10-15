package io.pudge.auresto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AuRestoFormula.
 */
@Entity
@Table(name = "au_resto_formula")
@Document(indexName = "aurestoformula")
public class AuRestoFormula implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private AuRestoMenu auRestoMenu;

    @OneToMany(mappedBy = "auRestoFormula")
    @JsonIgnore
    private Set<AuRestoPhoto> photos = new HashSet<>();

    @OneToMany(mappedBy = "auRestoFormula")
    @JsonIgnore
    private Set<AuRestoRecipe> recipes = new HashSet<>();

    @ManyToOne
    private AuRestoFormulaType type;

    @ManyToOne
    private AuRestoRecipe entree;

    @ManyToOne
    private AuRestoRecipe dish;

    @ManyToOne
    private AuRestoRecipe dessert;

    @ManyToOne
    private AuRestoOrder auRestoOrder;

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

    public AuRestoFormula title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public AuRestoFormula price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public AuRestoMenu getAuRestoMenu() {
        return auRestoMenu;
    }

    public AuRestoFormula auRestoMenu(AuRestoMenu auRestoMenu) {
        this.auRestoMenu = auRestoMenu;
        return this;
    }

    public void setAuRestoMenu(AuRestoMenu auRestoMenu) {
        this.auRestoMenu = auRestoMenu;
    }

    public Set<AuRestoPhoto> getPhotos() {
        return photos;
    }

    public AuRestoFormula photos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
        return this;
    }

    public AuRestoFormula addPhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.add(auRestoPhoto);
        auRestoPhoto.setAuRestoFormula(this);
        return this;
    }

    public AuRestoFormula removePhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.remove(auRestoPhoto);
        auRestoPhoto.setAuRestoFormula(null);
        return this;
    }

    public void setPhotos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
    }

    public Set<AuRestoRecipe> getRecipes() {
        return recipes;
    }

    public AuRestoFormula recipes(Set<AuRestoRecipe> auRestoRecipes) {
        this.recipes = auRestoRecipes;
        return this;
    }

    public AuRestoFormula addRecipe(AuRestoRecipe auRestoRecipe) {
        this.recipes.add(auRestoRecipe);
        auRestoRecipe.setAuRestoFormula(this);
        return this;
    }

    public AuRestoFormula removeRecipe(AuRestoRecipe auRestoRecipe) {
        this.recipes.remove(auRestoRecipe);
        auRestoRecipe.setAuRestoFormula(null);
        return this;
    }

    public void setRecipes(Set<AuRestoRecipe> auRestoRecipes) {
        this.recipes = auRestoRecipes;
    }

    public AuRestoFormulaType getType() {
        return type;
    }

    public AuRestoFormula type(AuRestoFormulaType auRestoFormulaType) {
        this.type = auRestoFormulaType;
        return this;
    }

    public void setType(AuRestoFormulaType auRestoFormulaType) {
        this.type = auRestoFormulaType;
    }

    public AuRestoRecipe getEntree() {
        return entree;
    }

    public AuRestoFormula entree(AuRestoRecipe auRestoRecipe) {
        this.entree = auRestoRecipe;
        return this;
    }

    public void setEntree(AuRestoRecipe auRestoRecipe) {
        this.entree = auRestoRecipe;
    }

    public AuRestoRecipe getDish() {
        return dish;
    }

    public AuRestoFormula dish(AuRestoRecipe auRestoRecipe) {
        this.dish = auRestoRecipe;
        return this;
    }

    public void setDish(AuRestoRecipe auRestoRecipe) {
        this.dish = auRestoRecipe;
    }

    public AuRestoRecipe getDessert() {
        return dessert;
    }

    public AuRestoFormula dessert(AuRestoRecipe auRestoRecipe) {
        this.dessert = auRestoRecipe;
        return this;
    }

    public void setDessert(AuRestoRecipe auRestoRecipe) {
        this.dessert = auRestoRecipe;
    }

    public AuRestoOrder getAuRestoOrder() {
        return auRestoOrder;
    }

    public AuRestoFormula auRestoOrder(AuRestoOrder auRestoOrder) {
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
        AuRestoFormula auRestoFormula = (AuRestoFormula) o;
        if (auRestoFormula.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoFormula.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoFormula{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
