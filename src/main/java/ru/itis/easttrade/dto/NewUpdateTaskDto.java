package ru.itis.easttrade.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewUpdateTaskDto {
    private Integer id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Topic topic;
    @NotNull(message = "Name should not be null!")
    @Size(min = 5, message = "Size of name should be more than 5 characters")
    private String name;
    @NotNull(message = "Description should not be null!")
    @Size(min = 10, message = "Size of description should be more than 10 characters")
    private String description;
    @Pattern(regexp = "^[0-9]+$", message = "Price should be a number!")
    @Size(min = 1, message = "Size of price should be more than 0")
    private String price;
    private Task.TaskState state;
}
