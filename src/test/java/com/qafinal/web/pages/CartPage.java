package com.qafinal.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Page Object untuk halaman Cart demoblaze (https://www.demoblaze.com/cart.html)
 */
public class CartPage extends BasePage {

    private final By cartRows = By.cssSelector("#tbodyid tr");
    private final By productNameCells = By.cssSelector("#tbodyid tr td:nth-child(2)");
    private final By totalPriceLabel = By.id("totalp");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartRowCount() {
        // TIDAK pakai wait di sini secara sengaja - dipakai juga oleh skenario "keranjang kosong"
        // yang justru mengharapkan 0 baris, jadi tidak boleh menunggu elemen yang memang tidak ada.
        return driver.findElements(cartRows).size();
    }

    public List<String> getProductNamesInCart() {
        // Cart demoblaze memuat isi lewat AJAX setelah halaman termuat, jadi perlu ditunggu
        // sampai minimal 1 baris muncul sebelum dibaca (dipakai hanya oleh skenario yang
        // memang mengharapkan produk sudah ada).
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(cartRows, 0));
        return driver.findElements(productNameCells)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public double getTotalPrice() {
        String text = getText(totalPriceLabel).trim();
        return text.isEmpty() ? 0 : Double.parseDouble(text);
    }
}
