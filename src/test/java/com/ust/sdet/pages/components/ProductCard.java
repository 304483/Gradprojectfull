package com.ust.sdet.pages.components;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductCard {

    private final WebElement root;

    private static final By TITLE =
            By.cssSelector("[data-test='product-title']");

    private static final By PRICE =
            By.cssSelector("[data-test='product-price']");

    public ProductCard(WebElement root) {
        this.root = root;
    }

    public String title() {
        return root.findElement(TITLE).getText();
    }

    public String text() {
        return root.getText();
    }

    public Integer price() {

        return Integer.parseInt(
                root.findElement(PRICE)
                        .getText()
                        .replaceAll("[^0-9]", "")
        );
    }
}
