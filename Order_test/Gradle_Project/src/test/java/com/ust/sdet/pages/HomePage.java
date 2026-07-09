package com.ust.sdet.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage{

    private static final By HEADING = By.id("page-title");
    private static final By CATALOG = By.cssSelector("a[href='/catalog']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingVisible(){
        return visible(HEADING).isDisplayed();
    }

    public String headingText() {
        return text(HEADING);
    }

    public CatalogPage gotoCatalog() {
        click(CATALOG);
        return new CatalogPage(driver);
    }

}
