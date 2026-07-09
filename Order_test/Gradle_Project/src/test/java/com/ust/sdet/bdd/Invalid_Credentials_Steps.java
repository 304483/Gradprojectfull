package com.ust.sdet.bdd;

import com.ust.sdet.pages.CatalogPage;
import com.ust.sdet.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Invalid_Credentials_Steps {

    private final World world;

    public Invalid_Credentials_Steps(World world) {
        this.world = world;
    }

    @Given("the login page is opened")
    public void loginPageIsOpen() {
        world.login = new LoginPage(world.driver).open();
    }

    @When("I logged in with {string} and {string}")
    public void iLoginWith(String email, String password) {

        world.homePage = world.login.email(email).password(password).signIn();
    }

    @Then("the login error message is displayed")
    public void loginErrorDisplayed() {

        assertEquals(
                "Invalid credentials",
                world.login.errorMessage()
        );
    }

    @Then("the the cart badge becomes {int}")
    public void verifyCartBadge(int count) {
        world.header().cartBadge().expectCount(count);
    }

}
