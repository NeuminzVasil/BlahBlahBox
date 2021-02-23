package entities;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NamedQueries({
        @NamedQuery(name = "customerWithAuthority",
                query = "SELECT crs FROM Customer crs JOIN FETCH crs.authorities WHERE crs.id = :id"),
        @NamedQuery(name = "allCustomers",
                query = "SELECT crs FROM Customer crs")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "customer") // fetch = FetchType.EAGER
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Authority> authorities;

    public Customer() {
    }

    public Customer(String login, String firstName, String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer setActive(Boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName ;
    }
}
