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
 * A AuRestoOrder.
 */
@Entity
@Table(name = "au_resto_order")
@Document(indexName = "aurestoorder")
public class AuRestoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "auRestoOrder")
    @JsonIgnore
    private Set<AuRestoFormula> formulas = new HashSet<>();

    @OneToMany(mappedBy = "auRestoOrder")
    @JsonIgnore
    private Set<AuRestoRecipe> recipes = new HashSet<>();

    @ManyToOne
    private AuRestoRestaurant restaurant;

    @ManyToOne
    private AuRestoTable table;

    @ManyToOne
    private AuRestoUser commander;

    @ManyToOne
    private AuRestoOrderType type;

    @ManyToOne
    private AuRestoOrderStatus status;

    @ManyToOne
    private AuRestoUser auRestoUser;

    @ManyToOne
    private AuRestoBill auRestoBill;

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

    public AuRestoOrder code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public AuRestoOrder date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<AuRestoFormula> getFormulas() {
        return formulas;
    }

    public AuRestoOrder formulas(Set<AuRestoFormula> auRestoFormulas) {
        this.formulas = auRestoFormulas;
        return this;
    }

    public AuRestoOrder addFormula(AuRestoFormula auRestoFormula) {
        this.formulas.add(auRestoFormula);
        auRestoFormula.setAuRestoOrder(this);
        return this;
    }

    public AuRestoOrder removeFormula(AuRestoFormula auRestoFormula) {
        this.formulas.remove(auRestoFormula);
        auRestoFormula.setAuRestoOrder(null);
        return this;
    }

    public void setFormulas(Set<AuRestoFormula> auRestoFormulas) {
        this.formulas = auRestoFormulas;
    }

    public Set<AuRestoRecipe> getRecipes() {
        return recipes;
    }

    public AuRestoOrder recipes(Set<AuRestoRecipe> auRestoRecipes) {
        this.recipes = auRestoRecipes;
        return this;
    }

    public AuRestoOrder addRecipe(AuRestoRecipe auRestoRecipe) {
        this.recipes.add(auRestoRecipe);
        auRestoRecipe.setAuRestoOrder(this);
        return this;
    }

    public AuRestoOrder removeRecipe(AuRestoRecipe auRestoRecipe) {
        this.recipes.remove(auRestoRecipe);
        auRestoRecipe.setAuRestoOrder(null);
        return this;
    }

    public void setRecipes(Set<AuRestoRecipe> auRestoRecipes) {
        this.recipes = auRestoRecipes;
    }

    public AuRestoRestaurant getRestaurant() {
        return restaurant;
    }

    public AuRestoOrder restaurant(AuRestoRestaurant auRestoRestaurant) {
        this.restaurant = auRestoRestaurant;
        return this;
    }

    public void setRestaurant(AuRestoRestaurant auRestoRestaurant) {
        this.restaurant = auRestoRestaurant;
    }

    public AuRestoTable getTable() {
        return table;
    }

    public AuRestoOrder table(AuRestoTable auRestoTable) {
        this.table = auRestoTable;
        return this;
    }

    public void setTable(AuRestoTable auRestoTable) {
        this.table = auRestoTable;
    }

    public AuRestoUser getCommander() {
        return commander;
    }

    public AuRestoOrder commander(AuRestoUser auRestoUser) {
        this.commander = auRestoUser;
        return this;
    }

    public void setCommander(AuRestoUser auRestoUser) {
        this.commander = auRestoUser;
    }

    public AuRestoOrderType getType() {
        return type;
    }

    public AuRestoOrder type(AuRestoOrderType auRestoOrderType) {
        this.type = auRestoOrderType;
        return this;
    }

    public void setType(AuRestoOrderType auRestoOrderType) {
        this.type = auRestoOrderType;
    }

    public AuRestoOrderStatus getStatus() {
        return status;
    }

    public AuRestoOrder status(AuRestoOrderStatus auRestoOrderStatus) {
        this.status = auRestoOrderStatus;
        return this;
    }

    public void setStatus(AuRestoOrderStatus auRestoOrderStatus) {
        this.status = auRestoOrderStatus;
    }

    public AuRestoUser getAuRestoUser() {
        return auRestoUser;
    }

    public AuRestoOrder auRestoUser(AuRestoUser auRestoUser) {
        this.auRestoUser = auRestoUser;
        return this;
    }

    public void setAuRestoUser(AuRestoUser auRestoUser) {
        this.auRestoUser = auRestoUser;
    }

    public AuRestoBill getAuRestoBill() {
        return auRestoBill;
    }

    public AuRestoOrder auRestoBill(AuRestoBill auRestoBill) {
        this.auRestoBill = auRestoBill;
        return this;
    }

    public void setAuRestoBill(AuRestoBill auRestoBill) {
        this.auRestoBill = auRestoBill;
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
        AuRestoOrder auRestoOrder = (AuRestoOrder) o;
        if (auRestoOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoOrder{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
