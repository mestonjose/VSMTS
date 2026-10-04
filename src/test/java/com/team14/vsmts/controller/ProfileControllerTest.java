package com.team14.vsmts.controller;

import com.team14.vsmts.model.User;
import com.team14.vsmts.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private Model model;
    @Mock
    private UserDetails userDetails;
    @InjectMocks
    private ProfileController controller;

    @Test
    public void testProfile() {
        when(userDetails.getUsername()).thenReturn("test@test.com");
        when(userService.findByEmail("test@test.com")).thenReturn(new User());
        assertEquals("profile", controller.viewProfile(userDetails, model));
    }
}
