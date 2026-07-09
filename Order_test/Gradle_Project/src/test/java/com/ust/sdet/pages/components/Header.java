package com.ust.sdet.pages.components;

import com.ust.sdet.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BasePage {

    private static final By HOME = By.linkText("Home");
    private static final By SYNC_LAB = By.linkText("Sync Lab");
    private static final By PROFILE = By.linkText("Profile");
    private static final By PRODUCTS = By.linkText("Products");
    private static final By CART = By.cssSelector("[data-test='cart-icon']");
    private static final By CHECKOUT = By.linkText("Checkout");
    private static final By ORDERS = By.linkText("Orders");
    private static final By USER_NAME = By.cssSelector("[aria-label='Signed in user'] span");

    public Header(WebDriver driver) {
        super(driver);
    }

    public CartBadge cartBadge() {
        return new CartBadge(wait);
    }

    public CartPage openCart() {
        click(CART);
        return new CartPage(driver);
    }

    public HomePage openHome() {
        click(HOME);
        return new HomePage(driver);
    }

    public void openProfile() {
        click(PROFILE);
    }

    public CatalogPage openProducts() {
        click(PRODUCTS);
        return new CatalogPage(driver);
    }

    public void openOrders() {
        click(ORDERS);
    }

    public CheckoutPage checkoutPage() {
        click(CHECKOUT);
        return new CheckoutPage(driver);
    }

    public String userName() {
        return text(USER_NAME);
    }
}