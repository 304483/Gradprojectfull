package com.ust.sdet.support;

import com.ust.sdet.api.dbframework.support.TestEnvironment;

public final class Config {

    private Config() {
    }

    public static String baseUrl() {
        return TestEnvironment
                .optional("BASE_URL", "http://localhost:5173")
                .replaceAll("/$", "");
    }

    public static String loginUrl() {
        return baseUrl() + "/login";
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                TestEnvironment.optional("HEADLESS", "false")
        );
    }

    public static String browser(){
        return TestEnvironment.optional("BROWSER", "chrome");
    }
}