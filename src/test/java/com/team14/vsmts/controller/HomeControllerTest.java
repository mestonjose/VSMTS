package com.team14.vsmts.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {
    @Test
    public void testHome() {
        HomeController hc = new HomeController();
        assertEquals("home", hc.home());
        assertEquals("login", hc.login());
    }
}
