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
public class NewOrUpdateAccountDto {
    @Schema(description = "Email", example = "example@mail.ru")
    @NotNull(message = "Cannot be empty")
    @Email(regexp = "^[\\w\\-\\.]+@[\\w-]+\\.[\\w-]{2,4}$", message = "Invalid email password")
    private String email;

    @Schema(description = "Name", example = "Alex")
    @NotNull(message = "Cannot be empty")
    @Size(min = 2, max = 32, message = "Name should have size of 2-32")
    private String name;

    @Schema(description = "Surname", example = "Jones")
    @NotNull(message = "Cannot be empty")
    @Size(min = 2, max = 32, message = "Surname should have size of 2-32")
    private String surname;

    @Schema(description = "Password", example = "qwerty007")
    @NotNull(message = "Cannot be empty")
    @Size(min = 8, max = 40, message = "Password should have size of 8-40")
    private String password;

    @Schema(description = "Phone number", example = "89271112233")
    @NotNull(message = "Cannot be empty")
    @Size(min = 11, max = 11, message = "Should have size of 11")
    @Pattern(regexp = "^[0-9]{11}$", message = "Invalid phone number pattern")
    private String phoneNumber;

}