package com.qafinal.web.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk halaman detail produk demoblaze (mis. https://www.demoblaze.com/prod.html?idp_=1)
 */
public class ProductPage extends BasePage {

    private final By productNameTitle = By.className("name");
    private final By addToCartButton = By.xpath("//a[normalize-space(text())='Add to cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productNameTitle);
    }

    public void addToCart() {
        click(addToCartButton);
    }
}
