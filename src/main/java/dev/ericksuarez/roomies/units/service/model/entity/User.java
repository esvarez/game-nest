package dev.ericksuarez.roomies.units.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.ericksuarez.roomies.units.service.model.entity.relation.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class User extends AuditModel {
    @Id
    @Type(type="uuid-char")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "unit_id", nullable = false)
    //@OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Unit unit;

    @ManyToMany
    @JoinTable(
            name = "users_accounts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    Set<Account> accounts;

    private String name;

    private String username;

    private boolean active;
}
