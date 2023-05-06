package ru.itis.easttrade.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Task;
import ru.itis.easttrade.models.Topic;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task")
public class TaskDto {
    @Schema(description = "Id", example = "1642")
    private Integer id;
    @Schema(description = "Task owner", implementation = Account.class)
    private Account account;
    @Schema(description = "Publish date", example = "")
    private Date publishDate;
    @Schema(description = "Topic", example = "Housework", implementation = Topic.class)
    private Topic topic;
    @Schema(description = "Name", example = "Completing room design")
    private String name;
    @Schema(description = "Description", example = "Need to complete my room design")
    private String description;
    @Schema(description = "Price", example = "899")
    private Double price;

    public static TaskDto from(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .account(task.getAccount())
                .publishDate(task.getPublishDate())
                .topic(task.getTopic())
                .description(task.getDescription())
                .price(task.getPrice())
                .build();
    }

    public static List<TaskDto> from(List<Task> tasks) {
        return tasks.stream()
                .map(TaskDto::from)
                .collect(Collectors.toList());
    }
}
