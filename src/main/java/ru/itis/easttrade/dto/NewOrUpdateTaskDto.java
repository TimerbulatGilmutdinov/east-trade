package ru.itis.easttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewOrUpdateTaskDto {
    private Integer id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Topic topic;
    private String name;
    private String description;
    private Double price;
    private Task.TaskState state;
}
