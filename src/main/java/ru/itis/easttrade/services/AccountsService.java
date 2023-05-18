package ru.itis.easttrade.services;


import ru.itis.easttrade.dto.AccountDto;
import ru.itis.easttrade.dto.UpdateAccountDto;

public interface AccountsService {
    AccountDto addAccount(UpdateAccountDto accountDto);
    AccountDto updateAccount(Integer id, UpdateAccountDto updatedAccount);
    AccountDto banAccountById(Integer id);
    AccountDto banAccountByEmail(String email);
    AccountDto deleteAccountById(Integer id);
    AccountDto getAccountById(Integer id);
    AccountDto getAccountByEmail(String emil);
    AccountDto getAccountByPhoneNumber(String emil);
}
