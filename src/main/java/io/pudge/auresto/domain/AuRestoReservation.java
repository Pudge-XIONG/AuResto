package io.pudge.auresto.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A AuRestoReservation.
 */
@Entity
@Table(name = "au_resto_reservation")
@Document(indexName = "aurestoreservation")
public class AuRestoReservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "reserve_date")
    private ZonedDateTime reserveDate;

    @Column(name = "reserve_for_date")
    private ZonedDateTime reserveForDate;

    @ManyToOne
    private AuRestoUser commander;

    @ManyToOne
    private AuRestoOrderStatus status;

    @ManyToOne
    private AuRestoRestaurant restaurant;

    @ManyToOne
    private AuRestoTable table;

    @ManyToOne
    private AuRestoUser auRestoUser;

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

    public AuRestoReservation code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public AuRestoReservation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getReserveDate() {
        return reserveDate;
    }

    public AuRestoReservation reserveDate(ZonedDateTime reserveDate) {
        this.reserveDate = reserveDate;
        return this;
    }

    public void setReserveDate(ZonedDateTime reserveDate) {
        this.reserveDate = reserveDate;
    }

    public ZonedDateTime getReserveForDate() {
        return reserveForDate;
    }

    public AuRestoReservation reserveForDate(ZonedDateTime reserveForDate) {
        this.reserveForDate = reserveForDate;
        return this;
    }

    public void setReserveForDate(ZonedDateTime reserveForDate) {
        this.reserveForDate = reserveForDate;
    }

    public AuRestoUser getCommander() {
        return commander;
    }

    public AuRestoReservation commander(AuRestoUser auRestoUser) {
        this.commander = auRestoUser;
        return this;
    }

    public void setCommander(AuRestoUser auRestoUser) {
        this.commander = auRestoUser;
    }

    public AuRestoOrderStatus getStatus() {
        return status;
    }

    public AuRestoReservation status(AuRestoOrderStatus auRestoOrderStatus) {
        this.status = auRestoOrderStatus;
        return this;
    }

    public void setStatus(AuRestoOrderStatus auRestoOrderStatus) {
        this.status = auRestoOrderStatus;
    }

    public AuRestoRestaurant getRestaurant() {
        return restaurant;
    }

    public AuRestoReservation restaurant(AuRestoRestaurant auRestoRestaurant) {
        this.restaurant = auRestoRestaurant;
        return this;
    }

    public void setRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.restaurant = auRestoRestaurant;
    }

    public AuRestoTable getTable() {
        return table;
    }

    public AuRestoReservation table(AuRestoTable auRestoTable) {
        this.table = auRestoTable;
        return this;
    }

    public void setTable(AuRestoTable auRestoTable) {
        this.table = auRestoTable;
    }

    public AuRestoUser getAuRestoUser() {
        return auRestoUser;
    }

    public AuRestoReservation auRestoUser(AuRestoUser auRestoUser) {
        this.auRestoUser = auRestoUser;
        return this;
    }

    public void setAuRestoUser(AuRestoUser auRestoUser) {
        this.auRestoUser = auRestoUser;
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
        AuRestoReservation auRestoReservation = (AuRestoReservation) o;
        if (auRestoReservation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoReservation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoReservation{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", reserveDate='" + getReserveDate() + "'" +
            ", reserveForDate='" + getReserveForDate() + "'" +
            "}";
    }
}
