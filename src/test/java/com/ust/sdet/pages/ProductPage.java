package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {

    private static final By PRODUCT_TITLE =
            By.cssSelector("[data-test='detail-name']");

    private static final By ADD_TO_CART =
            By.cssSelector("[data-test='add-to-cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String title() {
        return text(PRODUCT_TITLE);
    }

    public CartPage addToCart() {
        click(ADD_TO_CART);
        return new CartPage(driver);
    }
}