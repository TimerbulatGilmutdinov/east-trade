package ru.itis.easttrade.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Transient
    public static final String ADMIN = "ROLE_ADMIN";
    @Transient
    public static final String USER = "ROLE_USER";
    @Transient
    public static final String MODERATOR = "ROLE_MODERATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
