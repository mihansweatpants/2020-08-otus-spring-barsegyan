package ru.otus.spring.barsegyan.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.barsegyan.domain.ApplicationUser;
import ru.otus.spring.barsegyan.exception.NotFoundException;
import ru.otus.spring.barsegyan.service.ApplicationUserService;
import ru.otus.spring.barsegyan.type.ApplicationUserDetails;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationUserDetailsService.class);

    private final ApplicationUserService applicationUserService;

    public ApplicationUserDetailsService(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            ApplicationUser user = applicationUserService.getByUsername(username);

            logger.info("loadUserByUsername() : {}", username);

            return new ApplicationUserDetails(user);
        } catch (NotFoundException e) {
          throw new UsernameNotFoundException(e.getMessage());
        }
    }
}
