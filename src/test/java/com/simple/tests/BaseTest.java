package com.simple.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        // Selenium Manager will download/resolve the correct driver automatically.
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--start-maximized",
                "--disable-notifications"
        );

        //Launches Chrome with the configured options.
        driver = new ChromeDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}