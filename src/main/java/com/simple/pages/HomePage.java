package com.simple.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ---------- Common locators ----------
    private final By acceptCookies   = By.id("onetrust-accept-btn-handler");

    private final By searchLink      = By.linkText("Search");
    private final By searchInput     = By.name("searchBox");
    private final By searchButton    = By.cssSelector("button.search-button");
    private final By asiaLinkPrimary = By.linkText("Asia Cruises 2026 & 2027 | Cruises to Asia");
    private final By viewCruisesCta  = By.cssSelector("a[data-automation-id='asia-cruises-cta-view-cruise']");

    private final By departDateBtn   = By.id("csr-primary-filter-button-departDate");
    private final By durationBtn     = By.id("csr-primary-filter-button-duration");
    private final By departureBtn    = By.id("csr-primary-filter-button-departure");
    private final By shipsBtn        = By.id("csr-primary-filter-button-ships");
    private final By applyBtn        = By.xpath("//button[@id='button0' and @class='csr-atom-button medium primary cruise-atom-button-container__button']");

    private final By sortDropDown    = By.id("sortByDropDown");
    private final By sortPriceAsc    = By.id("sortByDropDown-priceAsc");
    private final By resultsHeader   = By.cssSelector("h1.csr-filter-section__label");


    private void click(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        el.click();

    }

    private void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(text);
    }

    // ---------- Flow ----------
    public void openSite() {
        driver.get("https://www.hollandamerica.com/en");
    }

    public void acceptCookies() {
        click(acceptCookies);
    }

    public void searchAsia()  {
        click(searchLink);
        type(searchInput, "Asia");
        click(searchButton);

        // Prefer the exact marketing link; fall back to partial if text changes
        click(asiaLinkPrimary);

    }

    public void clickViewCruises()  {

        click(viewCruisesCta);
    }

    public void selectMonths()  {
        click(departDateBtn);

        // Select months on 2026 panel
        pickMonth("Sep");
        pickMonth("Oct");
        pickMonth("Nov");
        pickMonth("Dec");

        click(applyBtn);

    }

    private void pickMonth(String shortName)  {
        // Calendar month tiles under year 2026
        By month = By.xpath("//*[@aria-labelledby='yearLabel2026']//span[@aria-hidden='true' and text()='" + shortName + "']");
        click(month);

    }

    public void chooseDuration() {
        click(durationBtn);

        // Click first enabled duration option
        By container = By.cssSelector(".duration-filter-details__filters-container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(container));
        List<WebElement> enabled = driver.findElements(By.cssSelector(".duration-filter-details__filters-container button[aria-disabled='false']"));
        if (!enabled.isEmpty()) {
            enabled.get(0).click();
        }

        click(applyBtn);

    }

    public void chooseDeparture() throws InterruptedException {
        click(departureBtn);
        // Select Tokyo, Japan as departure
        click(By.xpath("//div[@aria-label='Tokyo, Japan']"));
        Thread.sleep(1000);
        click(applyBtn);

    }

    public void chooseShip()  {
        click(shipsBtn);

        // Click first enabled ship checkbox
        List<WebElement> ships = driver.findElements(By.cssSelector("div[data-testid='atomCheckboxButton'][aria-disabled='false']"));
        if (!ships.isEmpty()) {
            ships.get(0).click();
        }
        click(applyBtn);

    }

    public int sortAndGetResults(){
        click(sortDropDown);
        click(sortPriceAsc);

        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(resultsHeader)).getText();
        String digits = text.replaceAll("\\D+", "");
        return digits.isEmpty() ? 0 : Integer.parseInt(digits);
    }
}
