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

import java.sql.SQLException;

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
       // ---------------- PASSING TESTS ----------------

    @Story("Passing Test")
    @Severity(SeverityLevel.NORMAL)
    @Description("Simple passing test.")
    @Test
    void passingTest1() {
        assertEquals(1, 1);
    }

    @Story("Passing Test")
    @Severity(SeverityLevel.NORMAL)
    @Description("Another passing test.")
    @Test
    void passingTest2() {
        assertEquals("UST", "UST");
    }

    // ---------------- ASSERTION FAILURES ----------------

    @Story("Assertion Failure")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional assertion failure.")
    @Test
    void assertionFailure1() {
        assertEquals(2, 1);
    }

    @Story("Assertion Failure")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional assertion failure.")
    @Test
    void assertionFailure2() {
        assertEquals(10, 5);
    }

    @Story("Assertion Failure")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional assertion failure.")
    @Test
    void assertionFailure3() {
        assertEquals("ABC", "XYZ");
    }

    // ---------------- NULL POINTER ----------------

    @Story("Null Pointer")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Intentional NullPointerException.")
    @Test
    void nullPointer1() {
        String value = null;
        value.length();
    }

    @Story("Null Pointer")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Intentional NullPointerException.")
    @Test
    void nullPointer2() {
        Object obj = null;
        obj.toString();
    }

    // ---------------- ILLEGAL ARGUMENT ----------------

    @Story("Illegal Argument")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional IllegalArgumentException.")
    @Test
    void illegalArgument1() {
        throw new IllegalArgumentException("Negative quantity");
    }

    @Story("Illegal Argument")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional IllegalArgumentException.")
    @Test
    void illegalArgument2() {
        throw new IllegalArgumentException("Invalid customer id");
    }

    @Story("Illegal Argument")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Intentional IllegalArgumentException.")
    @Test
    void illegalArgument3() {
        throw new IllegalArgumentException("Order id cannot be null");
    }

    // ---------------- SQL EXCEPTION ----------------

    @Story("Database Failure")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Intentional SQLException.")
    @Test
    void databaseFailure() throws SQLException {
        throw new SQLException("Unable to connect to database");
    }

    // ---------------- ARITHMETIC EXCEPTION ----------------

    @Story("Arithmetic Failure")
    @Severity(SeverityLevel.NORMAL)
    @Description("Intentional ArithmeticException.")
    @Test
    void arithmeticFailure() {
        int x = 10 / 0;
        System.out.println(x);
    }

}