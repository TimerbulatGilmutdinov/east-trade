package ru.itis.easttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.easttrade.models.Topic;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewOrUpdateTaskDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Topic topic;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
}
