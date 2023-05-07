package ru.itis.easttrade.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Role;
import ru.itis.easttrade.models.State;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Account")
public class AccountDto  {
    @Schema(description = "Id", example = "1642")
    private Integer id;
    @Schema(description = "Email", example = "example@mail.ru")
    private String email;
    @Schema(description = "Name", example = "Alex")
    private String name;
    @Schema(description = "Surname", example = "Jones")
    private String surname;
    @Schema(description = "Phone number", example = "89271112233")
    private String phoneNumber;
    @Schema(description = "Account role", example = "ADMIN")
    private Role role;
    @Schema(description = "Account state", example = "BANNED")
    private State state;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .email(account.getEmail())
                .name(account.getName())
                .surname(account.getSurname())
                .phoneNumber(account.getPhoneNumber())
                .role(account.getRole())
                .state(account.getState())
                .build();
    }

    public static List<AccountDto> from(List<Account> accounts) {
        return accounts.stream()
                .map(AccountDto::from)
                .collect(Collectors.toList());
    }

}
