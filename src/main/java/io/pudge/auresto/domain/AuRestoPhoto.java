package io.pudge.auresto.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuRestoPhoto.
 */
@Entity
@Table(name = "au_resto_photo")
@Document(indexName = "aurestophoto")
public class AuRestoPhoto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @ManyToOne
    private AuRestoRestaurant auRestoRestaurant;

    @ManyToOne
    private AuRestoMenu auRestoMenu;

    @ManyToOne
    private AuRestoFormula auRestoFormula;

    @ManyToOne
    private AuRestoRecipe auRestoRecipe;

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

    public AuRestoPhoto code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public AuRestoPhoto name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public AuRestoPhoto image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public AuRestoPhoto imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public AuRestoRestaurant getAuRestoRestaurant() {
        return auRestoRestaurant;
    }

    public AuRestoPhoto auRestoRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.auRestoRestaurant = auRestoRestaurant;
        return this;
    }

    public void setAuRestoRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.auRestoRestaurant = auRestoRestaurant;
    }

    public AuRestoMenu getAuRestoMenu() {
        return auRestoMenu;
    }

    public AuRestoPhoto auRestoMenu(AuRestoMenu auRestoMenu) {
        this.auRestoMenu = auRestoMenu;
        return this;
    }

    public void setAuRestoMenu(AuRestoMenu auRestoMenu) {
        this.auRestoMenu = auRestoMenu;
    }

    public AuRestoFormula getAuRestoFormula() {
        return auRestoFormula;
    }

    public AuRestoPhoto auRestoFormula(AuRestoFormula auRestoFormula) {
        this.auRestoFormula = auRestoFormula;
        return this;
    }

    public void setAuRestoFormula(AuRestoFormula auRestoFormula) {
        this.auRestoFormula = auRestoFormula;
    }

    public AuRestoRecipe getAuRestoRecipe() {
        return auRestoRecipe;
    }

    public AuRestoPhoto auRestoRecipe(AuRestoRecipe auRestoRecipe) {
        this.auRestoRecipe = auRestoRecipe;
        return this;
    }

    public void setAuRestoRecipe(AuRestoRecipe auRestoRecipe) {
        this.auRestoRecipe = auRestoRecipe;
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
        AuRestoPhoto auRestoPhoto = (AuRestoPhoto) o;
        if (auRestoPhoto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoPhoto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoPhoto{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + imageContentType + "'" +
            "}";
    }
}
