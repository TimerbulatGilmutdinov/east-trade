package ru.itis.easttrade.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Информация об ошибке")
public class ExceptionDto {
    @Schema(description = "Message", example = "Not found")
    private String message;
    @Schema(description = "HTTP-status", example = "404")
    private int status;
}
