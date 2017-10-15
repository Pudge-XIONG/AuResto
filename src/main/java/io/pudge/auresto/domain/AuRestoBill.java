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
 * A AuRestoBill.
 */
@Entity
@Table(name = "au_resto_bill")
@Document(indexName = "aurestobill")
public class AuRestoBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "auRestoBill")
    @JsonIgnore
    private Set<AuRestoOrder> orders = new HashSet<>();

    @ManyToOne
    private AuRestoBillStatus status;

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

    public AuRestoBill code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public AuRestoBill date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<AuRestoOrder> getOrders() {
        return orders;
    }

    public AuRestoBill orders(Set<AuRestoOrder> auRestoOrders) {
        this.orders = auRestoOrders;
        return this;
    }

    public AuRestoBill addOrder(AuRestoOrder auRestoOrder) {
        this.orders.add(auRestoOrder);
        auRestoOrder.setAuRestoBill(this);
        return this;
    }

    public AuRestoBill removeOrder(AuRestoOrder auRestoOrder) {
        this.orders.remove(auRestoOrder);
        auRestoOrder.setAuRestoBill(null);
        return this;
    }

    public void setOrders(Set<AuRestoOrder> auRestoOrders) {
        this.orders = auRestoOrders;
    }

    public AuRestoBillStatus getStatus() {
        return status;
    }

    public AuRestoBill status(AuRestoBillStatus auRestoBillStatus) {
        this.status = auRestoBillStatus;
        return this;
    }

    public void setStatus(AuRestoBillStatus auRestoBillStatus) {
        this.status = auRestoBillStatus;
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
        AuRestoBill auRestoBill = (AuRestoBill) o;
        if (auRestoBill.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoBill.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoBill{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
