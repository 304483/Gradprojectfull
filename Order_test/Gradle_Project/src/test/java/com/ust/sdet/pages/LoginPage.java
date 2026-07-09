package com.ust.sdet.pages;

import com.ust.sdet.support.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By EMAIL =
            By.id("email");

    private static final By PASSWORD =
            By.id("password");

    private static final By SIGNIN =
            By.cssSelector(".form-submit");

    private static final By LOGIN_ERROR =
            By.cssSelector("[data-testid='login-error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(Config.loginUrl());
        return this;
    }

    public String title() {
        return driver.getTitle();
    }

    public LoginPage email(String email) {
        type(EMAIL, email);
        return this;
    }

    public LoginPage password(String password) {
        type(PASSWORD, password);
        return this;
    }

    public HomePage signIn() {
        visible(SIGNIN);
        click(SIGNIN);
        return new HomePage(driver);
    }

    public String errorMessage() {
        return text(LOGIN_ERROR);
    }
}
