package org.etiya.constants;

public class Constants {

    public static final String BASE_URL = "http://localhost:4200/home";

    // Selectors
    public static final String SEARCH_BUTTON_DISABLED = "button.search-button-disabled";
    public static final String CLEAR_BUTTON_DISABLED = "button.clear-button-disabled";
    public static final String SEARCH_BUTTON = "button.search-button";
    public static final String CLEAR_BUTTON = "button.clear-button";
    public static final String CREATE_CUSTOMER_BUTTON = "button.create-customer";
    public static final String TABLE_HEADER = "//table[@class='table']/thead/tr/th[contains(text(), '%s')]";
    public static final String ERROR_POPUP = "//p[contains(text(), 'Matching credentials not found.')]";
    public static final String NO_CUSTOMER_FOUND_MESSAGE = "//p[contains(text(), 'No customer found!')]";
    public static final String TABLE = "//table[@class='table']";
    public static final String CUSTOMER_ID_CELL = "td.cursor";

    // Messages
    public static final String CUSTOMER_ID_HEADER = "Customer Id";
    public static final String FIRST_NAME_HEADER = "First Name";
    public static final String SECOND_NAME_HEADER = "Second Name";
    public static final String LAST_NAME_HEADER = "Last Name";
    public static final String ROLE_HEADER = "Role";
    public static final String ID_NUMBER_HEADER = "ID Number";
    public static final String ACCOUNT_NUMBER_HEADER = "Account Number";
    public static final String GSM_NUMBER_HEADER = "GSM Number";
}
