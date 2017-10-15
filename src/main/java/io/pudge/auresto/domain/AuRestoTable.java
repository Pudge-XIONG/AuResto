package io.pudge.auresto.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AuRestoTable.
 */
@Entity
@Table(name = "au_resto_table")
@Document(indexName = "aurestotable")
public class AuRestoTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "max_place_num")
    private Integer maxPlaceNum;

    @Column(name = "taken_place_num")
    private Integer takenPlaceNum;

    @Column(name = "jhi_window")
    private Boolean window;

    @Column(name = "outside")
    private Boolean outside;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "available")
    private Boolean available;

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

    public AuRestoTable code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMaxPlaceNum() {
        return maxPlaceNum;
    }

    public AuRestoTable maxPlaceNum(Integer maxPlaceNum) {
        this.maxPlaceNum = maxPlaceNum;
        return this;
    }

    public void setMaxPlaceNum(Integer maxPlaceNum) {
        this.maxPlaceNum = maxPlaceNum;
    }

    public Integer getTakenPlaceNum() {
        return takenPlaceNum;
    }

    public AuRestoTable takenPlaceNum(Integer takenPlaceNum) {
        this.takenPlaceNum = takenPlaceNum;
        return this;
    }

    public void setTakenPlaceNum(Integer takenPlaceNum) {
        this.takenPlaceNum = takenPlaceNum;
    }

    public Boolean isWindow() {
        return window;
    }

    public AuRestoTable window(Boolean window) {
        this.window = window;
        return this;
    }

    public void setWindow(Boolean window) {
        this.window = window;
    }

    public Boolean isOutside() {
        return outside;
    }

    public AuRestoTable outside(Boolean outside) {
        this.outside = outside;
        return this;
    }

    public void setOutside(Boolean outside) {
        this.outside = outside;
    }

    public Integer getFloor() {
        return floor;
    }

    public AuRestoTable floor(Integer floor) {
        this.floor = floor;
        return this;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Boolean isAvailable() {
        return available;
    }

    public AuRestoTable available(Boolean available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public AuRestoRestaurant getAuRestoRestaurant() {
        return auRestoRestaurant;
    }

    public AuRestoTable auRestoRestaurant(AuRestoRestaurant auRestoRestaurant) {
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
        AuRestoTable auRestoTable = (AuRestoTable) o;
        if (auRestoTable.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoTable.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoTable{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", maxPlaceNum='" + getMaxPlaceNum() + "'" +
            ", takenPlaceNum='" + getTakenPlaceNum() + "'" +
            ", window='" + isWindow() + "'" +
            ", outside='" + isOutside() + "'" +
            ", floor='" + getFloor() + "'" +
            ", available='" + isAvailable() + "'" +
            "}";
    }
}
