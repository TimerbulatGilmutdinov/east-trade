package ru.itis.easttrade.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String passwordHash;
    @OneToMany(mappedBy = "account")
    private List<Task> tasks;
    @OneToMany(mappedBy = "account")
    private List<Article> articles;
    @OneToMany(mappedBy = "account")
    private List<Product> products;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @Enumerated(EnumType.STRING)
    private State state;
    @Override
    public String toString(){
        return name +" " +surname;
    }
}
