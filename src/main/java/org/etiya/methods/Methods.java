package org.etiya.methods;

import org.etiya.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Methods {

    private static WebDriver webDriver;

    public Methods(WebDriver webDriver) {
        Methods.webDriver = webDriver;
    }

    public static List<String[]> readCSV(String csvFilePath) throws IOException {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            boolean firstLine = true; // To skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] values = line.split(",", -1); // -1 parameter keeps empty cells
                data.add(values);
            }
        }
        return data;
    }

    public String extractNumbers(String input) {
        return input.replaceAll("[^0-9]", "");
    }

    public static boolean isHeaderPresent(String headerText) {
        WebElement tableHeader = webDriver.findElement(By.xpath(String.format(Constants.TABLE_HEADER, headerText)));
        return tableHeader != null;
    }

    public void assertTextBoxEmpty(String fieldName) {
        WebElement inputElement = webDriver.findElement(By.name(fieldName));
        assertEquals("Text box '" + fieldName + "' is not empty by default.", "", inputElement.getAttribute("value"));
    }

    public static void assertTextBoxCleared(String fieldName) {
        WebElement inputElement = webDriver.findElement(By.name(fieldName));
        assertEquals("Text box '" + fieldName + "' was not cleared.", "", inputElement.getAttribute("value"));
    }

    public boolean isTablePresent() {
        try {
            webDriver.findElement(By.xpath(Constants.TABLE));
            return true; // Table found
        } catch (Exception e) {
            return false; // Table not found
        }
    }

    public void enterData(String fieldName, String value) {
        WebElement inputElement = webDriver.findElement(By.name(fieldName));
        inputElement.clear();
        inputElement.sendKeys(value);
    }
}
