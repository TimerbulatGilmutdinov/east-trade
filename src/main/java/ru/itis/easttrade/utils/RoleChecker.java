package ru.itis.easttrade.utils;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.itis.easttrade.models.Role;

import java.util.List;

@Component
public class RoleChecker {
    public boolean isAdmin(Authentication authentication){
        return authentication.getAuthorities().contains(new Role(1,Role.ADMIN));
    }
    public boolean isModerator(Authentication authentication){
        return authentication.getAuthorities().contains(new Role(2,Role.MODERATOR));
    }

    public boolean isUser(Authentication authentication){
        return authentication.getAuthorities().contains(new Role(3,Role.USER));
    }

    public boolean hasEnoughAuthority(Authentication authentication){
        return isAdmin(authentication)||isModerator(authentication);
    }
}
