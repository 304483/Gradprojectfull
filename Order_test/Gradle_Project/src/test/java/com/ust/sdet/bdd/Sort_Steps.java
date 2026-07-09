package com.ust.sdet.bdd;

import com.ust.sdet.pages.CatalogPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Sort_Steps {

    private final World world;

    public Sort_Steps(World world) {
        this.world = world;
    }

    @Given("the catalog is opened")
    public void theCatalogIsOpen() {
        world.catalog = new CatalogPage(world.driver).open();
    }

    @When("I sort products by {string}")
    public void sortProducts(String sort) {
        world.catalog.sortBy(sort);
    }

    @Then("the products are sorted in ascending order")
    public void verifySort() {
        List<Integer> prices = world.catalog.prices();

        assertEquals(prices.stream().sorted().toList(), prices);
    }
}
