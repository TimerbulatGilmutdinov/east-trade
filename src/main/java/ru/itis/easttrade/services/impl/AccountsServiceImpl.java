package ru.itis.easttrade.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.NewOrUpdateAccountDto;
import ru.itis.easttrade.exceptions.AlreadyExistsException;
import ru.itis.easttrade.exceptions.NotFoundException;
import ru.itis.easttrade.models.Account;
import ru.itis.easttrade.models.Role;
import ru.itis.easttrade.models.State;
import ru.itis.easttrade.repositories.AccountsRepository;
import ru.itis.easttrade.services.AccountsService;

import javax.validation.Valid;

import static ru.itis.easttrade.dto.AccountDto.from;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService {
    private final AccountsRepository accountsRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountDto addAccount(@Valid @ModelAttribute NewOrUpdateAccountDto accountDto) {
        if (accountsRepository.findByEmail(accountDto.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Account with email <" + accountDto.getEmail() + "> already exists", accountDto);
        }

        Account accountToSave = Account.builder()
                .email(accountDto.getEmail())
                .name(accountDto.getName())
                .surname(accountDto.getSurname())
                .phoneNumber(accountDto.getPhoneNumber())
                .passwordHash(passwordEncoder.encode(accountDto.getPassword()))
                .role(new Role(1, Role.ADMIN))
                .state(State.ACTIVE)
                .build();
        accountsRepository.save(accountToSave);
        return from(accountToSave);
    }

    @Override
    public AccountDto updateAccount(Integer id, NewOrUpdateAccountDto updatedAccount) {
        Account accountForUpdate = accountsRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id <" + id + "> not found"));

        accountForUpdate.setEmail(updatedAccount.getEmail());
        accountForUpdate.setName(updatedAccount.getName());
        accountForUpdate.setSurname(updatedAccount.getSurname());
        accountForUpdate.setPhoneNumber(updatedAccount.getPhoneNumber());
        accountForUpdate.setPasswordHash(passwordEncoder.encode(updatedAccount.getPassword()));

        accountsRepository.save(accountForUpdate);
        return from(accountForUpdate);
    }

    @Override
    public AccountDto deleteAccountById(Integer id) {
        Account accountToDelete = accountsRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id <" + id + "> not found"));
        accountToDelete.setState(State.DELETED);
        accountsRepository.save(accountToDelete);
        return from(accountToDelete);
    }

    @Override
    public AccountDto getAccountById(Integer id) {
        Account account = accountsRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id <" + id + "> not found"));
        return from(account);
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        Account account = accountsRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Account with email <" + email + "> not found"));
        return from(account);
    }

    @Override
    public AccountDto getAccountByPhoneNumber(String phoneNumber) {
        Account account = accountsRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new NotFoundException("Account with phone number <" + phoneNumber + "> not found"));
        return from(account);
    }

    @Override
    public AccountDto banAccountById(Integer id) {
        Account accountToBan = accountsRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id <" + id + "> not found"));
        accountToBan.setState(State.BANNED);
        accountsRepository.save(accountToBan);
        return from(accountToBan);
    }

    @Override
    public AccountDto banAccountByEmail(String email) {
        Account accountToBan = accountsRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Account with email <" + email + "> not found"));
        accountToBan.setState(State.BANNED);
        accountsRepository.save(accountToBan);
        return from(accountToBan);
    }

    public AccountDto grantModeratorAuthority(Integer id) {
        Account account = accountsRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id <" + id + "> not found"));
        account.setRole(new Role(3, Role.MODERATOR));
        accountsRepository.save(account);
        return from(account);
    }

    public AccountDto grantAdminAuthority(Integer id) {
        Account account = accountsRepository.findById(id).orElseThrow(() -> new NotFoundException("Account with id <" + id + "> not found"));
        account.setRole(new Role(1, Role.ADMIN));
        accountsRepository.save(account);
        return from(account);
    }
}
