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

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FR3CustomersFiltered {
    private WebDriver webDriver;
    private Methods methods;
    private static final Logger logger = Logger.getLogger(FR3CustomersFiltered.class.getName());

    @BeforeEach
    public void setUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(Constants.BASE_URL);
        methods = new Methods(webDriver);
    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void customerSearchScreenCheck() throws InterruptedException {
        Thread.sleep(2000);
        // Check the headers in the table
        assertTrue(Methods.isHeaderPresent(Constants.CUSTOMER_ID_HEADER), Constants.CUSTOMER_ID_HEADER + " header not found.");
        assertTrue(Methods.isHeaderPresent(Constants.FIRST_NAME_HEADER), Constants.FIRST_NAME_HEADER + " header not found.");
        assertTrue(Methods.isHeaderPresent(Constants.SECOND_NAME_HEADER), Constants.SECOND_NAME_HEADER + " header not found.");
        assertTrue(Methods.isHeaderPresent(Constants.LAST_NAME_HEADER), Constants.LAST_NAME_HEADER + " header not found.");
        assertTrue(Methods.isHeaderPresent(Constants.ROLE_HEADER), Constants.ROLE_HEADER + " header not found.");
        assertTrue(Methods.isHeaderPresent(Constants.ID_NUMBER_HEADER), Constants.ID_NUMBER_HEADER + " header not found.");
        assertTrue(Methods.isHeaderPresent(Constants.ACCOUNT_NUMBER_HEADER), Constants.ACCOUNT_NUMBER_HEADER + " header not found.");
        assertTrue(Methods.isHeaderPresent(Constants.GSM_NUMBER_HEADER), Constants.GSM_NUMBER_HEADER + " header not found.");

        methods.assertTextBoxEmpty("nationalityIdentity");
        methods.assertTextBoxEmpty("id");
        methods.assertTextBoxEmpty("accountNumber");
        methods.assertTextBoxEmpty("gsmNumber");
        methods.assertTextBoxEmpty("firstname");
        methods.assertTextBoxEmpty("lastname");
        methods.assertTextBoxEmpty("orderNumber");
    }

    @Test
    public void testSearchButtonInactiveWithoutData() throws InterruptedException {
        WebElement searchButton = webDriver.findElement(By.cssSelector(Constants.SEARCH_BUTTON_DISABLED));
        searchButton.click();
        Thread.sleep(1000);
        boolean isButtonDisabled = searchButton.isDisplayed() && !searchButton.isEnabled();
        assertTrue(isButtonDisabled, "Search button should be disabled when no data is entered.");
    }

    @Test
    public void SuccessfulSearch() throws InterruptedException, IOException {
        String csvFilePath = "src/main/resources/dataset_scsearch.csv";
        List<String[]> testData = Methods.readCSV(csvFilePath);
        for (String[] data : testData) {
            performSearch(data);
        }
    }

    private boolean performSearch(String[] data) throws InterruptedException {
        String idNumber = data[0];
        String customerId = data[1];
        String accountNumber = data[2];
        String gsmNumber = data[3];
        String firstName = data[4];
        String lastName = data[5];
        String orderNumber = data[6];

        WebElement searchButtonDisabled = webDriver.findElement(By.cssSelector(Constants.SEARCH_BUTTON_DISABLED));
        assertTrue(searchButtonDisabled.getAttribute("disabled") != null, "Search button should be initially disabled.");

        WebElement clearButtonDisabled = webDriver.findElement(By.cssSelector(Constants.CLEAR_BUTTON_DISABLED));
        assertTrue(clearButtonDisabled.getAttribute("disabled") != null, "Clear button should be initially disabled.");

        methods.enterData("nationalityIdentity", idNumber);
        methods.enterData("id", customerId);
        methods.enterData("accountNumber", accountNumber);
        methods.enterData("gsmNumber", gsmNumber);
        methods.enterData("firstname", firstName);
        methods.enterData("lastname", lastName);
        methods.enterData("orderNumber", orderNumber);

        WebElement searchButton = webDriver.findElement(By.cssSelector(Constants.SEARCH_BUTTON));
        assertTrue(searchButton.getAttribute("disabled") == null, "Search button should be enabled after data entry.");

        WebElement clearButton = webDriver.findElement(By.cssSelector(Constants.CLEAR_BUTTON));
        assertTrue(clearButton.getAttribute("disabled") == null, "Clear button should be enabled after data entry.");

        searchButton.click();
        Thread.sleep(500);

        boolean userFound = methods.isTablePresent();
        assertTrue(userFound, "User not found.");
        clearButton.click();
        return userFound;
    }

    @Test
    public void notMatchingData() throws InterruptedException, IOException {
        String csvFilePath = "src/main/resources/dataset_unscsearch.csv";
        List<String[]> testData = Methods.readCSV(csvFilePath);
        for (String[] data : testData) {
            performUnsuccessfulSearch(data);
        }
    }

    private void performUnsuccessfulSearch(String[] data) throws InterruptedException {
        String idNumber = data[0];
        String customerId = data[1];
        String accountNumber = data[2];
        String gsmNumber = data[3];
        String firstName = data[4];
        String lastName = data[5];
        String orderNumber = data[6];

        methods.enterData("nationalityIdentity", idNumber);
        methods.enterData("id", customerId);
        methods.enterData("accountNumber", accountNumber);
        methods.enterData("gsmNumber", gsmNumber);
        methods.enterData("firstname", firstName);
        methods.enterData("lastname", lastName);
        methods.enterData("orderNumber", orderNumber);

        WebElement searchButton = webDriver.findElement(By.cssSelector(Constants.SEARCH_BUTTON));
        searchButton.click();
        Thread.sleep(1000);

        WebElement errorPopup = webDriver.findElement(By.xpath(Constants.ERROR_POPUP));
        assertEquals("Matching credentials not found.", errorPopup.getText());
        webDriver.navigate().refresh();
    }

    @Test
    public void testClearFunctionality() throws InterruptedException {
        String idNumber = "40255697334";
        String customerId = "5ce96070-f731-498b-a6c6-2438282e8af5";
        String accountNumber = "";
        String gsmNumber = "5454105388";
        String firstName = "Berk";
        String lastName = "oğlu";
        String orderNumber = "";

        methods.enterData("nationalityIdentity", idNumber);
        methods.enterData("id", customerId);
        methods.enterData("accountNumber", accountNumber);
        methods.enterData("gsmNumber", gsmNumber);
        methods.enterData("firstname", firstName);
        methods.enterData("lastname", lastName);
        methods.enterData("orderNumber", orderNumber);

        WebElement searchButton = webDriver.findElement(By.cssSelector(Constants.SEARCH_BUTTON));
        searchButton.click();
        Thread.sleep(2000);

        WebElement clearButton = webDriver.findElement(By.cssSelector(Constants.CLEAR_BUTTON));
        clearButton.click();
        Thread.sleep(2000);

        Methods.assertTextBoxCleared("nationalityIdentity");
        Methods.assertTextBoxCleared("id");
        Methods.assertTextBoxCleared("accountNumber");
        Methods.assertTextBoxCleared("gsmNumber");
        Methods.assertTextBoxCleared("firstname");
        Methods.assertTextBoxCleared("lastname");
        Methods.assertTextBoxCleared("orderNumber");

        boolean tablePresent = methods.isTablePresent();

        if (tablePresent) {
            logger.info("Table was reset after clearing.");
        } else {
            logger.severe("Table was not reset after clearing.");
        }
    }

    @Test
    public void testCustomerInfoNavigation() throws InterruptedException {
        String idNumber = "";
        String customerId = "";
        String accountNumber = "";
        String gsmNumber = "";
        String firstName = "";
        String lastName = "oğlu";
        String orderNumber = "";

        methods.enterData("nationalityIdentity", idNumber);
        methods.enterData("id", customerId);
        methods.enterData("accountNumber", accountNumber);
        methods.enterData("gsmNumber", gsmNumber);
        methods.enterData("firstname", firstName);
        methods.enterData("lastname", lastName);
        methods.enterData("orderNumber", orderNumber);

        WebElement searchButton = webDriver.findElement(By.cssSelector(Constants.SEARCH_BUTTON));
        searchButton.click();
        Thread.sleep(2000);

        WebElement customerIdElement = webDriver.findElement(By.cssSelector(Constants.CUSTOMER_ID_CELL));
        String customerIdText = customerIdElement.getText();
        customerIdElement.click();
        Thread.sleep(2000);

        String actualUrl = webDriver.getCurrentUrl();
        assertTrue(actualUrl.contains(customerIdText), "Customer ID not found in the URL.");
        logger.info("Customer ID verified in the URL.");
        logger.info("Successfully navigated to the Customer Info page: " + actualUrl);
    }

    @Test
    public void nonexistingCustomer() throws InterruptedException {
        String idNumber = "11111111112";
        methods.enterData("nationalityIdentity", idNumber);

        WebElement searchButton = webDriver.findElement(By.cssSelector(Constants.SEARCH_BUTTON));
        searchButton.click();
        Thread.sleep(2000);

        // Check for the presence of the "Create Customer" button
        WebElement createCustomerButton = webDriver.findElement(By.cssSelector(Constants.CREATE_CUSTOMER_BUTTON));
        assertTrue(createCustomerButton.isDisplayed(), "Create Customer button not found.");
        logger.info("Create Customer button displayed.");

        // Check for the presence of the "No customer found!" message
        WebElement noCustomerFoundMessage = webDriver.findElement(By.xpath(Constants.NO_CUSTOMER_FOUND_MESSAGE));
        assertTrue(noCustomerFoundMessage.isDisplayed(), "No customer found message not found.");
        logger.info("No customer found message displayed.");
    }

    @Test
    public void testTextBoxAcceptsOnlyIntValues() throws InterruptedException, IOException {
        String csvFilePath = "src/main/resources/dataset_int_check.csv";
        List<String[]> testData = Methods.readCSV(csvFilePath);
        for (String[] data : testData) {
            performIntegerData(data);
        }
    }

    private void performIntegerData(String[] data) throws InterruptedException {
        String idNumber = data[0];
        String accountNumberInput = data[1];
        String gsmNumberInput = data[2];
        String orderNumberInput = data[3];

        methods.enterData("nationalityIdentity", idNumber);
        methods.enterData("accountNumber", accountNumberInput);
        methods.enterData("gsmNumber", gsmNumberInput);
        methods.enterData("orderNumber", orderNumberInput);

        Thread.sleep(1000);

        String idNumberOutput = webDriver.findElement(By.name("nationalityIdentity")).getAttribute("value");
        String accountNumberOutput = webDriver.findElement(By.name("accountNumber")).getAttribute("value");
        String gsmNumberOutput = webDriver.findElement(By.name("gsmNumber")).getAttribute("value");
        String orderNumberOutput = webDriver.findElement(By.name("orderNumber")).getAttribute("value");

        // Use the function to extract numbers from the string
        String expectedIdNumber = methods.extractNumbers(idNumber);
        String expectedAccountNumber = methods.extractNumbers(accountNumberInput);
        String expectedGsmNumber = methods.extractNumbers(gsmNumberInput);
        String expectedOrderNumber = methods.extractNumbers(orderNumberInput);

        assertEquals(expectedIdNumber, idNumberOutput);
        assertEquals(expectedAccountNumber, accountNumberOutput);
        assertEquals(expectedGsmNumber, gsmNumberOutput);
        assertEquals(expectedOrderNumber, orderNumberOutput);
    }
}
