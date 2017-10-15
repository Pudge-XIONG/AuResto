package io.pudge.auresto.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AuRestoUser.
 */
@Entity
@Table(name = "au_resto_user")
@Document(indexName = "aurestouser")
public class AuRestoUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "login")
    private String login;

    @Column(name = "jhi_password")
    private String password;

    @OneToOne
    @JoinColumn(unique = true)
    private AuRestoPhoto photo;

    @OneToMany(mappedBy = "auRestoUser")
    @JsonIgnore
    private Set<AuRestoOrder> orders = new HashSet<>();

    @OneToMany(mappedBy = "auRestoUser")
    @JsonIgnore
    private Set<AuRestoReservation> reservations = new HashSet<>();

    @ManyToOne
    private AuRestoGender gender;

    @ManyToOne
    private AuRestoProfile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public AuRestoUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public AuRestoUser middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public AuRestoUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public AuRestoUser login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public AuRestoUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuRestoPhoto getPhoto() {
        return photo;
    }

    public AuRestoUser photo(AuRestoPhoto auRestoPhoto) {
        this.photo = auRestoPhoto;
        return this;
    }

    public void setPhoto(AuRestoPhoto auRestoPhoto) {
        this.photo = auRestoPhoto;
    }

    public Set<AuRestoOrder> getOrders() {
        return orders;
    }

    public AuRestoUser orders(Set<AuRestoOrder> auRestoOrders) {
        this.orders = auRestoOrders;
        return this;
    }

    public AuRestoUser addOrder(AuRestoOrder auRestoOrder) {
        this.orders.add(auRestoOrder);
        auRestoOrder.setAuRestoUser(this);
        return this;
    }

    public AuRestoUser removeOrder(AuRestoOrder auRestoOrder) {
        this.orders.remove(auRestoOrder);
        auRestoOrder.setAuRestoUser(null);
        return this;
    }

    public void setOrders(Set<AuRestoOrder> auRestoOrders) {
        this.orders = auRestoOrders;
    }

    public Set<AuRestoReservation> getReservations() {
        return reservations;
    }

    public AuRestoUser reservations(Set<AuRestoReservation> auRestoReservations) {
        this.reservations = auRestoReservations;
        return this;
    }

    public AuRestoUser addReservation(AuRestoReservation auRestoReservation) {
        this.reservations.add(auRestoReservation);
        auRestoReservation.setAuRestoUser(this);
        return this;
    }

    public AuRestoUser removeReservation(AuRestoReservation auRestoReservation) {
        this.reservations.remove(auRestoReservation);
        auRestoReservation.setAuRestoUser(null);
        return this;
    }

    public void setReservations(Set<AuRestoReservation> auRestoReservations) {
        this.reservations = auRestoReservations;
    }

    public AuRestoGender getGender() {
        return gender;
    }

    public AuRestoUser gender(AuRestoGender auRestoGender) {
        this.gender = auRestoGender;
        return this;
    }

    public void setGender(AuRestoGender auRestoGender) {
        this.gender = auRestoGender;
    }

    public AuRestoProfile getProfile() {
        return profile;
    }

    public AuRestoUser profile(AuRestoProfile auRestoProfile) {
        this.profile = auRestoProfile;
        return this;
    }

    public void setProfile(AuRestoProfile auRestoProfile) {
        this.profile = auRestoProfile;
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
        AuRestoUser auRestoUser = (AuRestoUser) o;
        if (auRestoUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), auRestoUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AuRestoUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", login='" + getLogin() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
