package com.team14.vsmts.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SecurityConfigCoverageTest {

    @Test
    public void testPasswordEncoder() {
        SecurityConfig config = new SecurityConfig();
        assertNotNull(config.passwordEncoder());
    }

    @Test
    public void testCustomSuccessHandlerOwner() throws Exception {
        SecurityConfig config = new SecurityConfig();
        AuthenticationSuccessHandler handler = config.customSuccessHandler();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication auth = mock(Authentication.class);
        
        when(auth.getAuthorities()).thenAnswer(invocation -> Collections.singleton(new SimpleGrantedAuthority("ROLE_VEHICLE_OWNER")));
        handler.onAuthenticationSuccess(request, response, auth);
        assertEquals("/owner/dashboard", response.getRedirectedUrl());
    }

    @Test
    public void testCustomSuccessHandlerServiceCenter() throws Exception {
        SecurityConfig config = new SecurityConfig();
        AuthenticationSuccessHandler handler = config.customSuccessHandler();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication auth = mock(Authentication.class);
        
        when(auth.getAuthorities()).thenAnswer(invocation -> Collections.singleton(new SimpleGrantedAuthority("ROLE_SERVICE_CENTER")));
        handler.onAuthenticationSuccess(request, response, auth);
        assertEquals("/service-center/dashboard", response.getRedirectedUrl());
    }

    @Test
    public void testCustomSuccessHandlerAdmin() throws Exception {
        SecurityConfig config = new SecurityConfig();
        AuthenticationSuccessHandler handler = config.customSuccessHandler();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication auth = mock(Authentication.class);
        
        when(auth.getAuthorities()).thenAnswer(invocation -> Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        handler.onAuthenticationSuccess(request, response, auth);
        assertEquals("/admin/dashboard", response.getRedirectedUrl());
    }

    @Test
    public void testCustomSuccessHandlerDefault() throws Exception {
        SecurityConfig config = new SecurityConfig();
        AuthenticationSuccessHandler handler = config.customSuccessHandler();
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Authentication auth = mock(Authentication.class);
        
        when(auth.getAuthorities()).thenAnswer(invocation -> Collections.emptyList());
        handler.onAuthenticationSuccess(request, response, auth);
        assertEquals("/", response.getRedirectedUrl());
    }
}
