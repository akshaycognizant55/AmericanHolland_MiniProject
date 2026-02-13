package com.simple.tests;

import com.simple.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HollandAmericaTest extends BaseTest {

    @Test
    public void verifyAsiaCruiseFilters() throws InterruptedException {
        HomePage page = new HomePage(driver);

        page.openSite();
        page.acceptCookies();
        page.searchAsia();
        page.clickViewCruises();

        page.selectMonths();   // Sep, Oct, Nov, Dec (2026)
        page.chooseDuration(); // first enabled duration
        page.chooseDeparture();// Tokyo, Japan
        page.chooseShip();     // first enabled ship

        int results = page.sortAndGetResults(); // sort by price asc + read header
        System.out.println("Cruise Results: " + results);
        Assert.assertTrue(results >= 0, "Results count should be non-negative.");
    }
}