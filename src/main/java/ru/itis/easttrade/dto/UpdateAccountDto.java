package ru.itis.easttrade.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Новый аккаунт/изменение аккаунта")
public class UpdateAccountDto {
    @Schema(description = "Email", example = "example@mail.ru")
    @NotNull(message = "{dto.null}")
    @Email(regexp = "^[\\w\\-\\.]+@[\\w-]+\\.[\\w-]{2,4}$", message = "{account.email.pattern}")
    private String email;

    @Schema(description = "Name", example = "Alex")
    @NotNull(message = "{dto.null}")
    @Size(min = 5, max = 32, message = "{account.name.size}")
    private String name;

    @Schema(description = "Surname", example = "Jones")
    @NotNull(message = "{dto.null}")
    @Size(min = 5, max = 32, message = "{account.surname.size}")
    private String surname;

    @Schema(description = "Password", example = "qwerty007")
    @NotNull(message = "{dto.null}")
    @Size(min = 8, max = 40, message = "{account.password.size}")
    private String password;

    @Schema(description = "Phone number", example = "89271112233")
    @NotNull(message = "{dto.null}")
    @Size(min = 11, max = 11, message = "{account.phoneNumber.size}")
    @Pattern(regexp = "^[0-9]{11}$")
    private String phoneNumber;

}