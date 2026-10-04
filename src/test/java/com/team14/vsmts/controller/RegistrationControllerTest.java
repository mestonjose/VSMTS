package com.team14.vsmts.controller;

import com.team14.vsmts.dto.UserRegistrationDto;
import com.team14.vsmts.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult result;

    @InjectMocks
    private RegistrationController controller;

    @Test
    public void testShowRegistrationForm() {
        assertEquals("register", controller.showRegistrationForm(model));
    }

    @Test
    public void testRegisterUserSuccess() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("t@t.com");
        dto.setPassword("pass");
        dto.setConfirmPassword("pass");
        when(userService.emailExists(anyString())).thenReturn(false);
        when(result.hasErrors()).thenReturn(false);
        assertEquals("redirect:/login?registered", controller.registerUser(dto, result, model));
    }

    @Test
    public void testRegisterUserEmailExists() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("t@t.com");
        dto.setPassword("pass");
        dto.setConfirmPassword("pass");
        when(userService.emailExists(anyString())).thenReturn(true);
        when(result.hasErrors()).thenReturn(true);
        assertEquals("register", controller.registerUser(dto, result, model));
    }

    @Test
    public void testRegisterUserPasswordMismatch() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setEmail("t@t.com");
        dto.setPassword("pass");
        dto.setConfirmPassword("wrong");
        when(userService.emailExists(anyString())).thenReturn(false);
        when(result.hasErrors()).thenReturn(true);
        assertEquals("register", controller.registerUser(dto, result, model));
    }
}
