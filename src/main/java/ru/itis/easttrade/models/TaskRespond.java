package ru.itis.easttrade.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "task_responds")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskRespond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private Account account;
    @ManyToMany(mappedBy = "taskResponds")
    private Set<Task> tasks;
}
