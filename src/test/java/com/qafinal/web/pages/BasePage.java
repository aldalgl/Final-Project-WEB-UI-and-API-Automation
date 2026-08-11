package com.qafinal.web.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Kelas dasar untuk semua Page Object Web UI (demoblaze.com).
 * Berisi fungsi umum: klik, isi form, baca teks, tunggu elemen, dan handle alert JS.
 */
public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.clear();
        if (text != null && !text.isEmpty()) {
            element.sendKeys(text);
        }
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Menunggu elemen menjadi TIDAK terlihat lagi (dipakai untuk menunggu modal Bootstrap
     * benar-benar selesai animasi/transisi tertutup sebelum membuka modal lain).
     * Aman dipanggil meski elemen sudah tidak ada sama sekali di DOM.
     */
    protected void waitInvisible(By locator) {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception ignored) {
            // Elemen memang sudah tidak ada / tidak pernah ada - aman diabaikan
        }
    }

    /**
     * Menunggu native JS alert muncul, membaca teksnya, lalu klik OK (accept).
     * Dipakai untuk notifikasi sign up/login/add-to-cart di demoblaze yang berupa alert() bawaan browser.
     */
    public String getAlertTextAndAccept() {
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }
}
