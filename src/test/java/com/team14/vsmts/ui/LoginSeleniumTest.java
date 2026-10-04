package com.team14.vsmts.ui;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginSeleniumTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("Selenium UI: Should successfully register and then login")
    public void testSuccessfulLogin() throws InterruptedException {
        // 1. REGISTER FIRST
        driver.get("http://localhost:" + port + "/register");
        
        driver.findElement(By.id("fullName")).sendKeys("Meston Jose");
        driver.findElement(By.id("email")).sendKeys("mestonjose.demo" + System.currentTimeMillis() + "@example.com");
        driver.findElement(By.id("password")).sendKeys("M3ston@3001");
        driver.findElement(By.id("confirmPassword")).sendKeys("M3ston@3001");
        new Select(driver.findElement(By.id("role"))).selectByValue("Vehicle Owner");
        
        Thread.sleep(1500); // Wait so you can see it
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1500); // Wait for redirect to login
        
        // 2. NOW LOGIN
        driver.get("http://localhost:" + port + "/login");

        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        emailField.sendKeys("mestonjose.demo" + System.currentTimeMillis() + "@example.com");
        passwordField.sendKeys("M3ston@3001");
        
        Thread.sleep(1500); // Wait so you can see the login form filled
        submitButton.click();
        Thread.sleep(2500); // Wait to see the dashboard!

        // Assert we got to the owner dashboard
        assertTrue(driver.getCurrentUrl().contains("dashboard"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
