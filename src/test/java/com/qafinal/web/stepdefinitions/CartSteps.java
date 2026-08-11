package com.qafinal.web.stepdefinitions;

import com.qafinal.web.pages.CartPage;
import com.qafinal.web.pages.HomePage;
import com.qafinal.web.pages.ProductPage;
import com.qafinal.web.utils.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CartSteps {

    private HomePage homePage() {
        return new HomePage(DriverFactory.getDriver());
    }

    private ProductPage productPage() {
        return new ProductPage(DriverFactory.getDriver());
    }

    private CartPage cartPage() {
        return new CartPage(DriverFactory.getDriver());
    }

    @When("saya memilih kategori {string}")
    public void saya_memilih_kategori(String category) {
        homePage().selectCategory(category);
    }

    @When("saya membuka produk {string}")
    public void saya_membuka_produk(String productName) {
        homePage().openProductByName(productName);
    }

    @When("saya menambahkan produk tersebut ke keranjang")
    public void saya_menambahkan_produk_tersebut_ke_keranjang() {
        productPage().addToCart();
    }

    @When("saya membuka halaman keranjang")
    public void saya_membuka_halaman_keranjang() {
        homePage().openCartPage();
    }

    @Then("produk {string} muncul di daftar keranjang")
    public void produk_muncul_di_daftar_keranjang(String productName) {
        Assert.assertTrue(cartPage().getProductNamesInCart().contains(productName),
                "Produk '" + productName + "' tidak ditemukan di halaman keranjang");
    }

    @Then("total harga di halaman keranjang lebih besar dari 0")
    public void total_harga_di_halaman_keranjang_lebih_besar_dari_0() {
        Assert.assertTrue(cartPage().getTotalPrice() > 0, "Total harga di keranjang seharusnya lebih besar dari 0");
    }

    @Then("jumlah baris produk di halaman keranjang adalah {int}")
    public void jumlah_baris_produk_di_halaman_keranjang_adalah(int expectedCount) {
        Assert.assertEquals(cartPage().getCartRowCount(), expectedCount);
    }
}
