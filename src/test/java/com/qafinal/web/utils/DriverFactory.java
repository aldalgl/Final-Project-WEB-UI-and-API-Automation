package com.qafinal.web.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Factory untuk membuat & mengelola instance WebDriver.
 * ThreadLocal supaya aman bila dijalankan paralel.
 */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        if (isRunningHeadless()) {
            // Otomatis aktif di GitHub Actions (env var CI=true) atau lewat -Dheadless=true
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        WebDriver driver = new ChromeDriver(options);
        DRIVER_THREAD_LOCAL.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            DRIVER_THREAD_LOCAL.remove();
        }
    }

    private static boolean isRunningHeadless() {
        boolean isCi = "true".equalsIgnoreCase(System.getenv("CI"));
        boolean forcedHeadless = Boolean.getBoolean("headless");
        return isCi || forcedHeadless;
    }
}
