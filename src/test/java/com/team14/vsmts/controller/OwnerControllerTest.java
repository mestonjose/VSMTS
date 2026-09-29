package com.team14.vsmts.controller;

import com.team14.vsmts.dto.UserRegistrationDto;
import com.team14.vsmts.model.User;
import com.team14.vsmts.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetRegisterReturnsRegisterPage() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testPostRegisterWithValidDataRedirectsToLogin() throws Exception {
        when(userService.emailExists(any(String.class))).thenReturn(false);
        when(userService.registerUser(any(UserRegistrationDto.class))).thenReturn(new User());

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .param("fullName", "Test User")
                        .param("email", "test@test.com")
                        .param("password", "StrongPass1!")
                        .param("confirmPassword", "StrongPass1!")
                        .param("role", "Administrator"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?registered"));
    }

    @Test
    void testGetLoginReturnsLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}
