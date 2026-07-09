package com.ust.sdet.pages;

import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.pages.components.ProductCard;
import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CatalogPage extends BasePage{

    private static final By SEARCH_INPUT =
            By.cssSelector("[data-test='search-input']");

    private static final By CATEGORY = By.id("category-filter");

    private static final By PRODUCT_CARD =
            By.cssSelector("[data-test='product-card']");

    private static final By RESULT_COUNT =
            By.cssSelector("[data-test='catalog-result-count']");

    private static final By SORT_SELECT =
            By.cssSelector("[data-test='sort-select']");

    private static final By PRODUCT_LINK =
            By.cssSelector("[data-test='product-card'] a");


    public CatalogPage(WebDriver driver) {
        super(driver);
    }

    public CatalogPage open() {
        driver.get(Config.catalogUrl());
        return this;
    }

    public CatalogPage search(String text, String expectedCount) {

        WebElement searchInput =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                SEARCH_INPUT
                        )
                );

        searchInput.clear();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.textToBe(
                        RESULT_COUNT,
                        expectedCount
                )
        );

        return this;
    }

    public CatalogPage searchFor(String text) {

        WebElement searchInput =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                SEARCH_INPUT
                        )
                );

        searchInput.clear();
        searchInput.sendKeys(text);
        searchInput.sendKeys(Keys.ENTER);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        PRODUCT_CARD
                )
        );

        return this;
    }

    public ProductPage openFirstProduct() {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        PRODUCT_LINK
                )
        );

        elements(PRODUCT_LINK)
                .getFirst()
                .click();

        return new ProductPage(driver);
    }

    public List<ProductCard> cards() {

        return driver.findElements(PRODUCT_CARD)
                .stream()
                .map(ProductCard::new)
                .toList();
    }

    public List<String> titles() {

        return cards()
                .stream()
                .map(ProductCard::title)
                .toList();
    }

    public List<Integer> prices() {

        return cards()
                .stream()
                .map(ProductCard::price)
                .toList();
    }

    public CatalogPage sortBy(String label) {

//        WebElement oldFirst = visibleElements(PRODUCT_CARD).getFirst();

        new Select(visible(SORT_SELECT))
                .selectByVisibleText(label);

//        wait.until(ExpectedConditions.stalenessOf(oldFirst));

        visibleElements(PRODUCT_CARD);

        return this;
    }

    public boolean isDisplayed() {
        return visible(SEARCH_INPUT).isDisplayed();
    }
}
