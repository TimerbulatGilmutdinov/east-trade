package ru.itis.easttrade.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
@Entity
public class Task {
    public enum TaskState {
        PENDING, DISCUSSING, COMPLETED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Column(nullable = false)
    private Date publishDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Topic topic;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskState state;
}
