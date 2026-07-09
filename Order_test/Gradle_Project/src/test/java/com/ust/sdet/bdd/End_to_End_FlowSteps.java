package com.ust.sdet.bdd;

import com.ust.sdet.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.*;

public class End_to_End_FlowSteps {

    private final World world;

    public End_to_End_FlowSteps(World world) {
        this.world = world;
    }

    @Given("the login page is open")
    public void theLoginPageIsOpen() {
        world.login = new LoginPage(world.driver).open();
    }

    @When("I login with {string} and {string}")
    public void login(String email, String password) {
        world.homePage = world.login.email(email).password(password).signIn();
    }

    @Then("checking the username")
    public void isUserNameMatching() {
        assertEquals("Customer User", world.homePage.header().userName());
    }

    @Then("the heading is displayed")
    public void isHeadingDisplayed() {
        assertTrue(world.homePage.isHeadingVisible());
    }

    @Then("the page title contains {string}")
    public void pageTitleContains(String text) {
        assertTrue(
                world.homePage.headingText().contains(text)
        );
    }

    @When("I navigate to the catalog page")
    public void navigateToCatalog() {
        world.catalog = world.homePage.header().openProducts();
    }

    @Then("the catalog page is displayed")
    public void catalogPageDisplayed() {
        assertTrue(world.catalog.isDisplayed());
    }

    @When("I searched for the {string}")
    public void iSearchFor(String query) {
        world.catalog.searchFor(query);
    }

    @Then("search results are displayed")
    public void searchResultsDisplayed() {
        assertTrue(!world.catalog.cards().isEmpty());
    }


    @When("I added the first result to the cart")
    public void iAddTheFirstResultToTheCart() {
        world.product = world.catalog.openFirstProduct();

        world.productName = world.product.title();

        assertFalse(world.product.title().isBlank());

        world.cart = world.product.addToCart();
    }

    @Then("the cart badge becomes {int}")
    public void theCartBadgeShows(int count) {
        world.header().cartBadge().expectCount(count);
    }

    @When("I opened the cart")
    public void iOpenTheCart() {
        world.cart = world.header().openCart();
    }

    @Then("the cart has {int} line of item")
    @Then("the cart has {int} line of items")
    public void theCartHasLineItems(int count) {
        assertEquals(count, world.cart.lineCount());
    }

    @When("I placed the order")
    public void iPlaceTheOrder() {
        world.checkout = world.cart.proceed();
        world.checkout.placeOrder();
    }

    @Then("the order is got confirmed")
    public void theOrderIsConfirmed() {
        assertTrue(
                world.checkout
                        .confirmationText()
                        .toLowerCase()
                        .contains("confirmed")
        );
    }
}