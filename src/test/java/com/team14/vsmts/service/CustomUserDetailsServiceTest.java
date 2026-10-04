package com.team14.vsmts.service;

import com.team14.vsmts.model.User;
import com.team14.vsmts.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository repo;
    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    public void testLoadUserByUsernameSuccess() {
        User u = new User();
        u.setEmail("t@t");
        u.setPassword("pass");
        u.setRole("ADMIN");
        when(repo.findByEmail(anyString())).thenReturn(Optional.of(u));
        UserDetails ud = service.loadUserByUsername("t@t");
        assertEquals("t@t", ud.getUsername());
    }

    @Test
    public void testLoadUserByUsernameFail() {
        when(repo.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("t@t"));
    }
}
