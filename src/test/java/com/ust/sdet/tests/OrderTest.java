package com.ust.sdet.tests;

import com.ust.sdet.builder.OrderBuilder;
import com.ust.sdet.factory.OrderFactory;
import com.ust.sdet.repository.OrderRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
void createsOrder() {
    factory.persisted(
            OrderBuilder.anOrder()
                    .withQty(3)
                    .build()
    );
    assertEquals(1, repo.count());
}

@Test
void createsSecondOrder() {
    factory.persisted(OrderBuilder.anOrder().build());
    assertEquals(1, repo.count());
}

@Test
void createsLargeQuantityOrder() {
    factory.persisted(
            OrderBuilder.anOrder()
                    .withQty(100)
                    .build()
    );
    assertEquals(1, repo.count());
}

@Test
void createsSingleQuantityOrder() {
    factory.persisted(
            OrderBuilder.anOrder()
                    .withQty(1)
                    .build()
    );
    assertEquals(1, repo.count());
}

@Test
void repositoryResetWorks() {
    factory.persisted(OrderBuilder.anOrder().build());
    repo.reset();
    assertEquals(0, repo.count());
}

@Test
void multipleResetsKeepRepositoryEmpty() {
    repo.reset();
    repo.reset();
    assertEquals(0, repo.count());
}

@Test
void countsOrders() {
    factory.persisted(OrderBuilder.anOrder().build());
    assertEquals(1, repo.count());
}

/* ---------- Intentionally failing tests ---------- */

@Test
void createOrderShouldFail() {
    factory.persisted(OrderBuilder.anOrder().build());
    assertEquals(2, repo.count()); // Fail
}

@Test
void repositoryShouldContainFiveOrders() {
    factory.persisted(OrderBuilder.anOrder().build());
    assertEquals(5, repo.count()); // Fail
}

@Test
void resetShouldStillContainOneOrder() {
    factory.persisted(OrderBuilder.anOrder().build());
    repo.reset();
    assertEquals(1, repo.count()); // Fail
}
}