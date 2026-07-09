package com.ust.sdet.support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() {

//        if (Config.gridEnabled()) {
//            try {
//                return new RemoteWebDriver(
//                        URI.create(Config.gridUrl()).toURL(),
//                        options
//                );
//            } catch (MalformedURLException e) {
//                throw new IllegalArgumentException(
//                        "Invalid Selenium Grid URL: " + Config.gridUrl(), e
//                );
//            }
//        }

        switch(Config.browser().toLowerCase()) {

            case "chrome":
                ChromeOptions options = new ChromeOptions();

                if (Config.headless()) {
                    options.addArguments("--headless=new");
                }

                options.addArguments("--window-size=1440,900");
                options.setExperimentalOption(
                        "prefs",
                        Map.of(
                                "credentials_enable_service", false,
                                "profile.password_manager_enabled", false
                        )
                );
                options.addArguments("--disable-save-password-bubble");
                options.addArguments("--incognito");
                return new ChromeDriver(options);

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (Config.headless()) {
                    firefoxOptions.addArguments("-headless");
                }

                return new FirefoxDriver(firefoxOptions);

            default:
                throw new IllegalArgumentException("Unsupported browser");
        }
    }
}