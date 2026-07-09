package com.ust.sdet.pages.components;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartBadge {

    private static final By COUNT =
            By.cssSelector("[data-test='cart-count']");

    private final WebDriverWait wait;

    public CartBadge(WebDriverWait wait) {
        this.wait = wait;
    }

    public void expectCount(int expected) {

        String actual =
                wait.until(driver ->
                        driver.findElement(COUNT).getText());

        assertEquals(
                String.valueOf(expected),
                actual
        );
    }
}