package org.etiya.test;

import org.etiya.constants.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FR4CustomersViewed {
    private WebDriver webDriver;

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(Constants.BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        webDriver.quit();
    }

    @Test
    public void customerInfoScreen() throws InterruptedException {


        WebElement customerIdElement = webDriver.findElement(By.cssSelector("td.cursor"));
        String customerIdText = customerIdElement.getText();
        customerIdElement.click();
        Thread.sleep(2000);


        String actualUrl = webDriver.getCurrentUrl();
        Assertions.assertTrue(actualUrl.contains(customerIdText), "Customer ID URL'de bulunamadı.");
        Thread.sleep(1000);

    }

    @Test
    public void verifyTabs() throws InterruptedException {
        webDriver.get("http://localhost:4200/home/customer/32555cc8-7cb0-41d9-9c95-f0f24e68efe9/info");
        Thread.sleep(1000);

        WebElement customerAccountTab = webDriver.findElement(By.linkText("Customer Account"));
        customerAccountTab.click();
        Thread.sleep(1000);
        String currentUrl1 = webDriver.getCurrentUrl();
        Assertions.assertTrue(currentUrl1.contains("account"), "Customer Account tabı için URL'de beklenen kısım bulunamadı: account");

        WebElement addressTab = webDriver.findElement(By.linkText("Address"));
        addressTab.click();
        Thread.sleep(1000);
        String currentUrl2 = webDriver.getCurrentUrl();
        Assertions.assertTrue(currentUrl2.contains("address"), "Address tabı için URL'de beklenen kısım bulunamadı: address");


        WebElement contactMediumTab = webDriver.findElement(By.linkText("Contact Medium"));
        contactMediumTab.click();
        Thread.sleep(1000);
        String currentUrl3 = webDriver.getCurrentUrl();
        Assertions.assertTrue(currentUrl3.contains("contact-medium-info"), "Contact Medium tabı için URL'de beklenen kısım bulunamadı: contact-medium-info");


        WebElement customerInfoTab = webDriver.findElement(By.linkText("Customer Info"));
        customerInfoTab.click();
        Thread.sleep(1000);
        String currentUrl4 = webDriver.getCurrentUrl();
        Assertions.assertTrue(currentUrl4.contains("info"), "Customer Info tabı için URL'de beklenen kısım bulunamadı: info");

    }

    @Test
    public void cancelButton() throws InterruptedException {
        webDriver.get("http://localhost:4200/home/customer/32555cc8-7cb0-41d9-9c95-f0f24e68efe9/info");

        WebElement cancelButton = webDriver.findElement(By.cssSelector(".cancel-button"));
        cancelButton.click();
        Thread.sleep(2000);

        String newUrl = webDriver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:4200/home", newUrl, "Ana sayfaya dönüş başarısız oldu.");
    }



}
