package org.etiya.test;

import org.etiya.constants.Constants;
import org.etiya.methods.Methods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FR1Login {
    private WebDriver webDriver;
    private Methods methods;

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
        webDriver.get(Constants.BASE_URL);
        webDriver.manage().window().maximize();
        methods = new Methods(webDriver);

    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void logintest() throws InterruptedException {
        WebElement usernameField = webDriver.findElement(By.id("username"));
        usernameField.click();
        usernameField.sendKeys("testuser");
        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys("testpassword");
        Thread.sleep(1000);

        WebElement loginButton = webDriver.findElement(By.cssSelector(".btn"));
        loginButton.click();
        Thread.sleep(1000);
        assertTrue(webDriver.getCurrentUrl().contains("home"), "Login başarılı olamadı.");
    }

    @Test
    public void testEmptyFields() throws InterruptedException {
        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys("testpassword");

        Thread.sleep(3000);

        WebElement loginButton = webDriver.findElement(By.cssSelector(".btn"));
        loginButton.click();

        WebElement popupMessage = webDriver.findElement(By.cssSelector(".popup-message"));
        assertEquals("Username and password fields cannot be empty.", popupMessage.getText());

        Thread.sleep(1000);
        webDriver.navigate().refresh();

        WebElement usernameField = webDriver.findElement(By.id("username"));
        usernameField.click();
        usernameField.sendKeys("testuser");

        Thread.sleep(3000);

        loginButton = webDriver.findElement(By.cssSelector(".btn"));
        loginButton.click();

        popupMessage = webDriver.findElement(By.cssSelector(".popup-message"));
        assertEquals("Username and password fields cannot be empty.", popupMessage.getText());

        Thread.sleep(1000);
    }

    @Test
    public void testBothFieldsEmpty() throws InterruptedException {
        // Hem kullanıcı adı hem de şifre alanları boş bırakılıyor
        WebElement loginButton = webDriver.findElement(By.cssSelector(".btn"));
        loginButton.click();

        WebElement popupMessage = webDriver.findElement(By.cssSelector(".popup-message"));
        assertEquals("Username and password fields cannot be empty.", popupMessage.getText());

        Thread.sleep(1000);
    }

    @Test
    public void invalidPasswordFormat() throws InterruptedException {
        WebElement usernameField = webDriver.findElement(By.id("username"));
        usernameField.click();
        usernameField.sendKeys("testuser");

        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys("short");

        Thread.sleep(1000);

        WebElement popupMessage = webDriver.findElement(By.cssSelector(".popup-message"));
        assertTrue(popupMessage.getText().contains("This field must be minimum 8"));


        Thread.sleep(1000);
    }

    @Test
    public void invalidUsernameformat() throws InterruptedException {
        // Kullanıcı adı alanına özel karakterler içeren bir değer girilir
        WebElement usernameField = webDriver.findElement(By.id("username"));
        usernameField.click();
        usernameField.sendKeys("#$test@user123");


        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys("testpassword");

        Thread.sleep(1000);


        WebElement popupMessage = webDriver.findElement(By.cssSelector(".popup-message"));
        assertEquals("Username field does not allow Turkish characters, numbers, or symbols.", popupMessage.getText());

        Thread.sleep(1000);
    }

    @Test
    public void testWrongCredentials() throws InterruptedException {
        // Kullanıcı adı alanına yanlış bir değer girilir
        WebElement usernameField = webDriver.findElement(By.id("username"));
        usernameField.click();
        usernameField.sendKeys("wronguser");

        // Şifre alanına yanlış bir değer girilir
        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys("wrongpassword");

        Thread.sleep(1000);

        // Login butonuna tıklanır
        WebElement loginButton = webDriver.findElement(By.cssSelector(".btn"));
        loginButton.click();

        // Popup mesajının kontrolü
        WebElement popupMessage = webDriver.findElement(By.cssSelector(".popup-message"));
        assertEquals("Wrong username or password. Please try again", popupMessage.getText());

        Thread.sleep(1000);
    }

    @Test
    public void testRememberMe() throws InterruptedException {
        WebElement usernameField = webDriver.findElement(By.id("username"));
        usernameField.click();
        usernameField.sendKeys("testuser");

        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys("testpassword");

        Thread.sleep(1000);
        WebElement rememberMeCheckbox = webDriver.findElement(By.id("rememberMe"));
        rememberMeCheckbox.click();

        assertTrue(rememberMeCheckbox.isSelected());

        Thread.sleep(1000);
    }

    @Test
    public void testShowPassword() throws InterruptedException {
        WebElement passwordField = webDriver.findElement(By.id("password"));
        passwordField.click();
        passwordField.sendKeys("testpassword");

        Thread.sleep(1000);

        WebElement eyeIcon = webDriver.findElement(By.cssSelector(".pass-icon"));
        eyeIcon.click();

        Thread.sleep(1000);

        WebElement passwordVisible = webDriver.findElement(By.cssSelector("#password[type='text']"));
        assertTrue(passwordVisible.isDisplayed());
        assertEquals("testpassword", passwordVisible.getAttribute("value"));

        Thread.sleep(1000);
        eyeIcon.click();

        Thread.sleep(1000);
        WebElement passwordHidden = webDriver.findElement(By.cssSelector("#password[type='password']"));
        assertTrue(passwordHidden.isDisplayed());

        Thread.sleep(1000);
    }











}
