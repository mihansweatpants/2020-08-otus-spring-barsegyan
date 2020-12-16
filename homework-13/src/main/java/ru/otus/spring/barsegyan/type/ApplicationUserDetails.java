package ru.otus.spring.barsegyan.type;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.domain.ApplicationUserRole;

import java.util.Collection;
import java.util.List;

public class ApplicationUserDetails implements UserDetails {
    private final Long userId;
    private final String username;
    private final String password;
    private final ApplicationUserRole role;
    private final Boolean isBlocked;

    public ApplicationUserDetails(ApplicationUser applicationUser) {
        this.userId = applicationUser.getId();
        this.username = applicationUser.getUsername();
        this.password = applicationUser.getPassword();
        this.role = applicationUser.getRole();
        this.isBlocked = applicationUser.getIsBlocked();
    }

    public Long getUserId() {
        return userId;
    }

    public ApplicationUserRole getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (isBlocked) {
            throw new LockedException("Account is blocked");
        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
