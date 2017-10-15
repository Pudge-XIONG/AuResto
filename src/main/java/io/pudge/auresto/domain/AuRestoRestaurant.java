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
 * A AuRestoRestaurant.
 */
@Entity
@Table(name = "au_resto_restaurant")
@Document(indexName = "aurestorestaurant")
public class AuRestoRestaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "open_time")
    private ZonedDateTime openTime;

    @Column(name = "close_time")
    private ZonedDateTime closeTime;

    @OneToOne
    @JoinColumn(unique = true)
    private AuRestoLocation location;

    @OneToMany(mappedBy = "auRestoRestaurant")
    @JsonIgnore
    private Set<AuRestoRestaurantType> types = new HashSet<>();

    @OneToMany(mappedBy = "auRestoRestaurant")
    @JsonIgnore
    private Set<AuRestoPhoto> photos = new HashSet<>();

    @OneToMany(mappedBy = "auRestoRestaurant")
    @JsonIgnore
    private Set<AuRestoMenu> menus = new HashSet<>();

    @OneToMany(mappedBy = "auRestoRestaurant")
    @JsonIgnore
    private Set<AuRestoTable> tables = new HashSet<>();

    @ManyToOne
    private AuRestoUser owner;

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

    public AuRestoRestaurant code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public AuRestoRestaurant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AuRestoRestaurant description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getOpenTime() {
        return openTime;
    }

    public AuRestoRestaurant openTime(ZonedDateTime openTime) {
        this.openTime = openTime;
        return this;
    }

    public void setOpenTime(ZonedDateTime openTime) {
        this.openTime = openTime;
    }

    public ZonedDateTime getCloseTime() {
        return closeTime;
    }

    public AuRestoRestaurant closeTime(ZonedDateTime closeTime) {
        this.closeTime = closeTime;
        return this;
    }

    public void setCloseTime(ZonedDateTime closeTime) {
        this.closeTime = closeTime;
    }

    public AuRestoLocation getLocation() {
        return location;
    }

    public AuRestoRestaurant location(AuRestoLocation auRestoLocation) {
        this.location = auRestoLocation;
        return this;
    }

    public void setLocation(AuRestoLocation auRestoLocation) {
        this.location = auRestoLocation;
    }

    public Set<AuRestoRestaurantType> getTypes() {
        return types;
    }

    public AuRestoRestaurant types(Set<AuRestoRestaurantType> auRestoRestaurantTypes) {
        this.types = auRestoRestaurantTypes;
        return this;
    }

    public AuRestoRestaurant addType(AuRestoRestaurantType auRestoRestaurantType) {
        this.types.add(auRestoRestaurantType);
        auRestoRestaurantType.setAuRestoRestaurant(this);
        return this;
    }

    public AuRestoRestaurant removeType(AuRestoRestaurantType auRestoRestaurantType) {
        this.types.remove(auRestoRestaurantType);
        auRestoRestaurantType.setAuRestoRestaurant(null);
        return this;
    }

    public void setTypes(Set<AuRestoRestaurantType> auRestoRestaurantTypes) {
        this.types = auRestoRestaurantTypes;
    }

    public Set<AuRestoPhoto> getPhotos() {
        return photos;
    }

    public AuRestoRestaurant photos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
        return this;
    }

    public AuRestoRestaurant addPhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.add(auRestoPhoto);
        auRestoPhoto.setAuRestoRestaurant(this);
        return this;
    }

    public AuRestoRestaurant removePhoto(AuRestoPhoto auRestoPhoto) {
        this.photos.remove(auRestoPhoto);
        auRestoPhoto.setAuRestoRestaurant(null);
        return this;
    }

    public void setPhotos(Set<AuRestoPhoto> auRestoPhotos) {
        this.photos = auRestoPhotos;
    }

    public Set<AuRestoMenu> getMenus() {
        return menus;
    }

    public AuRestoRestaurant menus(Set<AuRestoMenu> auRestoMenus) {
        this.menus = auRestoMenus;
        return this;
    }

    public AuRestoRestaurant addMenu(AuRestoMenu auRestoMenu) {
        this.menus.add(auRestoMenu);
        auRestoMenu.setAuRestoRestaurant(this);
        return this;
    }

    public AuRestoRestaurant removeMenu(AuRestoMenu auRestoMenu) {
        this.menus.remove(auRestoMenu);
        auRestoMenu.setAuRestoRestaurant(null);
        return this;
    }

    public void setMenus(Set<AuRestoMenu> auRestoMenus) {
        this.menus = auRestoMenus;
    }

    public Set<AuRestoTable> getTables() {
        return tables;
    }

    public AuRestoRestaurant tables(Set<AuRestoTable> auRestoTables) {
        this.tables = auRestoTables;
        return this;
    }

    public AuRestoRestaurant addTable(AuRestoTable auRestoTable) {
        this.tables.add(auRestoTable);
        auRestoTable.setAuRestoRestaurant(this);
        return this;
    }

    public AuRestoRestaurant removeTable(AuRestoTable auRestoTable) {
        this.tables.remove(auRestoTable);
        auRestoTable.setAuRestoRestaurant(null);
        return this;
    }

    public void setTables(Set<AuRestoTable> auRestoTables) {
        this.tables = auRestoTables;
    }

    public AuRestoUser getOwner() {
        return owner;
    }

    public AuRestoRestaurant owner(AuRestoUser auRestoUser) {
        this.owner = auRestoUser;
        return this;
    }

    public void setOwner(AuRestoUser auRestoUser) {
        this.owner = auRestoUser;
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
        AuRestoRestaurant auRestoRestaurant = (AuRestoRestaurant) o;
        if (auRestoRestaurant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoRestaurant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoRestaurant{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", openTime='" + getOpenTime() + "'" +
            ", closeTime='" + getCloseTime() + "'" +
            "}";
    }
}
