package com.ust.sdet.tests;

import com.ust.sdet.pages.*;
import com.ust.sdet.support.Config;
import com.ust.sdet.support.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Refactoring_Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(Config.loginUrl());
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void refactor(){

        LoginPage login = new LoginPage(driver);

        HomePage home = login.email("customer@example.com").password("Password@123").signIn();

        assertEquals("Customer User", home.header().userName());

        home.isHeadingVisible();

        String heading = home.headingText();

        assertEquals("Welcome, Customer User", heading);

        home.header().openProducts();
        CatalogPage catalog = new CatalogPage(driver);

        CartPage cart =
                catalog.search("headphones", "Showing 1 product")
                        .openFirstProduct()
                        .addToCart();

        cart.header().cartBadge().expectCount(1);

        cart.header().openCart();

        assertEquals(1, cart.lineCount());

        String total = cart.proceed().getTotal();

        assertEquals("Rs. 8,198", total);
    }
}
