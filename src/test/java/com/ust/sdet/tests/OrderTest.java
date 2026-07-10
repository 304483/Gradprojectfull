package com.ust.sdet.tests;

import com.ust.sdet.builder.OrderBuilder;
import com.ust.sdet.factory.OrderFactory;
import com.ust.sdet.repository.OrderRepository;
import io.qameta.allure.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Order Management")
@Feature("Order Repository")
public class OrderTest {

    private static final OrderRepository repo = new OrderRepository();
    private static final OrderFactory factory = new OrderFactory(repo);

    @BeforeAll
    static void migrateDatabase() {

        Flyway flyway = Flyway.configure()
                .dataSource(
                        System.getProperty("db.url", "jdbc:postgresql://localhost:5432/testdb"),
                        System.getProperty("db.user", "postgres"),
                        System.getProperty("db.password", "postgres")
                )
                .load();

        flyway.migrate();
    }

    @BeforeEach
    void setup() {
        repo.reset();
    }

    @Story("Create Order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an order is created successfully.")
    @Test
    void createsOrder() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .withQty(3)
                        .build()
        );

        assertEquals(1, repo.count());
    }

    @Story("Create Second Order")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify another order can be created.")
    @Test
    void createsSecondOrder() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        assertEquals(1, repo.count());
    }

    @Story("Large Quantity Order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify order creation with large quantity.")
    @Test
    void createsLargeQuantityOrder() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .withQty(100)
                        .build()
        );

        assertEquals(1, repo.count());
    }

    @Story("Single Quantity Order")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify order creation with quantity one.")
    @Test
    void createsSingleQuantityOrder() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .withQty(1)
                        .build()
        );

        assertEquals(1, repo.count());
    }

    @Story("Repository Reset")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify repository reset removes all orders.")
    @Test
    void repositoryResetWorks() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        repo.reset();

        assertEquals(0, repo.count());
    }

    @Story("Multiple Reset")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify multiple repository resets keep it empty.")
    @Test
    void multipleResetsKeepRepositoryEmpty() {

        repo.reset();
        repo.reset();

        assertEquals(0, repo.count());
    }

    @Story("Count Orders")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify repository count after creating one order.")
    @Test
    void countsOrders() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        assertEquals(1, repo.count());
    }

    // ------------------ Intentionally Failing Tests ------------------

    @Story("Invalid Order Count")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Intentional failure to demonstrate Allure Categories.")
    @Test
    void createOrderShouldFail() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        assertEquals(2, repo.count());
    }

    @Story("Repository Count Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional failure to demonstrate assertion failure.")
    @Test
    void repositoryShouldContainFiveOrders() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        assertEquals(5, repo.count());
    }

    @Story("Reset Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Intentional failure after repository reset.")
    @Test
    void resetShouldStillContainOneOrder() {

        factory.persisted(
                OrderBuilder.anOrder()
                        .build()
        );

        repo.reset();

        assertEquals(1, repo.count());
    }
}